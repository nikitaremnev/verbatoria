package com.verbatoria.ui.writing

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.remnev.verbatoria.BuildConfig
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.di.Injector
import com.verbatoria.di.writing.WritingComponent
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.infrastructure.extensions.*
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.dialog.ActivitySuggestDialog
import com.verbatoria.ui.questionnaire.QuestionnaireActivity
import com.verbatoria.ui.submit.SubmitActivity

/**
 * @author nikitaremnev
 */

private const val SESSION_ID_EXTRA = "session_id_extra"
private const val CHILD_AGE_EXTRA = "child_age_extra"

private const val BCI_CONNECTION_DIALOG_TAG = "BCI_CONNECTION_DIALOG_TAG"
private const val ZEROS_ERROR_DIALOG_TAG = "ZEROS_ERROR_DIALOG_TAG"

private const val Y_AXIS_MINIMUM = 0f
private const val Y_AXIS_MAXIMUM = 100f
private const val VISIBLE_RANGE_MAXIMUM = 20f
private const val LINE_CHART_WIDTH = 3f
private const val MAX_CHART_VALUES_SIZE = 21

interface WritingView : BaseView {

    fun setActivityNewState(activityCode: ActivityCode)

    fun setActivitySelectedState(activityCode: ActivityCode)

    fun setActivityNotFinishedState(activityCode: ActivityCode)
    
    fun setActivityDoneState(activityCode: ActivityCode)

    fun updateTimerTime(load: Int, totalLoadTime: Int)

    fun showFinishButton()

    fun showTimer()

    fun hideTimer()

    fun showFinishActivityFirstError()

    fun setUpPlayMode()

    fun setUpPauseMode()

    fun hidePlayer()

    fun showMusicFileName(fileName: String)

    fun getAssetFileDescriptor(rawMusicFileResource: Int): AssetFileDescriptor?

    fun addValueToGraph(attentionValue: Int)

    fun openQuestionnaire(sessionId: String, childAge: Int)

    fun openSubmit(sessionId: String)

    fun showBluetoothDisabledDialogState()

    fun showConnectionErrorDialogState()

    fun showConnectingDialogState()

    fun showConnectedDialogState()

    fun showBCIConnectionDialog()

    fun showZerosErrorDialog()

    fun hideZerosErrorDialog()

    fun dismissBCIConnectionDialog()

    fun connectBCI()

    fun startBCIWriting()

    fun disconnectBCI()

    fun openSettings()

    fun finish()

    interface Callback {

        fun onBackPressed()

        fun onFinishClicked()

        fun onCodeButtonClicked(activityCode: ActivityCode)

    }

    interface MusicPlayerCallback {

        fun onPlayClicked()

        fun onPauseClicked()

        fun onNextClicked()

        fun onBackClicked()

    }

    interface BCIConnectionDialogCallback {

        fun onStartClicked()

        fun onConnectClicked()

        fun onExitClicked()

        fun onSettingsClicked()

        fun onTryAgainClicked()

    }

}

