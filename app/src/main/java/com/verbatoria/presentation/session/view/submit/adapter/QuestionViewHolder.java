package com.verbatoria.presentation.session.view.submit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для вопроса
 *
 * @author nikitaremnev
 */
public class QuestionViewHolder {

    private static final int ANSWER_10 = 10;
    private static final int ANSWER_20 = 20;
    private static final int ANSWER_40 = 40;
    private static final int ANSWER_60 = 60;
    private static final int ANSWER_90 = 90;

    @Inject
    public Context mContext;

    @BindView(R.id.question_text_view)
    public TextView mQuestionTextView;

    @BindView(R.id.answer_10_button)
    public Button mAnswer10Button;

    @BindView(R.id.answer_20_button)
    public Button mAnswer20Button;

    @BindView(R.id.answer_40_button)
    public Button mAnswer40Button;

    @BindView(R.id.answer_60_button)
    public Button mAnswer60Button;

    @BindView(R.id.answer_90_button)
    public Button mAnswer90Button;

    @BindView(R.id.has_answer_checkbox)
    public CheckBox mHasAnswerCheckBox;

    @BindDrawable(R.drawable.background_button_selected)
    public Drawable mSelectedButtonDrawable;

    @BindDrawable(R.drawable.background_button_unselected)
    public Drawable mUnselectedButtonDrawable;

    private View mRootView;

    QuestionViewHolder(View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
    }

    public void bind(String questionTitle) {
        mQuestionTextView.setText(questionTitle);
        setUpButtons();
    }

    public void selectAnswer(int value) {
        clearAnswers();
        switch (value) {
            case ANSWER_10:
                mAnswer10Button.setBackground(mSelectedButtonDrawable);
                break;
            case ANSWER_20:
                mAnswer20Button.setBackground(mSelectedButtonDrawable);
                break;
            case ANSWER_40:
                mAnswer40Button.setBackground(mSelectedButtonDrawable);
                break;
            case ANSWER_60:
                mAnswer60Button.setBackground(mSelectedButtonDrawable);
                break;
            case ANSWER_90:
                mAnswer90Button.setBackground(mSelectedButtonDrawable);
                break;
        }
    }

    private void setUpButtons() {
        mAnswer10Button.setOnClickListener(view -> buttonClick(view));
        mAnswer20Button.setOnClickListener(view -> buttonClick(view));
        mAnswer40Button.setOnClickListener(view -> buttonClick(view));
        mAnswer60Button.setOnClickListener(view -> buttonClick(view));
        mAnswer90Button.setOnClickListener(view -> buttonClick(view));
    }

    private void buttonClick(View view) {
        int position = (int) mRootView.getTag();
        int value = Integer.parseInt(((Button) view).getText().toString());
        ParentsAnswersSharedPrefs.setValue(mContext, Integer.toString(position), value);
        selectAnswer(value);
    }

    private void clearAnswers() {
        mAnswer10Button.setBackground(mUnselectedButtonDrawable);
        mAnswer20Button.setBackground(mUnselectedButtonDrawable);
        mAnswer40Button.setBackground(mUnselectedButtonDrawable);
        mAnswer60Button.setBackground(mUnselectedButtonDrawable);
        mAnswer90Button.setBackground(mUnselectedButtonDrawable);
    }

}
