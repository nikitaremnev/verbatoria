package com.verbatoria.ui.session.view.submit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.ui.dashboard.view.DashboardActivity;
import com.verbatoria.ui.session.presenter.submit.ISubmitPresenter;
import com.verbatoria.ui.session.view.submit.questions.QuestionsAdapter;
import com.verbatoria.ui.session.view.submit.questions.QuestionsViewPagerContainer;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbatoria.ui.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Экран отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitActivity extends AppCompatActivity implements ISubmitView {

    private static final String TAG = SubmitActivity.class.getSimpleName();

    @Inject
    ISubmitPresenter mSubmitPresenter;

    @BindView(R.id.next_button)
    public Button nextButton;

    @BindView(R.id.back_button)
    public Button backButton;

    @BindView(R.id.navigation_layout)
    public LinearLayout mNavigationLayout;

    @BindView(R.id.questions_pager_container)
    public QuestionsViewPagerContainer mQuestionsPagerContainer;

    @BindView(R.id.questions_view_pager)
    public ViewPager mViewPager;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    private QuestionsAdapter mQuestionsAdapter;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, SubmitActivity.class);
    }

    public static Intent newInstance(Context mContext, EventModel eventModel) {
        Intent intent = new Intent(mContext, SubmitActivity.class);
        intent.putExtra(EXTRA_EVENT_MODEL, eventModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);
        setUpViews();
        //bind views
        VerbatoriaApplication.getInjector().addModule(new SessionModule()).inject(this);
        mSubmitPresenter.bindView(this);
        mSubmitPresenter.obtainEvent(getIntent());
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Logger.e(TAG, "onUserInteraction");
        VerbatoriaApplication.onUserInteraction();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubmitPresenter.unbindView();
    }

    private void setUpViews() {
        setUpQuestionaryNavigation();
        setUpQuestionaryAdapter();
        nextButton.setOnClickListener(v -> mViewPager.postDelayed(()
                -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true), 100));
        backButton.setOnClickListener(v -> mViewPager.postDelayed(()
                -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true), 100));
        mSubmitButton.setOnClickListener(v -> mSubmitPresenter.sendResults(mQuestionsAdapter.getAnswers()));
        mSubmitButton.setEnabled(mQuestionsAdapter.isAllQuestionsAnswered());
    }

    private void setUpQuestionaryNavigation() {
        ArrayList<View> navigationItems = new ArrayList<>();

        //first navigation item
        View firstItem = getLayoutInflater().inflate(R.layout.item_navigation, null, false);
        View firstCircle = firstItem.findViewById(R.id.circle_image_view);
        firstCircle.setBackgroundResource(R.drawable.background_navigation_item_selected);
        navigationItems.add(firstCircle);
        mNavigationLayout.addView(firstItem);

        //other items
        for (int i = 1; i < QuestionsAdapter.QUESTIONARY_SIZE; i ++) {
            View navigationItem = getLayoutInflater().inflate(R.layout.item_navigation, null, false);
            View circle = navigationItem.findViewById(R.id.circle_image_view);
            navigationItems.add(circle);
            mNavigationLayout.addView(navigationItem);
        }
        mNavigationLayout.invalidate();
        mQuestionsPagerContainer.setCircleViews(navigationItems);
    }

    private void setUpQuestionaryAdapter() {
        mQuestionsAdapter = new QuestionsAdapter(this);
        mViewPager.setAdapter(mQuestionsAdapter);
        mViewPager.setOffscreenPageLimit(QuestionsAdapter.OFFSCREEN_PAGE_LIMIT);
        mViewPager.setClipChildren(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mQuestionsPagerContainer.onPageSelected(position);
                backButton.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                if (position == 0) {
                    backButton.setVisibility(View.GONE);
                }
                if (position == QuestionsAdapter.QUESTIONARY_SIZE - 1) {
                    nextButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onAnswerClicked() {
        nextButton.performClick();
        mSubmitButton.setEnabled(mQuestionsAdapter.isAllQuestionsAnswered());
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSubmitButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
        mSubmitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mLoadingView, message);
    }

    @Override
    public void finishSession() {
        startActivity(DashboardActivity.newInstance(this, true));
        finish();
    }

    @Override
    public void finishSessionWithError() {
        startActivity(DashboardActivity.newInstance(this, false));
        finish();
    }

}