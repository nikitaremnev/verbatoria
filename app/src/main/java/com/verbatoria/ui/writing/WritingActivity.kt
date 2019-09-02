package com.verbatoria.ui.writing

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.infrastructure.extensions.getDrawableFromRes
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

private const val EVENT_ID_EXTRA = "event_id_extra"

interface WritingView : BaseView {

    fun setActivityNewState(activityCode: ActivityCode)

    fun setActivitySelectedState(activityCode: ActivityCode)

    fun setActivityDoneState(activityCode: ActivityCode)

    interface Callback {

        fun onCodeButtonClicked(activityCode: ActivityCode)

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

    private lateinit var activityNewStateDrawable: Drawable
    private lateinit var activitySelectedStateDrawable: Drawable
    private lateinit var activityDoneStateDrawable: Drawable

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

        activityNewStateDrawable = getDrawableFromRes(R.drawable.background_code_button_state_new)
        activitySelectedStateDrawable = getDrawableFromRes(R.drawable.background_code_button_state_selected)
        activityDoneStateDrawable = getDrawableFromRes(R.drawable.background_code_button_state_done)

        code11Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_11)
        }
        code21Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_21)
        }
        code31Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_31)
        }
        code41Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_41)
        }
        code51Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_51)
        }
        code61Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_61)
        }
        code71Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_71)
        }
        code99Button.setOnClickListener {
            presenter.onCodeButtonClicked(ActivityCode.CODE_99)
        }
    }

    //endregion

    //region WritingView

    override fun setActivityNewState(activityCode: ActivityCode) {
        getButtonByActivityCode(activityCode).background = activityNewStateDrawable
    }

    override fun setActivitySelectedState(activityCode: ActivityCode) {
        getButtonByActivityCode(activityCode).background = activitySelectedStateDrawable
    }

    override fun setActivityDoneState(activityCode: ActivityCode) {
        getButtonByActivityCode(activityCode).background = activityDoneStateDrawable
    }

    //endregion

    private fun getButtonByActivityCode(activityCode: ActivityCode): Button =
        when (activityCode) {
            ActivityCode.CODE_11 -> code11Button
            ActivityCode.CODE_21 -> code21Button
            ActivityCode.CODE_31 -> code31Button
            ActivityCode.CODE_41 -> code41Button
            ActivityCode.CODE_51 -> code51Button
            ActivityCode.CODE_61 -> code61Button
            ActivityCode.CODE_71 -> code71Button
            ActivityCode.CODE_99 -> code99Button
        }

}
