package com.verbatoria.di

import android.content.Context
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.business.session.activities.ActivitiesTimerTask
import com.verbatoria.business.session.activities.UserInteractionTimerTask
import com.verbatoria.business.session.manager.AudioPlayerManager
import com.verbatoria.component.session.SessionService
import com.verbatoria.data.repositories.late_send.LateSendRepository
import com.verbatoria.di.child.ChildComponent
import com.verbatoria.di.client.ClientComponent
import com.verbatoria.di.common.CommonModule
import com.verbatoria.di.dashboard.DashboardComponent
import com.verbatoria.di.event.EventDetailComponent
import com.verbatoria.di.late_send.LateSendComponent
import com.verbatoria.di.login.LoginComponent
import com.verbatoria.di.login.sms.SMSLoginComponent
import com.verbatoria.di.questionnaire.QuestionnaireComponent
import com.verbatoria.di.recovery_password.RecoveryPasswordComponent
import com.verbatoria.di.schedule.ScheduleComponent
import com.verbatoria.di.session.SessionComponent
import com.verbatoria.di.session.SessionModule
import com.verbatoria.di.splash.SplashComponent
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.ui.session.view.submit.questions.QuestionViewHolder
import com.verbatoria.ui.session.view.submit.questions.QuestionsAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author n.remnev
 */

@Singleton
@Component(modules = [CommonModule::class])
interface Injector {

    fun inject(application: VerbatoriaKtApplication)

    fun inject(lateSendRepository: LateSendRepository)

    fun inject(activitiesTimerTask: ActivitiesTimerTask)

    fun inject(audioPlayerManager: AudioPlayerManager)

    fun inject(questionsAdapter: QuestionsAdapter)

    fun inject(questionViewHolder: QuestionViewHolder)

    fun inject(userInteractionTimerTask: UserInteractionTimerTask)

    fun inject(sessionService: SessionService)

    fun addModule(sessionModule: SessionModule): SessionComponent

    fun plusSplashComponent(): SplashComponent.Builder

    fun plusLateSendComponent(): LateSendComponent.Builder

    fun plusLoginComponent(): LoginComponent.Builder

    fun plusSMSLoginComponent(): SMSLoginComponent.Builder

    fun plusRecoveryPasswordComponent(): RecoveryPasswordComponent.Builder

    fun plusDashboardComponent(): DashboardComponent.Builder

    fun plusEventDetailComponent(): EventDetailComponent.Builder

    fun plusScheduleComponent(): ScheduleComponent.Builder

    fun plusClientComponent(): ClientComponent.Builder

    fun plusChildComponent(): ChildComponent.Builder

    fun plusWritingComponent(): WritingComponent.Builder

    fun plusQuestionnaireComponent(): QuestionnaireComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): Injector

    }

}