package com.verbatoria.infrastructure.database.common.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.infrastructure.database.common.converter.DateRoomConverter
import com.verbatoria.infrastructure.database.entity.LateSendEntity
import com.verbatoria.infrastructure.database.entity.age_group.AgeGroupDao
import com.verbatoria.infrastructure.database.entity.age_group.AgeGroupEntity

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
