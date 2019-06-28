package com.verbatoria.infrastructure.database.common.room

import android.arch.persistence.db.SimpleSQLiteQuery
import android.arch.persistence.db.SupportSQLiteQueryBuilder
import com.verbatoria.infrastructure.conversion.DatabaseConverter
import com.verbatoria.infrastructure.database.common.dao.room.RoomDefaultDao
import com.verbatoria.infrastructure.database.common.exception.EntityNotFoundException

/**
 * @author n.remnev
 */

open class CommonRepository<in Entity, Domain>(
    private val dao: RoomDefaultDao<Entity>,
    private val converter: DatabaseConverter<Entity, Domain>,
    private val entity: Class<Entity>
) {

    open fun save(domain: Domain) {
        dao.save(converter.toEntity(domain))
    }

    open fun updateOrCreate(domain: Domain) {
        converter.toEntity(domain)
            .takeIf { entity -> dao.update(entity) == 0 }
            ?.let(dao::save)
    }

    open fun save(domains: List<Domain>) {
        dao.save(domains.map(converter::toEntity))
    }

    open fun delete(domain: Domain) {
        dao.delete(converter.toEntity(domain))
    }

    fun delete(domains: List<Domain>) {
        dao.delete(domains.map(converter::toEntity))
    }

    fun findAll(): List<Domain> =
        SupportSQLiteQueryBuilder.builder(entity.simpleName).create()
            .let(dao::findAll)
            .map(converter::toDomain)

    fun findAllSorted(field: String, ascending: Boolean): List<Domain> =
        SupportSQLiteQueryBuilder.builder(entity.simpleName)
            .orderBy(if (ascending) field else "$field DESC")
            .create()
            .let(dao::findAll)
            .map(converter::toDomain)

    open fun findById(id: String): Domain? =
        dao.findById(
            SimpleSQLiteQuery(
                "SELECT * FROM ${entity.simpleName} WHERE id=? LIMIT 1",
                arrayOf(id)
            )
        )
            .firstOrNull()
            ?.let(converter::toDomain)

    open fun getById(id: String): Domain =
        dao.findById(
            SimpleSQLiteQuery(
                "SELECT * FROM ${entity.simpleName} WHERE id=? LIMIT 1",
                arrayOf(id)
            )
        )
            .firstOrNull()
            ?.let(converter::toDomain)
            ?: throw EntityNotFoundException(id, entity)

    fun dropTable() {
        dao.dropTable(SimpleSQLiteQuery("DELETE FROM ${entity.simpleName}"))
    }

}