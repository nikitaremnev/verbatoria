package com.verbatoria.di.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.verbatoria.component.session.SessionServiceControllerImpl
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.domain.authorization.AuthorizationManagerImpl
import com.verbatoria.domain.authorization.AuthorizationRepository
import com.verbatoria.domain.session.PreferencesSessionProvider
import com.verbatoria.domain.session.SessionManager
import com.verbatoria.domain.session.SessionManagerImpl
import com.verbatoria.domain.session.SessionProvider
import com.verbatoria.infrastructure.file.FileUtil
import com.verbatoria.infrastructure.file.FileUtilImpl
import com.verbatoria.infrastructure.retrofit.EndpointsRegister
import com.verbatoria.infrastructure.retrofit.EndpointsRegisterImpl
import com.verbatoria.infrastructure.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author n.remnev
 */

private const val DEFAULT_PREFERENCES_SUFFIX = "_preferences"

@Module(includes = [RxSchedulersModule::class, DatabaseModule::class])
class CommonModule {

    @Provides
    @Singleton
    fun provideRetrofit(sessionManager: SessionManager) =
        RetrofitFactory(sessionManager).createRetrofit()

    @Provides
    @Singleton
    fun provideEndpointRegistry(retrofit: Retrofit): EndpointsRegister =
        EndpointsRegisterImpl(retrofit)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(
            context.packageName + DEFAULT_PREFERENCES_SUFFIX,
            Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideSessionProvider(sharedPreferences: SharedPreferences): SessionProvider =
        PreferencesSessionProvider(sharedPreferences)

    //region Managers

    @Provides
    @Singleton
    fun provideSessionManager(
        context: Context,
        sessionProvider: SessionProvider
    ): SessionManager =
        SessionManagerImpl(
            SessionServiceControllerImpl(context),
            sessionProvider
        )

    @Provides
    @Singleton
    fun provideAuthorizationManager(
        endpointsRegister: EndpointsRegister,
        authorizationRepository: AuthorizationRepository
    ): AuthorizationManager =
        AuthorizationManagerImpl(
            endpointsRegister.authorizationEndpoint,
            authorizationRepository
        )

    //endregion

    //region FileUtil

    @Provides
    @Singleton
    fun provideFileUtil(
        context: Context
    ): FileUtil =
        FileUtilImpl(context)

    //endregion

    //region Gson

    @Provides
    @Singleton
    fun provideGson() =
        GsonBuilder()
            .setLenient()
            .create()

    //endregion

}