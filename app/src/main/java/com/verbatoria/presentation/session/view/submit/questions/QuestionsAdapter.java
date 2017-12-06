package com.verbatoria.presentation.session.view.submit.questions;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Адаптер для отображения вопросов
 *
 * @author nikitaremnev
 */
public class QuestionsAdapter extends PagerAdapter {

    public static final int OFFSCREEN_PAGE_LIMIT = 2;
    public static final int QUESTIONARY_SIZE = 9;

    @Inject
    public Context mContext;

    private String[] mQuestionsArray;

    private Map<String, String> mAnswersMap;

    private IAnswerClickCallback mAnswerClickCallback;

    public QuestionsAdapter(IAnswerClickCallback answerClickCallback) {
        mAnswersMap = new HashMap<>();
        mAnswerClickCallback = answerClickCallback;
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mQuestionsArray = mContext.getResources().getStringArray(R.array.session_questions);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        if (position == QUESTIONARY_SIZE - 1) {
            View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_report_type_question, null);
            rootView.setTag(position);

            ReportTypeQuestionViewHolder reportTypeQuestionViewHolder = new ReportTypeQuestionViewHolder(this, mAnswerClickCallback, rootView);
            reportTypeQuestionViewHolder.bind();
            container.addView(rootView);

            String value = mAnswersMap.get(Integer.toString(position));
            if (value != null) {
                reportTypeQuestionViewHolder.selectAnswer(Integer.parseInt(value));
            } else {
                reportTypeQuestionViewHolder.addAnswer(0);
                reportTypeQuestionViewHolder.selectAnswer(0);
            }
            return rootView;
        } else if (position == QUESTIONARY_SIZE - 2) {
            View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_include_attention_question, null);
            rootView.setTag(position);

            IncludeAttentionQuestionViewHolder includeAttentionQuestionViewHolder = new IncludeAttentionQuestionViewHolder(this, mAnswerClickCallback, rootView);
            includeAttentionQuestionViewHolder.bind();
            container.addView(rootView);

            String value = mAnswersMap.get(Integer.toString(position));
            if (value != null) {
                includeAttentionQuestionViewHolder.selectAnswer(Integer.parseInt(value));
            }
            return rootView;
        } else {
            View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_question, null);
            rootView.setTag(position);

            QuestionViewHolder questionViewHolder = new QuestionViewHolder(this, mAnswerClickCallback, rootView);
            questionViewHolder.bind(mQuestionsArray[position]);
            container.addView(rootView);

            String value = mAnswersMap.get(Integer.toString(position));
            if (value != null) {
                questionViewHolder.selectAnswer(Integer.parseInt(value));
            }
            return rootView;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public int getCount() {
        return QUESTIONARY_SIZE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public boolean isAllQuestionsAnswered() {
        //6
        for (int i = 0; i < QUESTIONARY_SIZE; i ++) {
            if (!mAnswersMap.containsKey(String.valueOf(i))) {
                return false;
            }
        }
        return true;
    }

    public Map<String, String> getAnswers() {
        return mAnswersMap;
    }

    void addAnswer(int position, int value) {
        mAnswersMap.put(Integer.toString(position), Integer.toString(value));
    }

    void removeAnswer(int position) {
        mAnswersMap.remove(Integer.toString(position));
    }

}