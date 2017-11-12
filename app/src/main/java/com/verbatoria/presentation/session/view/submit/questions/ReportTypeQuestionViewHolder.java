package com.verbatoria.presentation.session.view.submit.questions;

import android.content.Context;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * View Holder для последнего вопроса да/нет
 *
 * @author nikitaremnev
 */
public class ReportTypeQuestionViewHolder {

    @Inject
    public Context mContext;

    private View mRootView;

    private IAnswerClickCallback mAnswerClickCallback;

    private QuestionsAdapter mQuestionsAdapter;

    ReportTypeQuestionViewHolder(QuestionsAdapter questionsAdapter, IAnswerClickCallback answerClickCallback, View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
        mAnswerClickCallback = answerClickCallback;
        mQuestionsAdapter = questionsAdapter;
    }

    public void bind() {
//        setUpButtons();
    }

    public void selectAnswer(int value) {
//        clearAnswers();
//        if (value == 1) {
//            mAnswerYesButton.setBackground(mSelectedButtonDrawable);
//        } else {
//            mAnswerNoButton.setBackground(mSelectedButtonDrawable);
//        }
    }
//
//    private void setUpButtons() {
//        mAnswerYesButton.setOnClickListener(v -> buttonClick(true));
//        mAnswerNoButton.setOnClickListener(v -> buttonClick(false));
//    }
//
//    private void buttonClick(boolean yes) {
//        int value = yes ? 1 : 2;
//        mQuestionsAdapter.addAnswer(getQuestionPosition(), value);
//        selectAnswer(value);
//        mAnswerClickCallback.onAnswerClicked();
//    }
//
//    private void clearAnswers() {
//        mAnswerYesButton.setBackground(mUnselectedButtonDrawable);
//        mAnswerNoButton.setBackground(mUnselectedButtonDrawable);
//    }

    private int getQuestionPosition() {
        return (int) mRootView.getTag();
    }

}
