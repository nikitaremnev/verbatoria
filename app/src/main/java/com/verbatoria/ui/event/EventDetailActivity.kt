package com.verbatoria.ui.event

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.item.EventDetailItem
import com.verbatoria.di.Injector
import com.verbatoria.di.event.EventDetailComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.common.Adapter
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"

interface EventDetailView : BaseView {

    fun setEventDetailItems(eventDetailItems: List<EventDetailItem>)

    interface Callback {



    }

}

class EventDetailActivity : BasePresenterActivity<EventDetailView, EventDetailPresenter, EventDetailActivity, EventDetailComponent>(), EventDetailView {

    companion object {

        fun createIntent(
            context: Context,
            eventDetailMode: EventDetailMode
        ): Intent =
            Intent(context, EventDetailActivity::class.java)
                .putExtra(EVENT_DETAIL_MODE_EXTRA, eventDetailMode.ordinal)

    }

    @Inject
    lateinit var adapter: Adapter

    private lateinit var recyclerView: RecyclerView

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_event_detail

    override fun buildComponent(injector: Injector, savedState: Bundle?): EventDetailComponent =
        injector.plusEventDetailComponent()
            .eventDetailMode(intent.getIntExtra(EVENT_DETAIL_MODE_EXTRA, EventDetailMode.CREATE_NEW.ordinal))
            .build()

    override fun initViews(savedState: Bundle?) {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    //endregion

    //region EventDetailView

    override fun setEventDetailItems(eventDetailItems: List<EventDetailItem>) {
        adapter.update(eventDetailItems)
    }

    //endregion

}