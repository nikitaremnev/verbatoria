package com.verbatoria.presentation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.di.DependencyHolder
import com.verbatoria.di.base.BaseInjector
import com.verbatoria.di.common.Injector
import javax.inject.Inject

/**
 * @author n.remnev
 */

abstract class BasePresenterActivity<V : BaseView, Presenter : BasePresenter<V>, Injected : Any, Component : BaseInjector<Injected>> :
    AppCompatActivity(),
    DependencyHolder<Component>,
    BaseView {

    override lateinit var component: Component

    @Inject
    lateinit var presenter: Presenter

    protected lateinit var dependencyHolder: DependencyHolder<Any>

    //region activity lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("UNCHECKED_CAST")
        dependencyHolder = applicationContext as DependencyHolder<Any>

        @Suppress("UNCHECKED_CAST")
        component = dependencyHolder.pop<Component>(getDependencyKey())
            ?: buildComponent(VerbatoriaApplication.getInjector(), savedInstanceState)

        inject(component)

        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        initViews(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        presenter.onAttachView(this as V)
    }

    override fun onDestroy() {
        presenter.onDetachView()

        if (!isFinishing) {
            dependencyHolder.put(getDependencyKey(), component)
        } else {
            presenter.onDestroy()
        }

        super.onDestroy()
    }

    //endregion

    //region DependencyHolder

    override fun put(key: String, dependency: Any) {
        dependencyHolder.put(key, dependency)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <D> pop(key: String): D? =
        dependencyHolder.pop(key)

    //endregion

    //region dependency

    protected open fun getDependencyKey(): String = localClassName

    @Suppress("UNCHECKED_CAST")
    protected fun inject(component: Component) {
        component.inject(this as Injected)
    }

    //endregion

    //region abstract methods

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun buildComponent(injector: Injector, savedState: Bundle?): Component

    protected abstract fun initViews(savedState: Bundle?)

    //endregion

}