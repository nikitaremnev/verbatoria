package com.verbatoria.presentation.session.view.submit;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.session.presenter.submit.ISubmitPresenter;
import com.verbatoria.presentation.session.view.submit.adapter.QuestionsAdapter;
import java.util.ArrayList;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран отправки результатов
 *
 * @author nikitaremnev
 */
public class SubmitActivity extends AppCompatActivity implements ISubmitView {

    @Inject
    ISubmitPresenter mSubmitPresenter;

    @BindView(R.id.loading_view)
    public View mLoadingView;

    @BindView(R.id.next_button)
    public Button mNextButton;

    @BindView(R.id.back_button)
    public Button mBackButton;

    @BindView(R.id.navigation_layout)
    public LinearLayout mNavigationLayout;

    @BindView(R.id.questions_pager_container)
    public ViewPagerContainer mQuestionsPagerContainer;

    @BindView(R.id.questions_view_pager)
    public ViewPager mViewPager;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_submit);
        ButterKnife.bind(this);
        setUpViews();
        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        mSubmitPresenter.bindView(this);
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
        mNextButton.setOnClickListener(v -> mViewPager.postDelayed(()
                -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true), 100));
        mBackButton.setOnClickListener(v -> mViewPager.postDelayed(()
                -> mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true), 100));
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
            View circle = firstItem.findViewById(R.id.circle_image_view);
            navigationItems.add(circle);
            mNavigationLayout.addView(navigationItem);
        }
        mNavigationLayout.invalidate();
        mQuestionsPagerContainer.setCircles(navigationItems);
    }

    private void setUpQuestionaryAdapter() {
        final QuestionsAdapter adapter = new QuestionsAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(QuestionsAdapter.OFFSCREEN_PAGE_LIMIT);
        mViewPager.setClipChildren(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0) {
                    mBackButton.setVisibility(View.VISIBLE);
                } else if (position == QuestionsAdapter.QUESTIONARY_SIZE - 1) {
                    mNextButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
