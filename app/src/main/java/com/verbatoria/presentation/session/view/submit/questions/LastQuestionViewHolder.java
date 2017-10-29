package com.verbatoria.presentation.session.view.submit.questions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import com.remnev.verbatoriamini.R;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * View Holder для последнего вопроса да/нет
 *
 * @author nikitaremnev
 */
public class LastQuestionViewHolder {

    @Inject
    public Context mContext;

    @BindView(R.id.answer_yes)
    public Button mAnswerYesButton;

    @BindView(R.id.answer_no)
    public Button mAnswerNoButton;

    @BindDrawable(R.drawable.background_button_selected)
    public Drawable mSelectedButtonDrawable;

    @BindDrawable(R.drawable.background_button_unselected)
    public Drawable mUnselectedButtonDrawable;

    private View mRootView;

    private IAnswerClickCallback mAnswerClickCallback;

    private QuestionsAdapter mQuestionsAdapter;

    LastQuestionViewHolder(QuestionsAdapter questionsAdapter, IAnswerClickCallback answerClickCallback, View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
        mAnswerClickCallback = answerClickCallback;
        mQuestionsAdapter = questionsAdapter;
    }

    public void bind() {
        setUpButtons();
    }

    public void selectAnswer(int value) {
        clearAnswers();
        if (value == 1) {
            mAnswerYesButton.setBackground(mSelectedButtonDrawable);
        } else {
            mAnswerNoButton.setBackground(mSelectedButtonDrawable);
        }
    }

    private void setUpButtons() {
        mAnswerYesButton.setOnClickListener(v -> buttonClick(true));
        mAnswerNoButton.setOnClickListener(v -> buttonClick(false));
    }

    private void buttonClick(boolean yes) {
        int value = yes ? 1 : 2;
        mQuestionsAdapter.addAnswer(getQuestionPosition(), value);
        selectAnswer(value);
        mAnswerClickCallback.onAnswerClicked();
    }

    private void clearAnswers() {
        mAnswerYesButton.setBackground(mUnselectedButtonDrawable);
        mAnswerNoButton.setBackground(mUnselectedButtonDrawable);
    }

    private int getQuestionPosition() {
        return (int) mRootView.getTag();
    }

}
