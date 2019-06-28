package com.verbatoria.infrastructure.database.common

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class DatabaseInitializer : RoomDatabase.Callback() {

    companion object {
        private const val RU_NAME_REGEX = "[А-Яа-яЁё \\-]+"
        private const val REQUIRED_NAME_REGEX = "[А-Яа-яЁёA-Za-z \\-]+"
        private const val OPTIONAL_NAME_REGEX = "[А-Яа-яЁёA-Za-z \\-]*"
        private const val DATE_REGEX = "\\d{2}\\.\\d{2}\\.(19|20)\\d{2}"
        private const val NOT_EMPTY_REGEX = "(?s).+"
    }

    private val logger = LoggerFactory.getLogger("DatabaseInitializer")

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        db.beginTransaction()
        try {
//            createDocumentTypes(db)
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            logger.error("DatabaseInitializer", e.message, e)
        } finally {
            db.endTransaction()
        }

    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
    }

}