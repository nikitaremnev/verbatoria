package com.verbatoria.domain.bci_data.manager

import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.domain.bci_data.repository.BCIDataRepository

/**
 * @author n.remnev
 */

interface BCIDataManager {

    fun findAllByEventId(eventId: String): List<BCIData>

    fun save(data: List<BCIData>)

    fun deleteAllByEventId(eventId: String)

}

class BCIDataManagerImpl(
    private val bciDataRepository: BCIDataRepository
) : BCIDataManager {

    override fun findAllByEventId(eventId: String): List<BCIData> =
        bciDataRepository.findAllByEventId(eventId)

    override fun save(data: List<BCIData>) {
        bciDataRepository.save(data)
    }

    override fun deleteAllByEventId(eventId: String) {
        bciDataRepository.deleteByEventId(eventId)
    }

}