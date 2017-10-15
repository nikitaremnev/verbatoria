package com.verbatoria.presentation.calendar.view.add.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;
import com.verbatoria.presentation.calendar.view.add.children.adapter.ClientsAdapter;
import com.verbatoria.utils.Helper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран поиска клиентов
 *
 * @author nikitaremnev
 */
public class SearchClientsActivity extends BaseActivity implements ISearchClientsView {

    private static final String TAG = SearchClientsActivity.class.getSimpleName();

    @Inject
    IClientsPresenter mClientsPresenter;

    @BindView(R.id.query_edit_text)
    public EditText mQueryEditText;

    @BindView(R.id.image_view)
    public ImageView mImageView;

    @BindView(R.id.found_adapter)
    public RecyclerView mFoundClientsRecyclerView;

    @BindView(R.id.search_button)
    public Button mSearchButton;

    @BindView(R.id.divider)
    public View mDivider;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, SearchClientsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_search);

        setPresenter((BasePresenter) mClientsPresenter);
        mClientsPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpRecyclerView();
        mImageView.setImageResource(R.drawable.ic_search_color);
        mSearchButton.setOnClickListener(v -> mClientsPresenter.searchClients());
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
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_search_client_title));
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
    public String getQuery() {
        return mQueryEditText.getText().toString();
    }

    @Override
    public void showClientsFound(List<ClientModel> clients) {
        mDivider.setVisibility(clients.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        mFoundClientsRecyclerView.setAdapter(new ClientsAdapter(clients, this));
    }

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mSearchButton, message);
    }

    private void setUpRecyclerView() {
        mFoundClientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFoundClientsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
