package com.verbatoria.domain.bci_data.repository

import com.verbatoria.domain.bci_data.model.BCIData

/**
 * @author n.remnev
 */

interface BCIDataRepository {

    fun save(data: List<BCIData>)

    fun save(data: BCIData)

    fun getByEventId(eventId: String): List<BCIData>

    fun deleteByEventId(eventId: String)

}

class BCIDataRepositoryImpl(
    private val dao: BCIDataDao,
    private val converter: BCIDataConverter
) : BCIDataRepository {

    override fun save(data: List<BCIData>) {
        dao.save(data.map(converter::toEntity))
    }

    override fun save(data: BCIData) {
        dao.save(converter.toEntity(data))
    }

    override fun getByEventId(eventId: String): List<BCIData> =
        dao.findByEventId(eventId).map(converter::toDomain)

    override fun deleteByEventId(eventId: String) {
        dao.deleteByEventId(eventId)
    }

}
