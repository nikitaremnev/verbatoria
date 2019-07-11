package com.verbatoria.di.common

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.verbatoria.infrastructure.database.common.room.MainRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author n.remnev
 */

private const val ROOM_DATABASE_NAME = "verbatoria.db"

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): MainRoomDatabase =
        Room.databaseBuilder(context, MainRoomDatabase::class.java, ROOM_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .build()

//    @Provides
//    @Singleton
//    fun provideArchiveRepository(database: MainRoomDatabase): ArchiveRepository =
//        ArchiveRepositoryImpl(
//            database.archiveDao(),
//            ArchiveConverter()
//        )
//
//    @Provides
//    @Singleton
//    fun provideArticleRepository(database: MainRoomDatabase): ArticleRepository =
//        ArticleRepositoryImpl(
//            database.articleDao(),
//            ArticleConverter()
//        )
//
//    @Provides
//    @Singleton
//    fun provideAuthorizationRepository(context: Context): AuthorizationRepository =
//        AuthorizationRepositoryImpl(
//            context.getSharedPreferences(AUTHORIZATION_SHARED_PREFERENCES, Context.MODE_PRIVATE)
//        )

}