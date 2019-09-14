package com.verbatoria.domain.bci_data.manager

import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.domain.bci_data.repository.BCIDataRepository

/**
 * @author n.remnev
 */

interface BCIDataManager {

    fun findAllBySessionId(sessionId: String): List<BCIData>

    fun save(data: List<BCIData>)

    fun deleteAllBySessionId(sessionId: String)

}

class BCIDataManagerImpl(
    private val bciDataRepository: BCIDataRepository
) : BCIDataManager {

    override fun findAllBySessionId(sessionId: String): List<BCIData> =
        bciDataRepository.findAllBySessionId(sessionId)

    override fun save(data: List<BCIData>) {
        bciDataRepository.save(data)
    }

    override fun deleteAllBySessionId(sessionId: String) {
        bciDataRepository.deleteBySessionId(sessionId)
    }

}