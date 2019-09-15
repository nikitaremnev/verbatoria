package com.verbatoria.di

import android.content.Context
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.component.session.SessionService
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
import com.verbatoria.di.splash.SplashComponent
import com.verbatoria.di.submit.SubmitComponent
import com.verbatoria.di.writing.WritingComponent
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

    fun inject(sessionService: SessionService)

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

    fun plusSubmitComponent(): SubmitComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): Injector

    }

}