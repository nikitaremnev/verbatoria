package com.verbatoria.presentation.session.view.submit.questions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.remnev.verbatoria.R;
import com.verbatoria.utils.PreferencesStorage;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbatoria.presentation.session.view.submit.questions.QuestionsAdapter.HOBBY_ANSWER_POSITION;

/**
 * View Holder для последнего вопроса да/нет
 *
 * @author nikitaremnev
 */


public class IncludeAttentionHobbyQuestionViewHolder {

    private static final int MINIMUM_HOBBY_AGE = 18;

    @Inject
    public Context mContext;

    @BindView(R.id.attention_answer_yes)
    public Button mAttentionAnswerYesButton;

    @BindView(R.id.attention_answer_no)
    public Button mAttentionAnswerNoButton;

    @BindView(R.id.hobby_question_text_view)
    public TextView mHobbyTitle;

    @BindView(R.id.hobby_buttons_layout)
    public View mHobbyButtonsLayout;

    @BindView(R.id.hobby_answer_yes)
    public Button mHobbyAnswerYesButton;

    @BindView(R.id.hobby_answer_no)
    public Button mHobbyAnswerNoButton;

    @BindDrawable(R.drawable.background_button_selected)
    public Drawable mSelectedButtonDrawable;

    @BindDrawable(R.drawable.background_button_unselected)
    public Drawable mUnselectedButtonDrawable;

    private View mRootView;

    private IAnswerClickCallback mAnswerClickCallback;

    private QuestionsAdapter mQuestionsAdapter;

    private boolean isAttentionSelected = false;

    private boolean isHobbySelected;

    IncludeAttentionHobbyQuestionViewHolder(QuestionsAdapter questionsAdapter, IAnswerClickCallback answerClickCallback, View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
        mAnswerClickCallback = answerClickCallback;
        mQuestionsAdapter = questionsAdapter;
        if (PreferencesStorage.getInstance().getCurrentAge() >= MINIMUM_HOBBY_AGE) {
            mHobbyButtonsLayout.setVisibility(View.VISIBLE);
            mHobbyTitle.setVisibility(View.VISIBLE);
            isHobbySelected = false;
        } else {
            mHobbyButtonsLayout.setVisibility(View.GONE);
            mHobbyTitle.setVisibility(View.GONE);
            mQuestionsAdapter.addAnswer(HOBBY_ANSWER_POSITION, 0);
            isHobbySelected = true;
        }
    }

    public void bind() {
        setUpButtons();
    }

    public void selectAttentionAnswer(int value) {
        clearAttentionAnswers();
        if (value == 1) {
            mAttentionAnswerYesButton.setBackground(mSelectedButtonDrawable);
        } else {
            mAttentionAnswerNoButton.setBackground(mSelectedButtonDrawable);
        }
        if (isHobbySelected) {
            mAnswerClickCallback.onAnswerClicked();
        }
    }

    public void selectHobbyAnswer(int value) {
        clearHobbyAnswers();
        if (value == 1) {
            mHobbyAnswerYesButton.setBackground(mSelectedButtonDrawable);
        } else {
            mHobbyAnswerNoButton.setBackground(mSelectedButtonDrawable);
        }
        if (isAttentionSelected) {
            mAnswerClickCallback.onAnswerClicked();
        }
    }

    private void setUpButtons() {
        mAttentionAnswerYesButton.setOnClickListener(v -> buttonAttentionClick(true));
        mAttentionAnswerNoButton.setOnClickListener(v -> buttonAttentionClick(false));
        mHobbyAnswerYesButton.setOnClickListener(v -> buttonHobbyClick(true));
        mHobbyAnswerNoButton.setOnClickListener(v -> buttonHobbyClick(false));
    }

    private void buttonAttentionClick(boolean yes) {
        isAttentionSelected = true;
        int value = yes ? 1 : 2;
        mQuestionsAdapter.addAnswer((int) mRootView.getTag(), value);
        selectAttentionAnswer(value);
    }

    private void buttonHobbyClick(boolean yes) {
        isHobbySelected = true;
        int value = yes ? 1 : 0;
        mQuestionsAdapter.addAnswer(HOBBY_ANSWER_POSITION, value);
        selectHobbyAnswer(value);
    }

    private void clearAttentionAnswers() {
        mAttentionAnswerYesButton.setBackground(mUnselectedButtonDrawable);
        mAttentionAnswerNoButton.setBackground(mUnselectedButtonDrawable);
    }

    private void clearHobbyAnswers() {
        mHobbyAnswerYesButton.setBackground(mUnselectedButtonDrawable);
        mHobbyAnswerNoButton.setBackground(mUnselectedButtonDrawable);
    }

}
