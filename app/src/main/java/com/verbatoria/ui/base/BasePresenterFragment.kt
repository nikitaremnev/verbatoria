package com.verbatoria.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.verbatoria.di.DependencyHolder
import com.verbatoria.di.BaseInjector
import javax.inject.Inject

/**
 * @author n.remnev
 */

abstract class BasePresenterFragment<PresenterView : BaseView, in ParentComponent, Injected : Any, ChildComponent : BaseInjector<Injected>, Presenter : BasePresenter<PresenterView>>
    : Fragment(), BaseView {

    @Inject
    protected lateinit var presenter: Presenter

    private lateinit var component: ChildComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        val dependencyHolder = context as DependencyHolder<ParentComponent>

        component = dependencyHolder.pop<ChildComponent>(getDependencyKey())
            ?: buildComponent(dependencyHolder.component, savedInstanceState)

        inject(component)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("UNCHECKED_CAST")
        presenter.onAttachView(this as PresenterView)
    }

    override fun onDestroyView() {
        presenter.onDetachView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        activity?.also { activity ->
            if (isNeedToSaveDependencies()) {
                @Suppress("UNCHECKED_CAST")
                (activity as DependencyHolder<ParentComponent>).put(getDependencyKey(), component)
            } else {
                presenter.onDestroy()
            }
        }
        super.onDestroy()
    }

    protected fun isNeedToSaveDependencies() =
        !(isRemoving || activity?.isFinishing ?: true)

    protected fun inject(component: ChildComponent) {
        @Suppress("UNCHECKED_CAST")
        component.inject(this as Injected)
    }

    protected open fun getDependencyKey(): String =
        javaClass.name

    protected abstract fun buildComponent(
        parentComponent: ParentComponent,
        savedState: Bundle?
    ): ChildComponent

}