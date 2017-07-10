package com.verbatoria.presentation.dashboard.view;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.remnev.verbatoriamini.R;
import com.verbatoria.presentation.dashboard.view.calendar.CalendarFragment;
import com.verbatoria.presentation.dashboard.view.main.DashboardMainFragment;
import com.verbatoria.presentation.dashboard.view.settings.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран dashboard
 *
 * @author nikitaremnev
 */
public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    @BindView(R.id.bottom_navigation_view)
    public BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        setUpBottomNavigation();
        setUpFragment(DashboardMainFragment.newInstance());
    }

    private void setUpFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragments_container, fragment);
        transaction.commit();
    }

    private void setUpBottomNavigation() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        setUpFragment(DashboardMainFragment.newInstance());
                        return true;
                    case R.id.navigation_calendar:
                        setUpFragment(CalendarFragment.newInstance());
                        return true;
                    case R.id.navigation_settings:
                        setUpFragment(SettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }
}
