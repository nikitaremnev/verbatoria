package com.verbatoria.di

import android.content.Context
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.business.session.activities.ActivitiesTimerTask
import com.verbatoria.business.session.activities.UserInteractionTimerTask
import com.verbatoria.business.session.manager.AudioPlayerManager
import com.verbatoria.business.token.processor.TokenProcessor
import com.verbatoria.component.session.SessionService
import com.verbatoria.data.repositories.late_send.LateSendRepository
import com.verbatoria.di.calendar.CalendarComponent
import com.verbatoria.di.calendar.CalendarModule
import com.verbatoria.di.common.CommonModule
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.dashboard.DashboardModule
import com.verbatoria.di.late_send.LateSendComponent
import com.verbatoria.di.login.AuthorizationComponent
import com.verbatoria.di.login.AuthorizationModule
import com.verbatoria.di.login.LoginComponent
import com.verbatoria.di.recovery_password.RecoveryPasswordComponent
import com.verbatoria.di.schedule.ScheduleComponent
import com.verbatoria.di.schedule.ScheduleModule
import com.verbatoria.di.session.SessionComponent
import com.verbatoria.di.session.SessionModule
import com.verbatoria.di.splash.SplashComponent
import com.verbatoria.di.token.TokenComponentInjects
import com.verbatoria.di.token.TokenModule
import com.verbatoria.ui.calendar.view.adapter.EventsAdapter
import com.verbatoria.ui.calendar.view.add.children.search.ClientsAdapter
import com.verbatoria.ui.calendar.view.add.clients.search.ChildrenAdapter
import com.verbatoria.ui.session.view.submit.questions.QuestionViewHolder
import com.verbatoria.ui.session.view.submit.questions.QuestionsAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author n.remnev
 */

@Singleton
@Component(modules = [TokenModule::class, CommonModule::class])
interface Injector : TokenComponentInjects {

    fun inject(application: VerbatoriaKtApplication)

    fun inject(lateSendRepository: LateSendRepository)

    fun inject(tokenProcessor: TokenProcessor)

    fun inject(eventsAdapter: EventsAdapter)

    fun inject(clientsAdapter: ClientsAdapter)

    fun inject(childrenAdapter: ChildrenAdapter)

    fun inject(activitiesTimerTask: ActivitiesTimerTask)

    fun inject(audioPlayerManager: AudioPlayerManager)

    fun inject(questionsAdapter: QuestionsAdapter)

    fun inject(questionViewHolder: QuestionViewHolder)

    fun inject(userInteractionTimerTask: UserInteractionTimerTask)

    fun inject(sessionService: SessionService)

    fun addModule(loginModule: AuthorizationModule): AuthorizationComponent

    fun addModule(dashboardModule: DashboardModule): DashboardComponent

    fun addModule(sessionModule: SessionModule): SessionComponent

    fun addModule(calendarModule: CalendarModule): CalendarComponent

    fun addModule(scheduleModule: ScheduleModule): ScheduleComponent

    fun plusSplashComponent(): SplashComponent.Builder

    fun plusLateSendComponent(): LateSendComponent.Builder

    fun plusLoginComponent(): LoginComponent.Builder

    fun plusRecoveryPasswordComponent(): RecoveryPasswordComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): Injector

    }

}