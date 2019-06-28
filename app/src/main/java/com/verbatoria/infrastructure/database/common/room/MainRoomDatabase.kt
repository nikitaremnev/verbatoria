package com.verbatoria.infrastructure.database.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.infrastructure.database.common.converter.DateRoomConverter

/**
 * @author n.remnev
 */

@Database(
    entities = [
    ],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    DateRoomConverter::class
)
abstract class MainRoomDatabase : RoomDatabase()
