package com.verbatoria.ui.writing

import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.infrastructure.extensions.getDrawableFromRes
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
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

    fun updateTimerTime(totalLoadTime: Int)

    fun showFinishButton()

    fun showTimer()

    fun hideTimer()

    fun showFinishActivityFirstError()

    fun setUpPlayMode()

    fun setUpPauseMode()

    fun hidePlayer()

    fun showMusicFileName(fileName: String)

    fun getAssetFileDescriptor(rawMusicFileResource: Int): AssetFileDescriptor?

    fun close()

    interface Callback {

        fun onFinishClicked()

        fun onCodeButtonClicked(activityCode: ActivityCode)

    }

    interface MusicPlayerCallback {

        fun onPlayClicked()

        fun onPauseClicked()

        fun onNextClicked()

        fun onBackClicked()

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

    private lateinit var finishButton: FloatingActionButton

    private lateinit var playButton: FloatingActionButton
    private lateinit var pauseButton: FloatingActionButton
    private lateinit var backButton: FloatingActionButton
    private lateinit var nextButton: FloatingActionButton

    private lateinit var musicFileNameTextView: TextView
    private lateinit var timerTextView: TextView

    private lateinit var lineChart: LineChart

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

        finishButton = findViewById(R.id.finish_button)
        playButton = findViewById(R.id.play_floating_button)
        pauseButton = findViewById(R.id.pause_floating_button)
        backButton = findViewById(R.id.back_floating_button)
        nextButton = findViewById(R.id.next_floating_button)

        musicFileNameTextView = findViewById(R.id.music_file_name_text_view)
        timerTextView = findViewById(R.id.timer_text_view)

        lineChart = findViewById(R.id.line_chart)

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

        playButton.setOnClickListener {
            presenter.onPlayClicked()
        }
        pauseButton.setOnClickListener {
            presenter.onPauseClicked()
        }
        nextButton.setOnClickListener {
            presenter.onNextClicked()
        }
        backButton.setOnClickListener {
            presenter.onBackClicked()
        }

        finishButton.setOnClickListener {
            presenter.onFinishClicked()
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

    override fun updateTimerTime(totalLoadTime: Int) {
        runOnUiThread {
            timerTextView.text = getString(R.string.writing_timer, totalLoadTime)
        }
    }

    override fun showFinishButton() {
        finishButton.show()
    }

    override fun showTimer() {
        timerTextView.show()
    }

    override fun hideTimer() {
        timerTextView.hide()
    }

    override fun showFinishActivityFirstError() {
        showErrorSnackbar(getString(R.string.writing_finish_activity_first_error))
    }

    override fun setUpPlayMode() {
        musicFileNameTextView.show()
        playButton.hide()
        pauseButton.show()
        backButton.show()
        nextButton.show()
    }

    override fun setUpPauseMode() {
        musicFileNameTextView.show()
        pauseButton.hide()
        playButton.show()
        backButton.show()
        nextButton.show()
    }

    override fun hidePlayer() {
        musicFileNameTextView.hide()
        playButton.hide()
        pauseButton.hide()
        backButton.hide()
        nextButton.hide()
    }

    override fun showMusicFileName(fileName: String) {
        musicFileNameTextView.text = fileName
    }

    override fun getAssetFileDescriptor(rawMusicFileResource: Int): AssetFileDescriptor =
        resources.openRawResourceFd(rawMusicFileResource)

    override fun close() {
        finish()
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
