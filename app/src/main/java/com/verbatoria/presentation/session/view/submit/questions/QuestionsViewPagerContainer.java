package com.verbatoria.presentation.session.view.submit.questions;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.remnev.verbatoria.R;

import java.util.ArrayList;

public class QuestionsViewPagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mPager;
    private boolean mNeedsRedraw;
    private ArrayList<View> mCircleViews;

    private final Point mCenter = new Point();
    private final Point mInitialTouch = new Point();

    public QuestionsViewPagerContainer(Context context) {
        super(context);
        init();
    }

    public QuestionsViewPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionsViewPagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setClipChildren(false);
        if (Build.VERSION.SDK_INT < 19) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            mPager = (ViewPager) getChildAt(0);
            mPager.addOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    public ViewPager getViewPager() {
        return mPager;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenter.x = w / 2;
        mCenter.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInitialTouch.x = (int) ev.getX();
                mInitialTouch.y = (int) ev.getY();
            default:
                ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
                break;
        }

        return mPager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mNeedsRedraw) invalidate();
    }

    public void setCircleViews(ArrayList<View> circleViews) {
        this.mCircleViews = circleViews;
    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mCircleViews.size(); i++) {
            if (position == i) {
                mCircleViews.get(i).setBackgroundResource(R.drawable.background_navigation_item_selected);
            } else {
                mCircleViews.get(i).setBackgroundResource(R.drawable.background_navigation_item_unselected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }
}
