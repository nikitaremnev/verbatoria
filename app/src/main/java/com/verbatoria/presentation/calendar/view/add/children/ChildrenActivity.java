package com.verbatoria.presentation.calendar.view.add.children;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.children.IChildrenPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран добавления детей
 *
 * @author nikitaremnev
 */
public class ChildrenActivity extends BaseActivity implements IChildrenView {

    private static final String TAG = ChildrenActivity.class.getSimpleName();
    public static final String EXTRA_CHILD_MODEL = "com.verbatoria.presentation.calendar.view.add.children.EXTRA_CHILD_MODEL";

    @Inject
    IChildrenPresenter mChildrenPresenter;

//    @BindView(R.id.client_name_edit_text)
//    public EditText mClientNameEditText;
//
//    @BindView(R.id.client_email_edit_text)
//    public EditText mClientEmailEditText;
//
//    @BindView(R.id.client_phone_edit_text)
//    public EditText mClientPhoneEditText;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext, ChildModel childModel) {
        Intent intent = new Intent(mContext, ChildrenActivity.class);
        intent.putExtra(EXTRA_CHILD_MODEL, childModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_children);

        setPresenter((BasePresenter) mChildrenPresenter);
        mChildrenPresenter.bindView(this);
        mChildrenPresenter.obtainChild(getIntent());

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
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_add_child_title));
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
