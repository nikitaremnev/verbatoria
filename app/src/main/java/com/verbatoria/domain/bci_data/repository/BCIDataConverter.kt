package com.verbatoria.domain.bci_data.repository

import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.domain.bci_data.model.BCIDataEntity
import com.verbatoria.infrastructure.conversion.DatabaseConverter

/**
 * @author n.remnev
 */

class BCIDataConverter : DatabaseConverter<BCIDataEntity, BCIData>() {

    override fun toEntity(domain: BCIData): BCIDataEntity =
        with(domain) {
            BCIDataEntity(
                id = id,
                eventId = eventId,
                timestamp = timestamp,
                attention = attention,
                mediation = mediation,
                delta = delta,
                theta = theta,
                lowAlpha = lowAlpha,
                highAlpha = highAlpha,
                lowBeta = lowBeta,
                highBeta = highBeta,
                lowGamma = lowGamma,
                middleGamma = middleGamma
            )
        }

    override fun toDomain(entity: BCIDataEntity): BCIData =
        with(entity) {
            BCIData(
                id = id,
                eventId = eventId,
                timestamp = timestamp,
                attention = attention,
                mediation = mediation,
                delta = delta,
                theta = theta,
                lowAlpha = lowAlpha,
                highAlpha = highAlpha,
                lowBeta = lowBeta,
                highBeta = highBeta,
                lowGamma = lowGamma,
                middleGamma = middleGamma
            )
        }

}