package com.remnev.verbatoriamini.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.remnev.verbatoriamini.R;


public class FilteredImageView extends ImageView {

    public FilteredImageView(Context context) {
        super(context);
    }

    public FilteredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilteredImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getDrawable().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

    }


}