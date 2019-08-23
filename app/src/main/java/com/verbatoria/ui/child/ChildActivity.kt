package com.verbatoria.ui.child

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.child.ChildComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author nikitaremnev
 */

interface ChildView : BaseView {



    interface Callback {



    }

}

class ChildActivity : BasePresenterActivity<ChildView, ChildPresenter, ChildActivity, ChildComponent>(), ChildView {

    companion object {

        fun createIntent(
            context: Context,
            eventDetailMode: EventDetailMode
        ): Intent =
            Intent(context, ChildActivity::class.java)

    }
    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_splash

    override fun buildComponent(injector: Injector, savedState: Bundle?): ChildComponent =
        injector.plusChildComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        //empty
    }

    //endregion

    //region ChildView



    //endregion

}
