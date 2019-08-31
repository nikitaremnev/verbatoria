package com.verbatoria.infrastructure.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.remnev.verbatoria.BuildConfig
import com.verbatoria.domain.activities.repository.ActivitiesDao
import com.verbatoria.infrastructure.db.converter.DateRoomConverter
import com.verbatoria.domain.late_send.LateSendEntity
import com.verbatoria.domain.dashboard.info.repository.AgeGroupDao
import com.verbatoria.domain.dashboard.info.model.AgeGroupEntity
import com.verbatoria.domain.questionnaire.repository.QuestionnaireDao
import com.verbatoria.domain.questionnaire.model.QuestionnaireEntity

/**
 * @author n.remnev
 */

@Database(
    entities = [
        LateSendEntity::class,
        AgeGroupEntity::class,
        QuestionnaireEntity::class
    ],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(
    DateRoomConverter::class
)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun ageGroupDao(): AgeGroupDao

    abstract fun questionnaireDao(): QuestionnaireDao

    abstract fun activitiesDao(): ActivitiesDao

}
