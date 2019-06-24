package com.verbatoria

import android.app.Application
import com.verbatoria.di.DependencyHolder
import com.verbatoria.di.common.Injector
import java.util.concurrent.ConcurrentHashMap
//import com.verbatoria.di.common.DaggerInjector


/***
 * @author n.remnev
 */

class VerbatoriaKtApplication
//
//    : Application(), DependencyHolder<Any?> {
//
//    lateinit var injector: Injector
//
//    private val dependenciesContainer = ConcurrentHashMap<String, Any>()
//
//    //region Application
//
//    override fun onCreate() {
//        super.onCreate()
//        initDependencies()
//    }
//
//    //endregion
//
//    //region DependencyHolder
//
//    override val component: Any?
//        get() = null
//
//    override fun put(key: String, dependency: Any) {
//        dependenciesContainer[key] = dependency
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <D> pop(key: String): D? =
//        dependenciesContainer.remove(key) as D
//
//    //endregion
//
//    private fun initDependencies() {
//        injector = DaggerInjector.builder()
//            .context(this)
//            .build()
//        injector.inject(this)
//    }
//
//}