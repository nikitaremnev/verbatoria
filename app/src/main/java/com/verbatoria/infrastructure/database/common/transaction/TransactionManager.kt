package com.verbatoria.infrastructure.database.common.transaction

/**
 * @author n.remnev
 */

interface TransactionManager {

    fun <T> transactional(function: () -> T): T

    fun inTransaction(): Boolean

    fun beginTransaction()

    fun commitTransaction()

    fun rollbackTransaction()

}