package com.verbatoria.presentation.calendar.view.add.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран добавления клиентов
 *
 * @author nikitaremnev
 */
public class ClientsActivity extends BaseActivity implements IClientsView {

    private static final String TAG = ClientsActivity.class.getSimpleName();

    @Inject
    IClientsPresenter mClientsPresenter;

//    @BindView(R.id.child_id_edit_text)
//    public EditText mChildIdEditText;
//
//    @BindView(R.id.location_id_edit_text)
//    public EditText mLocationIdEditText;
//
//    @BindView(R.id.start_date_edit_text)
//    public EditText mStartDateEditText;
//
//    @BindView(R.id.end_date_edit_text)
//    public EditText mEndDateEditText;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, ClientsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_client);

        setPresenter((BasePresenter) mClientsPresenter);
        mClientsPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_add_client_title));
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

}
