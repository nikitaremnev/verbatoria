package com.verbatoria.infrastructure.database.common.support

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val THREAD_PREFIX = "DATABASE_THREAD_"

class DatabaseThreadFactory @Inject constructor() : ThreadFactory {

    private val counter = AtomicLong()

    override fun newThread(action: Runnable?): Thread =
        Thread(action, THREAD_PREFIX + nextThreadId())

    fun newHandledThread(): DatabaseHandledThread =
        DatabaseHandledThread(THREAD_PREFIX + nextThreadId())

    private fun nextThreadId() = counter.incrementAndGet().toString()

}
