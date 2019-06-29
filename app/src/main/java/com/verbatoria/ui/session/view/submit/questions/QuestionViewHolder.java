package com.verbatoria.ui.session.view.submit.questions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.remnev.verbatoria.R;
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

    private IAnswerClickCallback mAnswerClickCallback;

    private QuestionsAdapter mQuestionsAdapter;

    QuestionViewHolder(QuestionsAdapter questionsAdapter, IAnswerClickCallback answerClickCallback, View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
        mAnswerClickCallback = answerClickCallback;
        mQuestionsAdapter = questionsAdapter;
    }

    public void bind(String questionTitle) {
        mQuestionTextView.setText(questionTitle);
        setUpButtons();
        setUpHasAnswerOption();
    }

    public void selectAnswer(int value) {
        clearAnswers();
        mHasAnswerCheckBox.setChecked(false);
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
        mAnswer10Button.setOnClickListener(this::buttonClick);
        mAnswer20Button.setOnClickListener(this::buttonClick);
        mAnswer40Button.setOnClickListener(this::buttonClick);
        mAnswer60Button.setOnClickListener(this::buttonClick);
        mAnswer90Button.setOnClickListener(this::buttonClick);
    }

    private void setUpHasAnswerOption() {
        mHasAnswerCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                clearAnswers();
                mQuestionsAdapter.removeAnswer(getQuestionPosition());
                mAnswerClickCallback.onAnswerClicked();
            }
        });
    }

    private void buttonClick(View view) {
        int value = Integer.parseInt(((Button) view).getText().toString());
        mQuestionsAdapter.addAnswer(getQuestionPosition(), value);
        selectAnswer(value);
        mAnswerClickCallback.onAnswerClicked();
    }

    private void clearAnswers() {
        mAnswer10Button.setBackground(mUnselectedButtonDrawable);
        mAnswer20Button.setBackground(mUnselectedButtonDrawable);
        mAnswer40Button.setBackground(mUnselectedButtonDrawable);
        mAnswer60Button.setBackground(mUnselectedButtonDrawable);
        mAnswer90Button.setBackground(mUnselectedButtonDrawable);
    }

    private int getQuestionPosition() {
        return (int) mRootView.getTag();
    }

}
