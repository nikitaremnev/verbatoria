package com.verbatoria.ui.dashboard.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.ui.blocked.BlockedFragment;
import com.verbatoria.ui.calendar.view.CalendarFragment;
import com.verbatoria.ui.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.ui.dashboard.view.info.VerbatologInfoFragment;
import com.verbatoria.ui.dashboard.view.settings.SettingsFragment;
import javax.inject.Inject;
import butterknife.BindView;

/**
 * Экран dashboard
 *
 * @author nikitaremnev
 */
public class DashboardActivity extends BaseActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    public static final String EXTRA_FINISH_SESSION = "com.verbatoria.presentation.dashboard.view.EXTRA_FINISH_SESSION";

    @Inject
    IDashboardPresenter mDashboardPresenter;

    @BindView(R.id.bottom_navigation_view)
    public BottomNavigationView mBottomNavigationView;

    public static Intent newInstance(Context mContext, boolean sessionFinish) {
        Intent intent = new Intent(mContext, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(EXTRA_FINISH_SESSION, sessionFinish);
        return intent;
    }

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, DashboardActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getInjector().addModule(new DashboardModule()).inject(this);

        setContentView(R.layout.activity_dashboard);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        //empty
    }

    @Override
    protected void setUpViews() {
        setUpBottomNavigation();
        if (mDashboardPresenter.isShowSettings()) {
            mDashboardPresenter.setShowSettings(false);
            setUpFragment(SettingsFragment.newInstance());
        } else {
            if (mDashboardPresenter.isBlocked()) {
                setUpFragment(BlockedFragment.newInstance());
            } else {
                setUpFragment(VerbatologInfoFragment.newInstance());
            }
        }
        showSessionFinish();
    }

    private void setUpFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragments_container, fragment);
        transaction.commit();
    }

    private void setUpBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_main:
                    if (mDashboardPresenter.isBlocked()) {
                        setUpFragment(BlockedFragment.newInstance());
                    } else {
                        setUpFragment(VerbatologInfoFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_calendar:
                    if (mDashboardPresenter.isBlocked()) {
                        setUpFragment(BlockedFragment.newInstance());
                    } else {
                        setUpFragment(CalendarFragment.newInstance());
                    }
                    return true;
                case R.id.navigation_settings:
                    setUpFragment(SettingsFragment.newInstance());
                    return true;
            }
            return false;
        });
    }

    private void showSessionFinish() {
        if (getIntent().hasExtra(EXTRA_FINISH_SESSION)) {
            if (getIntent().getBooleanExtra(EXTRA_FINISH_SESSION, false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.dashboard_session_finish));
                builder.setNegativeButton(getString(R.string.ok), null);
                builder.create().show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.dashboard_session_finish_error_title))
                        .setIcon(R.drawable.ic_neurointerface_error)
                        .setMessage(getString(R.string.dashboard_session_finish_error_message));
                builder.setNegativeButton(getString(R.string.all_understand), null);
                builder.create().show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBottomNavigationView.setSelectedItemId(R.id.navigation_calendar);
    }
}
