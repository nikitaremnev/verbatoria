package com.verbatoria.domain.bci_data.manager

import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.domain.bci_data.repository.BCIDataRepository

/**
 * @author n.remnev
 */

interface BCIDataManager {

    fun getByEventId(eventId: String): List<BCIData>

    fun save(data: List<BCIData>)

}

class BCIDataManagerImpl(
    private val bciDataRepository: BCIDataRepository
) : BCIDataManager {

    override fun getByEventId(eventId: String): List<BCIData> =
        bciDataRepository.getByEventId(eventId)

    override fun save(data: List<BCIData>) {
        bciDataRepository.save(data)
    }

}