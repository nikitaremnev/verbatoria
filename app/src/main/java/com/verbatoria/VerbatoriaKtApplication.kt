package com.verbatoria

import android.app.Application
import com.verbatoria.component.connection.*
import com.verbatoria.di.DaggerInjector
import com.verbatoria.di.DependencyHolder
import com.verbatoria.di.Injector
import java.util.concurrent.ConcurrentHashMap

/***
 * @author n.remnev
 */

class VerbatoriaKtApplication : Application(),
    DependencyHolder<Any?> {

    lateinit var injector: Injector

    private val dependenciesContainer = ConcurrentHashMap<String, Any>()

    private var neurodataConnectionController: NeurodataConnectionController? = null

    private var neurodataConnectionHandler: NeurodataConnectionHandler = NeurodataConnectionHandlerImpl()

    //region Application

    override fun onCreate() {
        super.onCreate()
        initDependencies()
    }

    //endregion

    //region BCI connection

    fun setNeurodataDataCallback(presenter: NeurodataConnectionDataCallback?) {
        neurodataConnectionHandler.setDataCallback(presenter)
    }

    fun setNeurodataConnectionStateCallback(presenter: NeurodataConnectionStateCallback?) {
        neurodataConnectionHandler.setStateCallback(presenter)
    }

    fun startConnection() {
        if (neurodataConnectionController == null) {
            neurodataConnectionController = NeurodataConnectionControllerImpl(neurodataConnectionHandler)
        }
        neurodataConnectionController?.startConnection()
    }

    fun stopConnection() {
        neurodataConnectionController?.stopConnection()
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