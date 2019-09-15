package com.verbatoria.domain.late_send.repository

import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.late_send.model.LateSendEntity
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.infrastructure.conversion.DatabaseConverter

/**
 * @author n.remnev
 */

class LateSendConverter : DatabaseConverter<LateSendEntity, LateSend>() {

    override fun toEntity(domain: LateSend): LateSendEntity =
        with(domain) {
            LateSendEntity(
                sessionId = sessionId,
                eventId = eventId,
                childName = childName,
                childAge = childAge,
                startDate = startDate,
                endDate = endDate,
                state = state.ordinal
            )
        }

    override fun toDomain(entity: LateSendEntity): LateSend =
        with(entity) {
            LateSend(
                sessionId = sessionId,
                eventId = eventId,
                childName = childName,
                childAge = childAge,
                startDate = startDate,
                endDate = endDate,
                state = LateSendState.valueOf(state)
            )
        }

}