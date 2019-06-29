package com.verbatoria.infrastructure.database.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.infrastructure.database.common.converter.DateRoomConverter
import com.verbatoria.infrastructure.database.entity.late_send.LateSendEntity

/**
 * @author n.remnev
 */

@Database(
    entities = [
        LateSendEntity::class
    ],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    DateRoomConverter::class
)
abstract class MainRoomDatabase : RoomDatabase()
