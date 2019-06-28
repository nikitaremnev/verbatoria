package com.verbatoria.infrastructure.database.common.dao

/**
 * @author n.remnev
 */

interface DaoFactory {

    fun <T : Entity> createDao(tClass: Class<T>): Dao<T>

}