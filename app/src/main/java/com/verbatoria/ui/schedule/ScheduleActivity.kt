package com.verbatoria.ui.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.schedule.ScheduleComponent
import com.verbatoria.domain.schedule.ScheduleDataSource
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author n.remnev
 */

interface ScheduleView : BaseView {

    fun setSchedule(scheduleDataSource: ScheduleDataSource)

    fun close()

    interface Callback {

        fun onNavigationClicked()

    }

}

class ScheduleActivity : BasePresenterActivity<ScheduleView, SchedulePresenter, ScheduleActivity, ScheduleComponent>(), ScheduleView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, ScheduleActivity::class.java)

    }

    private lateinit var toolbar: Toolbar
    private lateinit var adaptiveTableLayout: AdaptiveTableLayout

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_schedule

    override fun buildComponent(injector: Injector, savedState: Bundle?): ScheduleComponent =
        injector.plusScheduleComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        adaptiveTableLayout = findViewById(R.id.schedule_adaptive_table_layout)

        toolbar.setTitle(R.string.schedule_title)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }

    }

    //endregion

    //region ScheduleView

    override fun setSchedule(scheduleDataSource: ScheduleDataSource) {
        adaptiveTableLayout.setAdapter(
            ScheduleAdapter(this, adaptiveTableLayout.width, adaptiveTableLayout.height, scheduleDataSource)
        )
    }

    override fun close() {
        finish()
    }

    //endregion

}
