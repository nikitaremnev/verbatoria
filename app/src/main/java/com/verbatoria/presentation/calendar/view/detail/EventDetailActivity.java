package com.verbatoria.presentation.calendar.view.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.IEventDetailPresenter;
import com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity;
import com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран события
 *
 * @author nikitaremnev
 */
public class EventDetailActivity extends BaseActivity implements IEventDetailView {

    private static final String TAG = EventDetailActivity.class.getSimpleName();

    public static final int ACTIVITY_CHILDREN_CODE = 27;
    public static final int ACTIVITY_ClIENTS_CODE = 28;

    @Inject
    IEventDetailPresenter mEventDetailPresenter;

    @BindView(R.id.client_field)
    public View mClientFieldView;

    @BindView(R.id.child_field)
    public View mChildFieldView;

    @BindView(R.id.date_field)
    public View mDateFieldView;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext, EventModel eventModel) {
        Intent intent = new Intent(mContext, EventDetailActivity.class);
        intent.putExtra(EventDetailPresenter.EXTRA_EVENT_MODEL, eventModel);
        return intent;
    }

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, EventDetailActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        setContentView(R.layout.activity_event_detail);

        mEventDetailPresenter.bindView(this);
        mEventDetailPresenter.obtainEvent(getIntent());

        setPresenter((BasePresenter) mEventDetailPresenter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void startConnection() {
        Intent intent = ConnectionActivity.newInstance(this, mEventDetailPresenter.getEvent());
        startActivity(intent);
        finish();
    }

    @Override
    public void startChild() {
        Intent intent = ChildrenActivity.newInstance(this, mEventDetailPresenter.getChildModel());
        startActivityForResult(intent, ACTIVITY_CHILDREN_CODE);
    }

    @Override
    public void startClient() {
        Intent intent = ClientsActivity.newInstance(this);
        startActivityForResult(intent, ACTIVITY_ClIENTS_CODE);
    }

    @Override
    public void startDatePicker() {

    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mLoadingView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void setUpViews() {
        setUpToolbar();
        setUpButton();
        setUpFields();
    }

    private void setUpToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.dashboard_event));
    }

    private void setUpButton() {
        if (!mEventDetailPresenter.isEditMode()) {
            mSubmitButton.setText(getString(R.string.calendar_activity_event_create));
            mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.startSession());
        } else {
            mSubmitButton.setText(getString(R.string.dashboard_start_session));
            mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.startSession());
        }
    }

    private void setUpFields() {
        String clientString = TextUtils.isEmpty(mEventDetailPresenter.getClient()) ? getString(R.string.event_detail_activity_field_empty): mEventDetailPresenter.getClient();
        String childString = TextUtils.isEmpty(mEventDetailPresenter.getChild()) ? getString(R.string.event_detail_activity_field_empty): mEventDetailPresenter.getChild();
        String timeString = TextUtils.isEmpty(mEventDetailPresenter.getTime()) ? getString(R.string.event_detail_activity_field_empty): mEventDetailPresenter.getTime();
        setUpFieldView(mClientFieldView, R.drawable.ic_client, clientString, getString(R.string.event_detail_activity_client), v -> startClient());
        setUpFieldView(mChildFieldView, R.drawable.ic_child, childString, getString(R.string.event_detail_activity_child), v -> startChild());
        setUpFieldView(mDateFieldView, R.drawable.ic_date, timeString, getString(R.string.event_detail_activity_time), v -> startDatePicker());
        setUpFieldsStatus();
    }

    private void setUpFieldsStatus() {
        if (!mEventDetailPresenter.isEditMode()) {
            ((ImageView) mClientFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            ((ImageView) mChildFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            ((ImageView) mDateFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
        }
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.setOnClickListener(onClickListener);
    }

}
