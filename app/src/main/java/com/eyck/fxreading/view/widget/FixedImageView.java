package com.eyck.fxreading.view.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Eyck on 2017/8/30.
 */

public class FixedImageView extends ImageView {
    private int mScreenHeight;
    public FixedImageView(Context context) {
        this(context,null);
    }

    public FixedImageView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public FixedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mScreenHeight = getScreenWidthHeight(context)[1];
    }

    private int[] getScreenWidthHeight(Context context) {
        int[] arrayOfInt = new int[2];
        if (context == null)
            return arrayOfInt;
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        int j = localDisplayMetrics.heightPixels;
        arrayOfInt[0] = i;
        arrayOfInt[1] = j;
        return arrayOfInt;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = View.MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(i,mScreenHeight);
    }
}
