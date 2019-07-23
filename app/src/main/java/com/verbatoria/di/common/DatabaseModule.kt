package com.verbatoria.di.common

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.verbatoria.domain.authorization.AuthorizationRepository
import com.verbatoria.domain.authorization.AuthorizationRepositoryImpl
import com.verbatoria.domain.dashboard.calendar.CalendarRepository
import com.verbatoria.domain.dashboard.calendar.CalendarRepositoryImpl
import com.verbatoria.domain.dashboard.info.InfoRepository
import com.verbatoria.domain.dashboard.info.InfoRepositoryImpl
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.dashboard.settings.SettingsRepositoryImpl
import com.verbatoria.infrastructure.database.common.room.MainRoomDatabase
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


}