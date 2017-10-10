package com.verbatoria.presentation.calendar.view.detail;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.detail.EventDetailPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.IEventDetailPresenter;
import com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity;
import com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.utils.Helper;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;

import static com.verbatoria.presentation.calendar.view.add.children.ChildrenActivity.EXTRA_CHILD_MODEL;
import static com.verbatoria.presentation.calendar.view.add.clients.ClientsActivity.EXTRA_CLIENT_MODEL;

/**
 * Экран события
 *
 * @author nikitaremnev
 */
public class EventDetailActivity extends BaseActivity implements IEventDetailView,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    private static final String TAG = EventDetailActivity.class.getSimpleName();

    public static final int ACTIVITY_CHILDREN_CODE = 27;
    public static final int ACTIVITY_CLIENTS_CODE = 28;

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

    private Calendar mSelectTimeCalendar;

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
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_event_detail);

        mEventDetailPresenter.bindView(this);
        mEventDetailPresenter.obtainEvent(getIntent());

        setPresenter((BasePresenter) mEventDetailPresenter);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventDetailPresenter.loadClient();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_CLIENTS_CODE && resultCode == RESULT_OK) {
            ClientModel obtainedClientModel = data.getParcelableExtra(EXTRA_CLIENT_MODEL);
            mEventDetailPresenter.setClientModel(obtainedClientModel);

        }
        if (requestCode == ACTIVITY_CHILDREN_CODE && resultCode == RESULT_OK) {
            ChildModel obtainedChildModel = data.getParcelableExtra(EXTRA_CHILD_MODEL);
            mEventDetailPresenter.setChildModel(obtainedChildModel);
        }
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
        startProgress();
    }

    @Override
    public void hideProgress() {
        stopProgress();
    }

    @Override
    public void startConnection() {
        Intent intent = ConnectionActivity.newInstance(this, mEventDetailPresenter.getEvent());
        startActivity(intent);
        finish();
    }

    @Override
    public void startChild() {
        if (!mEventDetailPresenter.isEditMode() && (mEventDetailPresenter.getClientModel() == null ||
                (mEventDetailPresenter.getClientModel() != null && mEventDetailPresenter.getClientModel().getId() == null))) {
            showError(getString(R.string.event_detail_add_client_first));
        } else {
            ChildModel childModel = mEventDetailPresenter.getChildModel();
            Intent intent = childModel == null ? ChildrenActivity.newInstance(this, mEventDetailPresenter.getClientModel())
                    : ChildrenActivity.newInstance(this, childModel, mEventDetailPresenter.getClientModel());
            startActivityForResult(intent, ACTIVITY_CHILDREN_CODE);
        }
    }

    @Override
    public void startClient() {
        ClientModel clientModel = mEventDetailPresenter.getClientModel();
        Intent intent = clientModel == null ? ClientsActivity.newInstance(this) : ClientsActivity.newInstance(this, clientModel);
        startActivityForResult(intent, ACTIVITY_CLIENTS_CODE);
    }

    @Override
    public void startDatePicker() {
        mSelectTimeCalendar = null;
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void startTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);
        timePickerDialog.show();
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mSubmitButton, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showEventAdded() {
        Snackbar snackbar = Snackbar.make(mSubmitButton, getString(R.string.event_detail_event_added), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void showEventEdited() {
        Snackbar snackbar = Snackbar.make(mSubmitButton, getString(R.string.event_detail_event_edited), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void updateClientView(ClientModel clientModel) {
        if (clientModel == null) {
            ((ImageView) mClientFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            String clientString = getString(R.string.event_detail_activity_field_empty);
            setUpFieldView(mClientFieldView, R.drawable.ic_client, clientString, getString(R.string.event_detail_activity_client), v -> startClient());
        } else {
            ((ImageView) mClientFieldView.findViewById(R.id.status_image_view)).setImageResource(clientModel.isFull() ? R.drawable.ic_ok : R.drawable.ic_not_ok);
            String clientString = TextUtils.isEmpty(clientModel.getName()) ? getString(R.string.event_detail_activity_field_empty): clientModel.getName();
            setUpFieldView(mClientFieldView, R.drawable.ic_client, clientString, getString(R.string.event_detail_activity_client), v -> startClient());
        }
    }

    @Override
    public void updateChildView(ChildModel childModel) {
        if (childModel == null) {
            ((ImageView) mChildFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            String childString = getString(R.string.event_detail_activity_field_empty);
            setUpFieldView(mChildFieldView, R.drawable.ic_child, childString, getString(R.string.event_detail_activity_child), v -> startChild());
        } else {
            ((ImageView) mChildFieldView.findViewById(R.id.status_image_view)).setImageResource(childModel.isFull() ? R.drawable.ic_ok : R.drawable.ic_not_ok);
            String childString = TextUtils.isEmpty(childModel.getName()) ? getString(R.string.event_detail_activity_field_empty): childModel.getName();
            setUpFieldView(mChildFieldView, R.drawable.ic_child, childString, getString(R.string.event_detail_activity_child), v -> startChild());
        }
    }

    @Override
    public void updateEventTime(EventModel eventModel) {
        if (eventModel == null) {
            ((ImageView) mDateFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            String timeString = TextUtils.isEmpty(mEventDetailPresenter.getTime()) ? getString(R.string.event_detail_activity_field_empty): mEventDetailPresenter.getTime();
            setUpFieldView(mDateFieldView, R.drawable.ic_date, timeString, getString(R.string.event_detail_activity_time), v -> startDatePicker());
        } else {
            ((ImageView) mDateFieldView.findViewById(R.id.status_image_view)).setImageResource(eventModel.hasTime() ? R.drawable.ic_ok : R.drawable.ic_not_ok);
            String timeString = TextUtils.isEmpty(mEventDetailPresenter.getTime()) ? getString(R.string.event_detail_activity_field_empty): mEventDetailPresenter.getTime();
            setUpFieldView(mDateFieldView, R.drawable.ic_date, timeString, getString(R.string.event_detail_activity_time), v -> startDatePicker());
        }
    }

    @Override
    public void setUpEventCreated() {
        mSubmitButton.setText(getString(R.string.dashboard_start_session));
        mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.startSession());
    }

    @Override
    public void setUpEventEdit() {
        mSubmitButton.setText(getString(R.string.save));
        mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.editEvent());
    }

    @Override
    public void showClientNotFullError() {
        Helper.showSnackBar(mSubmitButton, getString(R.string.client_data_is_not_full));
    }

    @Override
    public void showChildNotFullError() {
        Helper.showSnackBar(mSubmitButton, getString(R.string.child_data_is_not_full));
    }

    @Override
    public void showTimeNotSetError() {
        Helper.showSnackBar(mSubmitButton, getString(R.string.time_is_not_set));
    }

    @Override
    protected void setUpViews() {
        setUpToolbar();
        setUpButton();
        setUpFields();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mSelectTimeCalendar = Calendar.getInstance();
        mSelectTimeCalendar.set(Calendar.YEAR, year);
        mSelectTimeCalendar.set(Calendar.MONTH, month);
        mSelectTimeCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        startTimePicker();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mSelectTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        mSelectTimeCalendar.set(Calendar.MINUTE, minute);
        mEventDetailPresenter.setEventDate(mSelectTimeCalendar);
        if (mEventDetailPresenter.isEditMode()) {
            setUpEventEdit();

        }
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
            mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.createEvent());
        } else {
            mSubmitButton.setText(getString(R.string.dashboard_start_session));
            mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.startSession());
        }
    }

    private void setUpFields() {
        updateClientView(mEventDetailPresenter.getClientModel());
        updateChildView(mEventDetailPresenter.getChildModel());
        updateEventTime(mEventDetailPresenter.getEvent());
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.setOnClickListener(onClickListener);
    }

}
