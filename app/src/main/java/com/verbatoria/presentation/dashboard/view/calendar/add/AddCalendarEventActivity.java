package com.verbatoria.presentation.dashboard.view.calendar.add;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.remnev.verbatoriamini.R;
import butterknife.ButterKnife;

/**
 * Экран добавления события в календарь
 *
 * @author nikitaremnev
 */
public class AddCalendarEventActivity extends AppCompatActivity implements IAddCalendarEventView {

    private static final String TAG = AddCalendarEventActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);
        ButterKnife.bind(this);
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
        getSupportActionBar().setTitle(getString(R.string.dashboard_add_event));
    }

}