class WritingActivity : BasePresenterActivity<WritingView, WritingPresenter, WritingActivity, WritingComponent>(),
    WritingView, BCIConnectionDialog.OnBCIConnectionDialogClickListener  {

    private lateinit var code99Button: Button
    private lateinit var code11Button: Button
    private lateinit var code21Button: Button
    private lateinit var code31Button: Button
    private lateinit var code41Button: Button
    private lateinit var code51Button: Button
    private lateinit var code61Button: Button
    private lateinit var code71Button: Button
    private lateinit var testButton: Button

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
    private lateinit var activityNotFinishedStateDrawable: Drawable
    private lateinit var activityDoneStateDrawable: Drawable

    private var bciConnectionDialog: BCIConnectionDialog? = null
    private var uiHandler = Handler()

    companion object {

        fun createIntent(
            context: Context,
            sessionId: String,
            childAge: Int
        ): Intent =
            Intent(context, WritingActivity::class.java)
                .putExtra(SESSION_ID_EXTRA, sessionId)
                .putExtra(CHILD_AGE_EXTRA, childAge)

    }

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_writing

    override fun buildComponent(injector: Injector, savedState: Bundle?): WritingComponent =
        injector.plusWritingComponent()
            .sessionId(intent.getStringExtra(SESSION_ID_EXTRA))
            .childAge(intent.getIntExtra(CHILD_AGE_EXTRA, 0))
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
        testButton = findViewById(R.id.test_button)

        if (BuildConfig.DEBUG) {
            testButton.visibility = View.VISIBLE
        } else {
            testButton.visibility = View.GONE
        }

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
        activityNotFinishedStateDrawable = getDrawableFromRes(R.drawable.background_code_button_state_not_finished)
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

        testButton.setOnClickListener {
            presenter.onAttentionDataReceived(0)
            presenter.onAttentionDataReceived(0)
            presenter.onAttentionDataReceived(0)
            presenter.onAttentionDataReceived(0)
            presenter.onAttentionDataReceived(0)
            presenter.onAttentionDataReceived(0)
        }

        setUpChart()

        bciConnectionDialog = supportFragmentManager.findFragmentByTag(BCI_CONNECTION_DIALOG_TAG) as? BCIConnectionDialog
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    //endregion

    //region WritingView

    override fun setActivityNewState(activityCode: ActivityCode) {
        uiHandler.post {
            getButtonByActivityCode(activityCode).background = activityNewStateDrawable
        }
    }

    override fun setActivitySelectedState(activityCode: ActivityCode) {
        uiHandler.post {
            getButtonByActivityCode(activityCode).background = activitySelectedStateDrawable
        }
    }

    override fun setActivityNotFinishedState(activityCode: ActivityCode) {
        uiHandler.post {
            getButtonByActivityCode(activityCode).background = activityNotFinishedStateDrawable
        }
    }

    override fun setActivityDoneState(activityCode: ActivityCode) {
        uiHandler.post {
            getButtonByActivityCode(activityCode).background = activityDoneStateDrawable
        }
    }

    override fun updateTimerTime(load: Int, totalLoadTime: Int) {
        uiHandler.post {
            timerTextView.text = getString(R.string.writing_timer, load, totalLoadTime)
        }
    }

    override fun showFinishButton() {
        uiHandler.post {
            finishButton.show()
        }
    }

    override fun showTimer() {
        uiHandler.post {
            timerTextView.show()
        }
    }

    override fun hideTimer() {
        uiHandler.post {
            timerTextView.invisible()
        }
    }

    override fun showFinishActivityFirstError() {
        showErrorSnackbar(getString(R.string.writing_finish_activity_first_error))
    }

    override fun setUpPlayMode() {
        uiHandler.post {
            musicFileNameTextView.show()
            playButton.invisible()
            pauseButton.show()
            backButton.show()
            nextButton.show()
        }
    }

    override fun setUpPauseMode() {
        uiHandler.post {
            musicFileNameTextView.show()
            pauseButton.invisible()
            playButton.show()
            backButton.show()
            nextButton.show()
        }
    }

    override fun hidePlayer() {
        uiHandler.post {
            musicFileNameTextView.invisible()
            playButton.invisible()
            pauseButton.invisible()
            backButton.invisible()
            nextButton.invisible()
        }
    }

    override fun showMusicFileName(fileName: String) {
        uiHandler.post {
            musicFileNameTextView.text = fileName
        }
    }

    override fun getAssetFileDescriptor(rawMusicFileResource: Int): AssetFileDescriptor =
        resources.openRawResourceFd(rawMusicFileResource)

    override fun addValueToGraph(attentionValue: Int) {
        var data: LineData? = lineChart.data
        if (data == null) {
            data = LineData()
            data.setDrawValues(false)
            lineChart.data = data
        }
        var set: ILineDataSet? = data.getDataSetByIndex(0)
        if (set == null) {
            set = createSet()
            data.addDataSet(set)
        }
        data.addXValue(set.entryCount.toString())
        data.addEntry(Entry(attentionValue.toFloat(), set.entryCount), 0)

        lineChart.notifyDataSetChanged()
        lineChart.setVisibleXRangeMaximum(VISIBLE_RANGE_MAXIMUM)
        lineChart.moveViewToX((data.xValCount - MAX_CHART_VALUES_SIZE).toFloat())
    }

    override fun openQuestionnaire(sessionId: String, childAge: Int) {
        startActivity(QuestionnaireActivity.createIntent(this, sessionId, childAge))
        finish()
    }

    override fun openSubmit(sessionId: String) {
        startActivity(SubmitActivity.createIntent(this, sessionId))
        finish()
    }

    override fun showBluetoothDisabledDialogState() {
        uiHandler.post {
            bciConnectionDialog?.showBluetoothDisabled()
        }
    }

    override fun showConnectingDialogState() {
        uiHandler.post {
            bciConnectionDialog?.showConnecting()
        }
    }

    override fun showConnectedDialogState() {
        uiHandler.post {
            bciConnectionDialog?.showConnected()
        }
    }

    override fun showConnectionErrorDialogState() {
        uiHandler.post {
            if (bciConnectionDialog == null) {
                bciConnectionDialog = BCIConnectionDialog.newInstance(true)
                bciConnectionDialog?.show(supportFragmentManager, BCI_CONNECTION_DIALOG_TAG)
            } else {
                bciConnectionDialog?.showConnectionError()
            }
        }
    }

    override fun showBCIConnectionDialog() {
        if (bciConnectionDialog == null) {
            bciConnectionDialog = BCIConnectionDialog.newInstance()
            bciConnectionDialog?.show(supportFragmentManager, BCI_CONNECTION_DIALOG_TAG)
        }
    }

    override fun showZerosErrorDialog() {
        ActivitySuggestDialog.build {
            title = getString(R.string.error)
            message = getString(R.string.session_zeros_error)
            cancelable = false
        }.show(supportFragmentManager, ZEROS_ERROR_DIALOG_TAG)
    }

    override fun hideZerosErrorDialog() {
        (supportFragmentManager.findFragmentByTag(ZEROS_ERROR_DIALOG_TAG) as? DialogFragment)?.dismiss()
    }

    override fun dismissBCIConnectionDialog() {
        bciConnectionDialog?.dismiss()
        bciConnectionDialog = null
    }

    override fun connectBCI() {
        (application as? VerbatoriaKtApplication)?.startConnection()
    }

    override fun startBCIWriting() {
        (application as? VerbatoriaKtApplication)?.startWriting()
    }

    override fun disconnectBCI() {
        (application as? VerbatoriaKtApplication)?.stopConnection()
    }

    override fun openSettings() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    //endregion

    //region BCIConnectionDialog.OnBCIConnectionDialogClickListener

    override fun onConnectClicked(tag: String?) {
        presenter.onConnectClicked()
    }

    override fun onExitClicked(tag: String?) {
        presenter.onExitClicked()
    }

    override fun onStartClicked(tag: String?) {
        presenter.onStartClicked()
    }

    override fun onSettingsClicked(tag: String?) {
        presenter.onSettingsClicked()
    }

    override fun onTryAgainClicked(tag: String?) {
        presenter.onTryAgainClicked()
    }

    //endregion

    //region LineChart

    private fun setUpChart() {
        lineChart.apply {
            setDescription("")
            setNoDataText(getString(R.string.writing_data_empty))

            setScaleEnabled(false);
            setDrawGridBackground(false)

            legend.isEnabled = false

            setBackgroundColor(getColorFromRes(R.color.font))
        }

        setUpXAxis()
        setUpYAxis()

        addZeroLine()
    }

    private fun setUpXAxis() {
        val xl = lineChart.xAxis
        xl.textColor = getColorFromRes(R.color.main)
        xl.setDrawGridLines(false)
        xl.setDrawAxisLine(false)
        xl.setDrawLabels(false)
    }

    private fun setUpYAxis() {
        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = getColorFromRes(R.color.black)
        leftAxis.axisMaxValue = Y_AXIS_MAXIMUM
        leftAxis.axisMinValue = Y_AXIS_MINIMUM
        leftAxis.setDrawGridLines(false)
        leftAxis.setDrawAxisLine(false)
        leftAxis.setDrawLabels(true)
        val rightAxis = lineChart.axisRight
        rightAxis.isEnabled = false
    }

    private fun addZeroLine() {
        for (i in 0..19) {
            addValueToGraph(1)
        }
    }

    private fun createSet(): LineDataSet =
        LineDataSet(null, "").apply {
            axisDependency = YAxis.AxisDependency.LEFT
            color = getColorFromRes(R.color.main)
            fillColor = getColorFromRes(R.color.main)
            lineWidth = LINE_CHART_WIDTH
            setDrawCircles(false)
            setDrawCubic(true)
            setDrawValues(false)
            isHighlightEnabled = false
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
