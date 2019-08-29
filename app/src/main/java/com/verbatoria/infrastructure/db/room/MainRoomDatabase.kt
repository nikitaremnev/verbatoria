package com.verbatoria.infrastructure.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.infrastructure.db.converter.DateRoomConverter
import com.verbatoria.domain.late_send.LateSendEntity
import com.verbatoria.domain.dashboard.info.repository.AgeGroupDao
import com.verbatoria.domain.dashboard.info.model.AgeGroupEntity

/**
 * @author n.remnev
 */

@Database(
    entities = [
        LateSendEntity::class,
        AgeGroupEntity::class
    ],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    DateRoomConverter::class
)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun ageGroupDao(): AgeGroupDao

}
