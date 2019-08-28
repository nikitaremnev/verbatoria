package com.verbatoria.di.common

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.verbatoria.component.event.EventManager
import com.verbatoria.component.event.EventManagerImpl
import com.verbatoria.component.session.SessionServiceControllerImpl
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.domain.authorization.AuthorizationManagerImpl
import com.verbatoria.domain.authorization.AuthorizationRepository
import com.verbatoria.domain.child.ChildManager
import com.verbatoria.domain.child.ChildManagerImpl
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.client.ClientManagerImpl
import com.verbatoria.domain.dashboard.calendar.CalendarManager
import com.verbatoria.domain.dashboard.calendar.CalendarManagerImpl
import com.verbatoria.domain.dashboard.calendar.CalendarRepository
import com.verbatoria.domain.dashboard.info.AgeGroupRepository
import com.verbatoria.domain.dashboard.info.InfoManager
import com.verbatoria.domain.dashboard.info.InfoManagerImpl
import com.verbatoria.domain.dashboard.info.InfoRepository
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.report.ReportManager
import com.verbatoria.domain.report.ReportManagerImpl
import com.verbatoria.domain.schedule.ScheduleManager
import com.verbatoria.domain.schedule.ScheduleManagerImpl
import com.verbatoria.domain.session.PreferencesSessionProvider
import com.verbatoria.domain.session.SessionManager
import com.verbatoria.domain.session.SessionManagerImpl
import com.verbatoria.domain.session.SessionProvider
import com.verbatoria.infrastructure.file.FileUtil
import com.verbatoria.infrastructure.file.FileUtilImpl
import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.EndpointsRegister
import com.verbatoria.infrastructure.retrofit.EndpointsRegisterImpl
import com.verbatoria.infrastructure.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author n.remnev
 */

private const val DEFAULT_PREFERENCES_SUFFIX = "_preferences"

private const val BASE_RETROFIT = "BASE_RETROFIT"
private const val PANEL_RETROFIT = "PANEL_RETROFIT"

@Module(includes = [RxSchedulersModule::class, DatabaseModule::class])
class CommonModule {

    @Provides
    @Singleton
    @Named(BASE_RETROFIT)
    fun provideRetrofit(sessionManager: SessionManager) =
        RetrofitFactory(sessionManager).createRetrofit(APIConstants.BASE_URL)

    @Provides
    @Singleton
    @Named(PANEL_RETROFIT)
    fun providePanelRetrofit(sessionManager: SessionManager) =
        RetrofitFactory(sessionManager).createRetrofit(APIConstants.API_PANEL_BASE_URL)

    @Provides
    @Singleton
    fun provideEndpointRegistry(
        @Named(BASE_RETROFIT) retrofit: Retrofit,
        @Named(PANEL_RETROFIT) panelRetrofit: Retrofit
    ): EndpointsRegister =
        EndpointsRegisterImpl(retrofit, panelRetrofit)

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

    @Provides
    @Singleton
    fun provideEventManager(): EventManager = EventManagerImpl()

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
        sessionManager: SessionManager,
        endpointsRegister: EndpointsRegister,
        authorizationRepository: AuthorizationRepository,
        infoRepository: InfoRepository,
        settingsRepository: SettingsRepository
    ): AuthorizationManager =
        AuthorizationManagerImpl(
            sessionManager,
            endpointsRegister.authorizationEndpoint,
            endpointsRegister.smsLoginEndpoint,
            authorizationRepository,
            infoRepository,
            settingsRepository
        )

    @Provides
    @Singleton
    fun provideInfoManager(
        infoRepository: InfoRepository,
        settingsRepository: SettingsRepository,
        ageGroupRepository: AgeGroupRepository,
        endpointsRegister: EndpointsRegister
    ): InfoManager =
        InfoManagerImpl(
            infoRepository,
            settingsRepository,
            ageGroupRepository,
            endpointsRegister.infoEndpoint
        )

    @Provides
    @Singleton
    fun provideChildManager(
        endpointsRegister: EndpointsRegister
    ): ChildManager =
        ChildManagerImpl(
            endpointsRegister.childEndpoint
        )

    @Provides
    @Singleton
    fun provideClientManager(
        endpointsRegister: EndpointsRegister
    ): ClientManager =
        ClientManagerImpl(
            endpointsRegister.clientEndpoint
        )

    @Provides
    @Singleton
    fun provideScheduleManager(
        endpointsRegister: EndpointsRegister
    ): ScheduleManager =
        ScheduleManagerImpl(
            endpointsRegister.scheduleEndpoint
        )

    @Provides
    @Singleton
    fun provideCalendarManager(
        infoManager: InfoManager,
        childManager: ChildManager,
        endpointsRegister: EndpointsRegister,
        calendarRepository: CalendarRepository
    ): CalendarManager =
        CalendarManagerImpl(
            infoManager,
            childManager,
            endpointsRegister.calendarEndpoint,
            endpointsRegister.eventEndpoint,
            calendarRepository
        )

    @Provides
    @Singleton
    fun provideReportManager(
        endpointsRegister: EndpointsRegister
    ): ReportManager =
        ReportManagerImpl(
            endpointsRegister.reportEndpoint
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