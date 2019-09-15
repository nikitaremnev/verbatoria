package com.verbatoria.domain.late_send.repository

import com.verbatoria.domain.late_send.model.LateSend

/**
 * @author n.remnev
 */

interface LateSendRepository {

    fun save(data: LateSend)

    fun findBySessionId(sessionId: String): LateSend?

    fun findAll(): List<LateSend>

    fun updateState(sessionId: String, state: Int)

    fun deleteBySessionId(sessionId: String)

}

class LateSendRepositoryImpl(
    private val dao: LateSendDao,
    private val converter: LateSendConverter
) : LateSendRepository {

    override fun save(data: LateSend) {
        dao.save(converter.toEntity(data))
    }

    override fun findBySessionId(sessionId: String): LateSend? =
        dao.findBySessionId(sessionId)?.let(converter::toDomain)

    override fun findAll(): List<LateSend> =
        dao.findAll().map(converter::toDomain)

    override fun updateState(sessionId: String, state: Int) {
        dao.updateState(sessionId, state)
    }

    override fun deleteBySessionId(sessionId: String) {
        dao.deleteBySessionId(sessionId)
    }

}
