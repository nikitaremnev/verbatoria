package com.verbatoria.presentation.calendar.view.detail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.ReportModel;
import com.verbatoria.business.dashboard.models.TimeIntervalModel;
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
import java.util.List;

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
        DatePickerDialog.OnDateSetListener {

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

    @BindView(R.id.report_field)
    public View mReportFieldView;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    private Calendar mSelectTimeCalendar;

    private MenuItem mDeleteMenuItem;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_delete, menu);
        mDeleteMenuItem = menu.findItem(R.id.action_delete);
        mDeleteMenuItem.setVisible(mEventDetailPresenter.isEditMode() && mEventDetailPresenter.isDeleteEnabled());
        return super.onCreateOptionsMenu(menu);
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
        if (item.getItemId() == R.id.action_delete) {
            showConfirmDeleteDialog();
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
    public void showError(String message) {
        Helper.showErrorSnackBar(mSubmitButton, message);
    }

    @Override
    public void showEventAdded() {
        Helper.showHintSnackBar(mSubmitButton, getString(R.string.event_detail_event_added));
    }

    @Override
    public void showEventEdited() {
        Helper.showHintSnackBar(mSubmitButton, getString(R.string.event_detail_event_edited));
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
    public void updateReportView(ReportModel reportModel) {
        if (reportModel == null) {
            mReportFieldView.setVisibility(View.GONE);
        } else {
            setUpFieldView(mReportFieldView, R.drawable.ic_report, reportModel.getReportId(), getString(R.string.event_detail_activity_report), v -> { showStatusHint(reportModel); });
            setUpReportStatus(reportModel);
            mReportFieldView.setVisibility(View.VISIBLE);
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
        mSubmitButton.setOnClickListener(v -> {
            mEventDetailPresenter.startSession();
        });
        mDeleteMenuItem.setVisible(mEventDetailPresenter.isEditMode() && mEventDetailPresenter.isDeleteEnabled());
    }

    @Override
    public void setUpEventEdit() {
        mSubmitButton.setText(getString(R.string.save));
        mSubmitButton.setOnClickListener(v -> mEventDetailPresenter.editEvent());
    }

    @Override
    public void showClientNotFullError() {
        Helper.showErrorSnackBar(mSubmitButton, getString(R.string.client_data_is_not_full));
    }

    @Override
    public void showChildNotFullError() {
        Helper.showErrorSnackBar(mSubmitButton, getString(R.string.child_data_is_not_full));
    }

    @Override
    public void showTimeNotSetError() {
        Helper.showErrorSnackBar(mSubmitButton, getString(R.string.time_is_not_set));
    }

    @Override
    public void showPossibleTimeIntervals(List<TimeIntervalModel> timeIntervals) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.event_detail_activity_available_time));
        builder.setSingleChoiceItems(getTimeIntervalsStrings(timeIntervals), -1, (dialog, which) -> {
            mSelectTimeCalendar.setTime(timeIntervals.get(which).getStartAt());
            mEventDetailPresenter.setEventDate(mSelectTimeCalendar);
            if (mEventDetailPresenter.isEditMode()) {
                setUpEventEdit();
            }
        });
        builder.setNegativeButton(getString(R.string.ok), null);
        builder.create().show();
    }

    @Override
    public void closeWhenDeleted() {
        Snackbar snackbar = Snackbar.make(mSubmitButton, getString(R.string.event_deleted), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.hint_color);
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                finish();
            }
        });
        snackbar.show();
    }

    @Override
    public void showConfirmOverrideWriting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.event_detail_activity_confirm_override_title))
                .setMessage(getString(R.string.event_detail_activity_confirm_override_message))
                .setIcon(R.drawable.ic_neurointerface_error)
                .setPositiveButton(getString(R.string.event_detail_activity_continue), (dialog, which) -> {
                    mEventDetailPresenter.startSession();
                })
                .setNegativeButton(getString(R.string.event_detail_activity_cancel), (dialog, which) -> {
                    dialog.dismiss();
                });
        builder.create().show();
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
        mEventDetailPresenter.pickTime(mSelectTimeCalendar);
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
            mSubmitButton.setOnClickListener(v -> {
                if (!mEventDetailPresenter.checkStartSession()) {
                    mEventDetailPresenter.startSession();
                }
            });
        }
    }

    private void setUpFields() {
        updateClientView(mEventDetailPresenter.getClientModel());
        updateChildView(mEventDetailPresenter.getChildModel());
        updateEventTime(mEventDetailPresenter.getEvent());
        updateReportView(mEventDetailPresenter.getEvent().getReport());
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.setOnClickListener(onClickListener);
    }

    private void setUpReportStatus(ReportModel reportModel) {
        switch (reportModel.getStatus()) {
            case ReportModel.STATUS.NEW:
                ((ImageView) mReportFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_report_new);
                break;
            case ReportModel.STATUS.READY:
                ((ImageView) mReportFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_report_ready);
                break;
            case ReportModel.STATUS.SENT:
                ((ImageView) mReportFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_report_sent);
                break;
            case ReportModel.STATUS.UPLOADED:
                ((ImageView) mReportFieldView.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_report_uploaded);
                break;
        }
    }

    private void showStatusHint(ReportModel reportModel) {
        switch (reportModel.getStatus()) {
            case ReportModel.STATUS.NEW:
                Helper.showHintSnackBar(mReportFieldView, getString(R.string.event_detail_activity_hint_status_new));
                break;
            case ReportModel.STATUS.READY:
                Helper.showHintSnackBar(mReportFieldView, getString(R.string.event_detail_activity_hint_status_ready));
                break;
            case ReportModel.STATUS.SENT:
                Helper.showHintSnackBar(mReportFieldView, getString(R.string.event_detail_activity_hint_status_sent));
                break;
            case ReportModel.STATUS.UPLOADED:
                Helper.showHintSnackBar(mReportFieldView, getString(R.string.event_detail_activity_hint_status_uploaded));
                break;
        }
    }

    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.calendar_confirm_delete_title))
                .setMessage(getString(R.string.calendar_confirm_delete_message));
        builder.setPositiveButton(getString(R.string.delete), (dialog, which) -> {
            mEventDetailPresenter.deleteEvent();
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();
    }

    private String[] getTimeIntervalsStrings(List<TimeIntervalModel> timeIntervalModels) {
        String[] intervalsStrings = new String[timeIntervalModels.size()];
        int index = 0;
        for (TimeIntervalModel timeIntervalModel : timeIntervalModels) {
            intervalsStrings[index] = timeIntervalModel.getIntervalString();
            index ++;
        }
        return intervalsStrings;
    }

}
