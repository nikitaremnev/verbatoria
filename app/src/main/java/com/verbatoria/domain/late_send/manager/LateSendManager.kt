package com.verbatoria.domain.late_send.manager

import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.domain.late_send.repository.LateSendRepository
import java.util.*

/**
 * @author n.remnev
 */

interface LateSendManager {

    fun findLateSend(sessionId: String): LateSend?

    fun findAllLateSend(): List<LateSend>

    fun createLateSend(eventId: String, sessionId: String, reportId: String, childName: String, childAge: Int,
                       startDate: Date, endDate: Date)

    fun updateLateSendState(sessionId: String, state: LateSendState)

    fun deleteLateSendBySessionId(sessionId: String)

}

class LateSendManagerImpl(
    private val lateSendRepository: LateSendRepository
) : LateSendManager {

    override fun findLateSend(sessionId: String): LateSend? =
        lateSendRepository.findBySessionId(sessionId)

    override fun findAllLateSend(): List<LateSend> =
        lateSendRepository.findAll()

    override fun createLateSend(
        eventId: String,
        sessionId: String,
        reportId: String,
        childName: String,
        childAge: Int,
        startDate: Date,
        endDate: Date
    ) {
        lateSendRepository.save(
            LateSend(
                eventId = eventId,
                sessionId = sessionId,
                reportId = reportId,
                childName = childName,
                childAge = childAge,
                startDate = startDate,
                endDate = endDate,
                state = LateSendState.HAS_NOTHING
            )
        )

    }

    override fun updateLateSendState(sessionId: String, state: LateSendState) {
        lateSendRepository.updateState(sessionId, state.ordinal)
    }

    override fun deleteLateSendBySessionId(sessionId: String) {
        lateSendRepository.deleteBySessionId(sessionId)
    }

}