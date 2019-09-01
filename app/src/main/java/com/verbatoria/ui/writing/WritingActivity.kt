package com.verbatoria.ui.writing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

private const val EVENT_ID_EXTRA = "event_id_extra"

interface WritingView : BaseView {


    interface Callback {



    }

}

class WritingActivity : BasePresenterActivity<WritingView, WritingPresenter, WritingActivity, WritingComponent>(),
    WritingView {

    private lateinit var code99Button: Button
    private lateinit var code11Button: Button
    private lateinit var code21Button: Button
    private lateinit var code31Button: Button
    private lateinit var code41Button: Button
    private lateinit var code51Button: Button
    private lateinit var code61Button: Button
    private lateinit var code71Button: Button

    companion object {

        fun createIntent(
            context: Context,
            eventId: String
        ): Intent =
            Intent(context, WritingActivity::class.java)
                .putExtra(EVENT_ID_EXTRA, eventId)

    }

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_writing

    override fun buildComponent(injector: Injector, savedState: Bundle?): WritingComponent =
        injector.plusWritingComponent()
            .eventId(intent.getStringExtra(EVENT_ID_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        code99Button = findViewById(R.id.code_99_button)
        code11Button = findViewById(R.id.code_11_button)
        code21Button = findViewById(R.id.code_21_button)
        code31Button = findViewById(R.id.code_31_button)
        code41Button = findViewById(R.id.code_41_button)
        code51Button = findViewById(R.id.code_51_button)
        code61Button = findViewById(R.id.code_61_button)
        code71Button = findViewById(R.id.code_71_button)
    }

    //endregion

    //region WritingView



    //endregion

}
