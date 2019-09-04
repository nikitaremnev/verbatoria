package com.verbatoria

import android.app.Application
import android.util.Log
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

    private var bciConnectionController: BCIConnectionController? = null

    private var bciConnectionHandler: BCIConnectionHandler = BCIConnectionHandlerImpl()

    //region Application

    override fun onCreate() {
        super.onCreate()
        initDependencies()
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
        Log.e("test", "VerbatoriaKtApplication startConnection")

        if (bciConnectionController == null) {
            bciConnectionController = BCIConnectionControllerImpl(bciConnectionHandler)
        }
        bciConnectionController?.startConnection()
    }

    fun stopConnection() {
        bciConnectionController?.stopConnection()
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