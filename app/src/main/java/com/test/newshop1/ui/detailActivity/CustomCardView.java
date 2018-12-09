package com.test.newshop1.ui.detailActivity;

import android.content.Context;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;

public class CustomCardView extends CardView {
    private static final String TAG = "CustomCardView";
    private boolean expand = false;
    public CustomCardView(Context context) {
        super(context);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void toggle(Button b){
        if (expand){
            expand = false;
            collapse(500,b);

        } else {
            expand = true;
            expand(b);
        }
    }

    private void expand(final Button b) {
        final int initialHeight = getHeight();

        measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        int targetHeight = getMeasuredHeight();

        final int distanceToExpand = targetHeight - initialHeight;

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime > 0.5){
                    b.setText("بستن");
                }

                getLayoutParams().height = (int) (initialHeight + (distanceToExpand * interpolatedTime));
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) distanceToExpand);
        a.setInterpolator(new FastOutLinearInInterpolator());
        startAnimation(a);
    }

    private void collapse(int collapsedHeight, final Button b) {
        final int initialHeight = getMeasuredHeight();

        final int distanceToCollapse = (int) (initialHeight - collapsedHeight);

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime > 0.5){
                    b.setText("ادامه...");
                }


                //Log.i(TAG, "Collapse | InterpolatedTime = " + interpolatedTime);

                getLayoutParams().height = (int) (initialHeight - (distanceToCollapse * interpolatedTime));
                requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((long) distanceToCollapse);
        a.setInterpolator(new LinearOutSlowInInterpolator());
        startAnimation(a);
    }
}
