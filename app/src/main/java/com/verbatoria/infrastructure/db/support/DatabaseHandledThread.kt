package com.verbatoria.infrastructure.db.support

import android.os.Looper
import android.os.Process

/**
 * @author n.remnev
 */

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
class DatabaseHandledThread(
    name: String = ""
) : Thread(THREAD_NAME_PREFIX + name) {

    private companion object {
        const val THREAD_NAME_PREFIX = "DATABASE_HANDLED_THREAD-"
    }

    private var looper: Looper? = null

    private fun prepare() {
        Looper.prepare()

        synchronized(this) {
            looper = Looper.myLooper()
            (this as Object).notifyAll()
        }

        Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT)
    }

    override fun run() {
        prepare()

        Looper.loop()
    }

    fun getLooper(): Looper? {
        if (!isAlive) {
            return null
        }

        synchronized(this) {
            while (isAlive && looper == null) {
                try {
                    (this as Object).wait()
                } catch (e: InterruptedException) {
                    // ignore
                }
            }
        }

        return looper
    }

    fun quit(): Boolean {
        val looper = getLooper()
        if (looper != null) {
            looper.quitSafely()
            return true
        }
        return false
    }

}