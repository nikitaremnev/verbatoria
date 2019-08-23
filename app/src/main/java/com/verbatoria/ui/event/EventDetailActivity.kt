package com.verbatoria.ui.event

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.item.EventDetailItem
import com.verbatoria.business.client.Client
import com.verbatoria.di.Injector
import com.verbatoria.di.event.EventDetailComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.client.ClientActivity
import com.verbatoria.ui.common.Adapter
import javax.inject.Inject

/**
 * @author n.remnev
 */

private const val CLIENT_REQUEST_CODE = 912

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"

interface EventDetailView : BaseView {

    fun setTitle(titleResourceId: Int)

    fun setEventDetailItems(eventDetailItems: List<EventDetailItem>)

    fun openClient(eventDetailMode: EventDetailMode, client: Client?)

    fun updateEventDetailItem(position: Int)

    fun close()

    interface Callback {

        fun onClientReturned(client: Client?)

        fun onNavigationClicked()

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

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_event_detail

    override fun buildComponent(injector: Injector, savedState: Bundle?): EventDetailComponent =
        injector.plusEventDetailComponent()
            .eventDetailMode(intent.getIntExtra(EVENT_DETAIL_MODE_EXTRA, EventDetailMode.CREATE_NEW.ordinal))
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }
    }

    //endregion

    //region BasePresenterActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CLIENT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.onClientReturned(data?.getParcelableExtra(ClientActivity.CLIENT_EXTRA))
        }
    }

    //endregion

    //region EventDetailView

    override fun setTitle(titleResourceId: Int) {
        toolbar.title = getString(titleResourceId)
    }

    override fun setEventDetailItems(eventDetailItems: List<EventDetailItem>) {
        adapter.update(eventDetailItems)
    }

    override fun openClient(eventDetailMode: EventDetailMode, client: Client?) {
        startActivityForResult(ClientActivity.createIntent(this, eventDetailMode, client), CLIENT_REQUEST_CODE)
    }

    override fun updateEventDetailItem(position: Int) {
        adapter.update(position)
    }

    override fun close() {
        finish()
    }

    //endregion

}