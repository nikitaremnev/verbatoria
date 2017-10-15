package com.verbatoria.presentation.calendar.view.add.children;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.children.IChildrenPresenter;
import com.verbatoria.presentation.calendar.view.add.clients.adapter.ChildrenAdapter;
import com.verbatoria.utils.Helper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран поиска детей
 *
 * @author nikitaremnev
 */
public class SearchChildrenActivity extends BaseActivity implements ISearchChildrenView {

    private static final String TAG = SearchChildrenActivity.class.getSimpleName();

    @Inject
    IChildrenPresenter mChildrenPresenter;

    @BindView(R.id.query_edit_text)
    public EditText mQueryEditText;

    @BindView(R.id.image_view)
    public ImageView mImageView;

    @BindView(R.id.found_adapter)
    public RecyclerView mFoundChildrenRecyclerView;

    @BindView(R.id.search_button)
    public Button mSearchButton;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, SearchChildrenActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_search);

        setPresenter((BasePresenter) mChildrenPresenter);
        mChildrenPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpRecyclerView();
        mImageView.setImageResource(R.drawable.ic_search_color);
        mQueryEditText.setHint(getString(R.string.search_by_child_name));
        mSearchButton.setOnClickListener(v -> mChildrenPresenter.searchChildren());
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_search_child_title));
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
    public void showChildsFound(List<ChildModel> children) {
        mFoundChildrenRecyclerView.setAdapter(new ChildrenAdapter(children, this));
    }

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mSearchButton, message);
    }

    private void setUpRecyclerView() {
        mFoundChildrenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFoundChildrenRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
