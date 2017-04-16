package com.remnev.verbatoriamini.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.adapters.ParentsQuestionsAdapter;
import com.remnev.verbatoriamini.callbacks.IAllAnsweredCallback;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;
import com.remnev.verbatoriamini.views.ViewPagerContainer;

import java.util.ArrayList;

/**
 * Created by nikitaremnev on 11.03.17.
 */

public class QuestionaryDialogFragment extends DialogFragment implements ParentsQuestionsAdapter.IVariantButtonClick {

    public static final String TAG = "ParentsQuestionaryDialogFragment";

    private final ArrayList<View> mCircles = new ArrayList<>();
    private ViewPagerContainer mViewPagerContainer;
    private ViewPager mViewPager;
    private Button nextButton;
    private Button backButton;

    private String age;
    private String reportID;
    private String directoryAbsPath;

    private IAllAnsweredCallback activityCallback;

    public QuestionaryDialogFragment() {}

    public QuestionaryDialogFragment(String age, String reportID, String directoryAbsPath, IAllAnsweredCallback allAnswered) {
        this.age = age;
        this.reportID = reportID;
        this.directoryAbsPath = directoryAbsPath;
        activityCallback = allAnswered;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogStyle);
        clearSharedPrefs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_parents_questionary, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPagerContainer = (ViewPagerContainer) view.findViewById(R.id.view_pager_container);
        mViewPager = mViewPagerContainer.getViewPager();
        nextButton = (Button) view.findViewById(R.id.next_button);
        backButton = (Button) view.findViewById(R.id.back_button);
        setUpSlider(view);
    }

    private void clearSharedPrefs() {
        ParentsAnswersSharedPrefs.clear(getActivity());
    }

    private void setUpSlider(View rootView) {
        LinearLayout ciclesView = (LinearLayout) rootView.findViewById(R.id.navigationCircles);
        for (int i = 0; i < 7; i ++) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.item_circle, null, false);
            View circle = view.findViewById(R.id.itemCircle);
            if (i == 0) {
                circle.setBackgroundResource(R.drawable.demo_circle_selected);
            }
            ciclesView.addView(view);
            mCircles.add(circle);
        }
        ciclesView.invalidate();
        mViewPagerContainer.setCircles(mCircles);

        final ParentsQuestionsAdapter adapter = new ParentsQuestionsAdapter(getActivity(), this);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setClipChildren(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 6) {
                    nextButton.setText(getString(R.string.ready));
                } else {
                    nextButton.setText(getString(R.string.next));
                }
                if (position > 0) {
                    backButton.setVisibility(View.VISIBLE);
                } else {
                    backButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewPager.getCurrentItem() == 6) {
                    if (activityCallback != null) {
                        activityCallback.allAnswered(age, reportID, System.currentTimeMillis(), directoryAbsPath);
                    }
                } else {
                    mViewPager.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                        }
                    }, 100);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
                    }
                }, 100);
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void anyVariantClick() {
        nextButton.performClick();
    }

}
