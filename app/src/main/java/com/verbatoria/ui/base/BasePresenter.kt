package com.verbatoria.ui.base

import android.support.annotation.CallSuper
import com.verbatoria.component.event.EventManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author n.remnev
 */

open class BasePresenter<View : BaseView>: EventManager.Subscriber {

    protected var view: View? = null

    private val compositeDisposable = CompositeDisposable()

    @CallSuper
    open fun onAttachView(view: View) {
        this.view = view
    }

    @CallSuper
    open fun onDetachView() {
        view = null
    }

    @CallSuper
    open fun onDestroy() {
        clearDisposables()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    protected fun clearDisposables() {
        compositeDisposable.clear()
    }

    //region EventManager.Subscriber

    protected open fun getEventsKey(): List<String> = emptyList()

    override fun onEvent(key: String, data: Any?) { /* empty */ }

    //endregion

}