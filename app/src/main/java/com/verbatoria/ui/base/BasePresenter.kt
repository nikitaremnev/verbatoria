package com.verbatoria.ui.base

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author n.remnev
 */

open class BasePresenter<View : BaseView> {

    protected var view: View? = null

    private val compositeDisposable = CompositeDisposable()

    init {
        //empty
    }

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
        compositeDisposable.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}