package com.verbatoria.component.event

import android.os.Build
import android.os.Handler
import android.os.Looper
import java.util.concurrent.locks.ReentrantLock

/**
 * @author n.remnev
 */

interface EventManager {

    fun post(key: String, data: Any? = null)

    fun subscribe(key: String, subscriber: Subscriber)

    fun subscribeMainThread(key: String, subscriber: Subscriber)

    fun unsubscribe(key: String, subscriber: Subscriber)

    fun unsubscribeMainThread(key: String, subscriber: Subscriber)

    interface Subscriber {

        fun onEvent(key: String, data: Any? = null) { /* empty */ }

    }

}

class EventManagerImpl : EventManager {

    private val subscriptions = HashMap<String, MutableSet<EventManager.Subscriber>>()
    private val lock = ReentrantLock(true)

    private val subscriptionsMainThread = HashMap<String, MutableSet<EventManager.Subscriber>>()
    private val lockMainThread = ReentrantLock(true)

    private val handler: Handler = if (Build.VERSION.SDK_INT >= 28) {
        Handler.createAsync(Looper.getMainLooper())
    } else {
        Handler::class.java.getConstructor(Looper::class.java, Handler.Callback::class.java, Boolean::class.java)
                .newInstance(Looper.getMainLooper(), null, true)
    }

    override fun post(key: String, data: Any?) {
        lock.lock()
        try {
            subscriptions[key]?.forEach { subscriber -> subscriber.onEvent(key, data) }
        } finally {
            lock.unlock()
        }

        lockMainThread.lock()
        try {
            subscriptionsMainThread[key]?.forEach { subscriberMainThread ->
                handler.postAtTime({ subscriberMainThread.onEvent(key, data) }, subscriberMainThread, 0)
            }
        } finally {
            lockMainThread.unlock()
        }
    }

    override fun subscribe(key: String, subscriber: EventManager.Subscriber) {
        subscribeInternal(subscriptions, lock, key, subscriber)
    }

    override fun subscribeMainThread(key: String, subscriber: EventManager.Subscriber) {
        subscribeInternal(subscriptionsMainThread, lockMainThread, key, subscriber)
    }

    override fun unsubscribe(key: String, subscriber: EventManager.Subscriber) {
        lock.lock()
        try {
            if (subscriptions[key]?.remove(subscriber) != true) {
                throw RuntimeException("This subscriber has not been registered")
            }
        } finally {
            lock.unlock()
        }
    }

    override fun unsubscribeMainThread(key: String, subscriber: EventManager.Subscriber) {
        lockMainThread.lock()
        try {
            if (subscriptionsMainThread[key]?.remove(subscriber) != true) {
                throw RuntimeException("This subscriber has not been registered")
            }
            handler.removeCallbacksAndMessages(subscriber)
        } finally {
            lockMainThread.unlock()
        }
    }

    private fun subscribeInternal(
            map: HashMap<String, MutableSet<EventManager.Subscriber>>,
            lock: ReentrantLock,
            key: String,
            subscriber: EventManager.Subscriber
    ) {
        lock.lock()
        try {
            map[key]?.add(subscriber)
                    ?: mutableSetOf(subscriber).let { map[key] = it }
        } finally {
            lock.unlock()
        }
    }

}