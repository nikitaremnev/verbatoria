package com.remnev.verbatoriamini.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;

/**
 * Created by nikitaremnev on 11.03.17.
 */

public class ParentsQuestionsAdapter extends PagerAdapter {

    private static final int TOTAL_COUNT = 7;

    private View mCurrentView;
    private final Context mContext;
    private IVariantButtonClick variantButtonClickCallback;

    private String[] mTitlesOfQuestions;

    public ParentsQuestionsAdapter(Context mContext, IVariantButtonClick variantButtonClickCallback) {
        this.mContext = mContext;
        mTitlesOfQuestions = mContext.getResources().getStringArray(R.array.parents_questions);
        this.variantButtonClickCallback = variantButtonClickCallback;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_question_parent, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        final Button button10 = (Button) view.findViewById(R.id.button10);
        final Button button20 = (Button) view.findViewById(R.id.button20);
        final Button button40 = (Button) view.findViewById(R.id.button40);
        final Button button60 = (Button) view.findViewById(R.id.button60);
        final Button button90 = (Button) view.findViewById(R.id.button90);

        title.setText(mTitlesOfQuestions[position]);

        container.addView(view);
        view.setTag(position);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof Button) {
                    //ui
                    setAllButtonsUnselected(button10, button20, button40, button60, button90);
                    Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_code_form_pressed);
                    view.setBackground(drawable);
                    //save
                    int value = Integer.parseInt(((Button) view).getText().toString());
                    ParentsAnswersSharedPrefs.setValue(mContext, Integer.toString(position), value);
                    variantButtonClickCallback.anyVariantClick();
                }
            }
        };

        button10.setOnClickListener(onClickListener);
        button20.setOnClickListener(onClickListener);
        button40.setOnClickListener(onClickListener);
        button60.setOnClickListener(onClickListener);
        button90.setOnClickListener(onClickListener);

        int value = ParentsAnswersSharedPrefs.getValue(mContext, Integer.toString(position));
        setAllButtonsUnselected(button10, button20, button40, button60, button90);
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_code_form_pressed);
        switch (value) {
            case 10:
                button10.setBackground(drawable);
                break;
            case 20:
                button20.setBackground(drawable);
                break;
            case 40:
                button40.setBackground(drawable);
                break;
            case 60:
                button60.setBackground(drawable);
                break;
            case 90:
                button90.setBackground(drawable);
                break;
        }

        return view;
    }

    private void setAllButtonsUnselected(View button10, View button20, View button40, View button60, View button90) {
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_code_form);
        button10.setBackground(drawable);
        button20.setBackground(drawable);
        button40.setBackground(drawable);
        button60.setBackground(drawable);
        button90.setBackground(drawable);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public int getCount() {
        return TOTAL_COUNT;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentView = (View) object;
    }

    public interface IVariantButtonClick {
        void anyVariantClick();
    }
}