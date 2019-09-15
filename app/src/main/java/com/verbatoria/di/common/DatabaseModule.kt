package com.verbatoria.di.common

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.verbatoria.domain.activities.repository.ActivitiesRepository
import com.verbatoria.domain.activities.repository.ActivitiesRepositoryImpl
import com.verbatoria.domain.authorization.repository.AuthorizationRepository
import com.verbatoria.domain.authorization.repository.AuthorizationRepositoryImpl
import com.verbatoria.domain.bci_data.repository.BCIDataConverter
import com.verbatoria.domain.bci_data.repository.BCIDataRepository
import com.verbatoria.domain.bci_data.repository.BCIDataRepositoryImpl
import com.verbatoria.domain.dashboard.calendar.repository.CalendarRepository
import com.verbatoria.domain.dashboard.calendar.repository.CalendarRepositoryImpl
import com.verbatoria.domain.dashboard.info.repository.AgeGroupRepository
import com.verbatoria.domain.dashboard.info.repository.AgeGroupRepositoryImpl
import com.verbatoria.domain.dashboard.info.repository.InfoRepository
import com.verbatoria.domain.dashboard.info.repository.InfoRepositoryImpl
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.dashboard.settings.SettingsRepositoryImpl
import com.verbatoria.domain.late_send.repository.LateSendConverter
import com.verbatoria.domain.late_send.repository.LateSendRepository
import com.verbatoria.domain.late_send.repository.LateSendRepositoryImpl
import com.verbatoria.domain.questionnaire.repository.QuestionnaireConverter
import com.verbatoria.domain.questionnaire.repository.QuestionnaireRepository
import com.verbatoria.domain.questionnaire.repository.QuestionnaireRepositoryImpl
import com.verbatoria.infrastructure.db.room.MainRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author n.remnev
 */

private const val ROOM_DATABASE_NAME = "verbatoria.db"

private const val AUTHORIZATION_SHARED_PREFERENCES = "authorization"
private const val INFO_SHARED_PREFERENCES = "info"
private const val SETTINGS_SHARED_PREFERENCES = "settings"
private const val CALENDAR_SHARED_PREFERENCES = "calendar"

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): MainRoomDatabase =
        Room.databaseBuilder(context, MainRoomDatabase::class.java, ROOM_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .build()

    @Provides
    @Singleton
    fun provideAuthorizationRepository(context: Context): AuthorizationRepository =
        AuthorizationRepositoryImpl(
            context.getSharedPreferences(AUTHORIZATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun provideInfoRepository(context: Context): InfoRepository =
        InfoRepositoryImpl(
            context.getSharedPreferences(INFO_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun provideSettingsRepository(context: Context): SettingsRepository =
        SettingsRepositoryImpl(
            context.getSharedPreferences(SETTINGS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun provideCalendarRepository(context: Context): CalendarRepository =
        CalendarRepositoryImpl(
            context.getSharedPreferences(CALENDAR_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        )

    @Provides
    @Singleton
    fun provideAgeGroupRepository(mainRoomDatabase: MainRoomDatabase): AgeGroupRepository =
        AgeGroupRepositoryImpl(mainRoomDatabase.ageGroupDao())

    @Provides
    @Singleton
    fun provideQuestionnaireRepository(mainRoomDatabase: MainRoomDatabase): QuestionnaireRepository =
        QuestionnaireRepositoryImpl(mainRoomDatabase.questionnaireDao(), QuestionnaireConverter())

    @Provides
    @Singleton
    fun provideActivitiesRepository(mainRoomDatabase: MainRoomDatabase): ActivitiesRepository =
        ActivitiesRepositoryImpl(mainRoomDatabase.activitiesDao())

    @Provides
    @Singleton
    fun provideBCIDataRepository(mainRoomDatabase: MainRoomDatabase): BCIDataRepository =
        BCIDataRepositoryImpl(mainRoomDatabase.bciDataDao(), BCIDataConverter())

    @Provides
    @Singleton
    fun provideLateSendRepository(mainRoomDatabase: MainRoomDatabase): LateSendRepository =
        LateSendRepositoryImpl(
            mainRoomDatabase.lateSendDao(),
            LateSendConverter()
        )

}