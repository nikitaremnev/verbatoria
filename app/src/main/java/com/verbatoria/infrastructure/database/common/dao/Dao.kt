package com.verbatoria.infrastructure.database.common.dao

/**
 * @author n.remnev
 */

interface Dao<T : Entity> {

    /**
     *  call in transaction
     */
    fun save(entity: T): T

    /**
     *  call in transaction
     */
    fun save(entities: Collection<T>): Collection<T>

    /**
     *  call in transaction
     */
    fun remove(entity: T)

    /**
     *  call in transaction
     */
    fun removeById(id: String)

    /**
     *  call in transaction
     */
    fun remove(query: Query<T>)

    /**
     *  call in transaction
     */
    fun dropTable()

    fun findById(id: String): T?

    fun findAll(): Collection<T>

    fun find(query: Query<T>): List<T>

    fun findFirst(query: Query<T>): T?

    fun createQuery(): Query<T>

    fun count(query: Query<T>): Long

    fun <E> executeInTransaction(lambda : () -> E) : E

}