package com.verbatoria.presentation.session.view.submit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;
import com.verbatoria.VerbatoriaApplication;

import javax.inject.Inject;

/**
 * Адаптер для отображения вопросов
 *
 * @author nikitaremnev
 */
public class QuestionsAdapter extends PagerAdapter {

    public static final int OFFSCREEN_PAGE_LIMIT = 2;
    public static final int QUESTIONARY_SIZE = 7;

    @Inject
    public Context mContext;

    private String[] mQuestionsArray;

    public QuestionsAdapter() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
        mQuestionsArray = mContext.getResources().getStringArray(R.array.session_questions);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View rootView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_question, null);
        rootView.setTag(position);

        QuestionViewHolder questionViewHolder = new QuestionViewHolder(rootView);
        questionViewHolder.bind(mQuestionsArray[position]);
        container.addView(rootView);

        int value = ParentsAnswersSharedPrefs.getValue(mContext, Integer.toString(position));
        questionViewHolder.selectAnswer(value);

        return rootView;
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

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        mCurrentView = (View) object;
    }

    public interface IVariantButtonClick {
        void anyVariantClick();
    }

}