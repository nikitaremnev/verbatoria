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

import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.adapters.ParentsQuestionsAdapter;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;
import com.verbatoria.presentation.session.view.submit.ViewPagerContainer;

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

//    private IAllAnsweredCallback activityCallback;

    public QuestionaryDialogFragment() {}

//    public QuestionaryDialogFragment(String age, String reportID, String directoryAbsPath, IAllAnsweredCallback allAnswered) {
//        this.age = age;
//        this.reportID = reportID;
//        this.directoryAbsPath = directoryAbsPath;
//        activityCallback = allAnswered;
//    }

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
    }

    private void clearSharedPrefs() {
        ParentsAnswersSharedPrefs.clear(getActivity());
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
