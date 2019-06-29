package com.verbatoria.ui.session.view.submit.questions;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;

import com.remnev.verbatoria.R;

import javax.inject.Inject;

import butterknife.BindView;
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

    @BindView(R.id.type_0)
    public RadioButton mRadioButtonType0;

    @BindView(R.id.type_1)
    public RadioButton mRadioButtonType1;

    @BindView(R.id.type_2)
    public RadioButton mRadioButtonType2;

    @BindView(R.id.type_3)
    public RadioButton mRadioButtonType3;

    @BindView(R.id.type_4)
    public RadioButton mRadioButtonType4;

    @BindView(R.id.type_5)
    public RadioButton mRadioButtonType5;

    @BindView(R.id.type_6)
    public RadioButton mRadioButtonType6;

    @BindView(R.id.type_7)
    public RadioButton mRadioButtonType7;

    @BindView(R.id.type_8)
    public RadioButton mRadioButtonType8;

    @BindView(R.id.type_9)
    public RadioButton mRadioButtonType9;

    @BindView(R.id.type_10)
    public RadioButton mRadioButtonType10;

    private IAnswerClickCallback mAnswerClickCallback;

    private QuestionsAdapter mQuestionsAdapter;

    ReportTypeQuestionViewHolder(QuestionsAdapter questionsAdapter, IAnswerClickCallback answerClickCallback, View rootView) {
        ButterKnife.bind(this, rootView);
        mRootView = rootView;
        mAnswerClickCallback = answerClickCallback;
        mQuestionsAdapter = questionsAdapter;
    }

    public void bind() {
        setUpRadioGroups();
    }

    public void selectAnswer(int value) {
        switch (value) {
            case 0:
                mRadioButtonType0.setChecked(true);
                break;
            case 1:
                mRadioButtonType1.setChecked(true);
                break;
            case 2:
                mRadioButtonType2.setChecked(true);
                break;
            case 3:
                mRadioButtonType3.setChecked(true);
                break;
            case 4:
                mRadioButtonType4.setChecked(true);
                break;
            case 5:
                mRadioButtonType5.setChecked(true);
                break;
            case 6:
                mRadioButtonType6.setChecked(true);
                break;
            case 7:
                mRadioButtonType7.setChecked(true);
                break;
            case 8:
                mRadioButtonType8.setChecked(true);
                break;
            case 9:
                mRadioButtonType9.setChecked(true);
                break;
            case 10:
                mRadioButtonType10.setChecked(true);
                break;
        }
    }

    public void addAnswer(int value) {
        mQuestionsAdapter.addAnswer(getQuestionPosition(), value);
    }

    private void setUpRadioGroups() {
        mRadioButtonType0.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(0);
        });
        mRadioButtonType1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType0.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(1);
        });
        mRadioButtonType2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(2);
        });
        mRadioButtonType3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(3);
        });
        mRadioButtonType4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(4);
        });
        mRadioButtonType5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(5);
        });
        mRadioButtonType6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(6);
        });
        mRadioButtonType7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(7);
        });
        mRadioButtonType8.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(8);
        });
        mRadioButtonType9.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType0.setChecked(false);
            mRadioButtonType10.setChecked(false);
            addAnswer(9);
        });
        mRadioButtonType10.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                return;
            }
            mRadioButtonType1.setChecked(false);
            mRadioButtonType2.setChecked(false);
            mRadioButtonType3.setChecked(false);
            mRadioButtonType4.setChecked(false);
            mRadioButtonType5.setChecked(false);
            mRadioButtonType6.setChecked(false);
            mRadioButtonType7.setChecked(false);
            mRadioButtonType8.setChecked(false);
            mRadioButtonType9.setChecked(false);
            mRadioButtonType0.setChecked(false);
            addAnswer(10);
        });
    }

    private int getQuestionPosition() {
        return (int) mRootView.getTag();
    }

}
