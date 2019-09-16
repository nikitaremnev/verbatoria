package com.verbatoria

import android.app.Application
import com.facebook.stetho.Stetho
import com.verbatoria.business.dashboard.LocalesAvailable
import com.verbatoria.component.connection.*
import com.verbatoria.di.DaggerInjector
import com.verbatoria.di.DependencyHolder
import com.verbatoria.di.Injector
import com.verbatoria.domain.authorization.manager.UserInteractionTimerTask
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

/***
 * @author n.remnev
 */

class VerbatoriaKtApplication : Application(),
    DependencyHolder<Any?> {

    companion object {

        lateinit var injector: Injector

        var currentLocale: String = LocalesAvailable.RUSSIAN_LOCALE

    }

    private val dependenciesContainer = ConcurrentHashMap<String, Any>()

    private var bciConnectionController: BCIConnectionController? = null

    private var bciConnectionHandler: BCIConnectionHandler = BCIConnectionHandlerImpl()

    private var userInteractionTimerTask: UserInteractionTimerTask? = null

    @Inject
    lateinit var settingsRepository: SettingsRepository

    //region Application

    override fun onCreate() {
        super.onCreate()
        initDependencies()

        currentLocale = settingsRepository.getCurrentLocale()

        Stetho.initializeWithDefaults(this)
    }

    //endregion

    //region BCI connection

    fun setBCIDataCallback(presenter: BCIDataCallback?) {
        bciConnectionHandler.setDataCallback(presenter)
    }

    fun setBCIConnectionStateCallback(presenter: BCIConnectionStateCallback?) {
        bciConnectionHandler.setStateCallback(presenter)
    }

    fun startConnection() {
        if (bciConnectionController == null) {
            bciConnectionController = BCIConnectionControllerImpl(bciConnectionHandler)
        }
        bciConnectionController?.startConnection()
    }

    fun startWriting() {
        bciConnectionController?.startWriting()
    }

    fun stopConnection() {
        bciConnectionController?.stopConnection()
    }

    fun updateCurrentLocale(locale: String) {
        currentLocale = locale
    }

    //endregion
    
    //region user activity

    fun onUserInteraction() {
        if (userInteractionTimerTask == null) {
            userInteractionTimerTask = UserInteractionTimerTask()
        } else {
            userInteractionTimerTask?.updateLastInteractionTime()
        }
    }

    fun onSmsConfirmationPassed() {
        if (userInteractionTimerTask == null) {
            userInteractionTimerTask = UserInteractionTimerTask()
        } else {
            userInteractionTimerTask?.dropTimerTaskState()
        }
    }


    //endregion

    //region DependencyHolder

    override val component: Any?
        get() = null

    override fun put(key: String, dependency: Any) {
        dependenciesContainer[key] = dependency
    }

    @Suppress("UNCHECKED_CAST")
    override fun <D> pop(key: String): D? =
        dependenciesContainer.remove(key) as D

    //endregion

    private fun initDependencies() {
        injector = DaggerInjector.builder()
            .context(this)
            .build()
        injector.inject(this)
    }

}