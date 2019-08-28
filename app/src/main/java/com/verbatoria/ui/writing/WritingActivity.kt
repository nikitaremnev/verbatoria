package com.verbatoria.ui.writing

import android.os.Bundle
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

interface WritingView : BaseView {


    interface Callback {



    }

}

class WritingActivity : BasePresenterActivity<WritingView, WritingPresenter, WritingActivity, WritingComponent>(),
    WritingView {

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_splash

    override fun buildComponent(injector: Injector, savedState: Bundle?): WritingComponent =
        injector.plusWritingComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        //empty
    }

    //endregion

    //region WritingView



    //endregion

}
