package com.verbatoria.presentation.dashboard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.blocked.BlockedFragment;
import com.verbatoria.presentation.calendar.view.CalendarFragment;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.presentation.dashboard.view.info.VerbatologInfoFragment;
import com.verbatoria.presentation.dashboard.view.settings.SettingsFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран dashboard
 *
 * @author nikitaremnev
 */
public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Inject
    IDashboardPresenter mDashboardPresenter;

    @BindView(R.id.bottom_navigation_view)
    public BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);

        setUpBottomNavigation();
        setUpFragment(VerbatologInfoFragment.newInstance());
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
                    setUpFragment(VerbatologInfoFragment.newInstance());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBottomNavigationView.setSelectedItemId(R.id.navigation_calendar);
    }
}
