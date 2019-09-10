package com.verbatoria.ui.submit

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.submit.SubmitComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

interface SubmitView : BaseView {

    fun setStatus(status: String)

    interface Callback {



    }

}

class SubmitActivity : BasePresenterActivity<SubmitView, SubmitPresenter, SubmitActivity, SubmitComponent>(), SubmitView {

    private lateinit var progressBar: ProgressBar
    private lateinit var statusTextView: TextView

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_submit_new

    override fun buildComponent(injector: Injector, savedState: Bundle?): SubmitComponent =
        injector.plusSubmitComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        progressBar = findViewById(R.id.progress_bar)
        statusTextView = findViewById(R.id.status_text_view)
    }

    //endregion

    //region SubmitView

    override fun setStatus(status: String) {
        statusTextView.text = status
    }

    //endregion

}
