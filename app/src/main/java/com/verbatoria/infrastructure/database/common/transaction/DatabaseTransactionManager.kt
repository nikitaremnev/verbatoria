package com.verbatoria.infrastructure.database.common.transaction

import com.verbatoria.infrastructure.database.common.room.MainRoomDatabase

/**
 * @author n.remnev
 */

class DatabaseTransactionManager(
        private val database: MainRoomDatabase
) : TransactionManager {

    override fun <T> transactional(function: () -> T): T =
            database.runInTransaction<T> { function() }

    override fun inTransaction(): Boolean =
            database.inTransaction()

    override fun beginTransaction() =
            database.beginTransaction()

    override fun commitTransaction() {
        database.setTransactionSuccessful()
        database.endTransaction()
    }

    override fun rollbackTransaction() {
        database.endTransaction()
    }

}