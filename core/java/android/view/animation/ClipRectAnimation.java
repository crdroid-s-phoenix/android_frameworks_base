/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.view.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

/**
 * An animation that controls the clip of an object. See the
 * {@link android.view.animation full package} description for details and
 * sample code.
 *
 * @hide
 */
public class ClipRectAnimation extends Animation {
    protected final Rect mFromRect = new Rect();
    protected final Rect mToRect = new Rect();

    private int mFromLeftType = ABSOLUTE;
    private int mFromTopType = ABSOLUTE;
    private int mFromRightType = ABSOLUTE;
    private int mFromBottomType = ABSOLUTE;

    private int mToLeftType = ABSOLUTE;
    private int mToTopType = ABSOLUTE;
    private int mToRightType = ABSOLUTE;
    private int mToBottomType = ABSOLUTE;

    private float mFromLeftValue;
    private float mFromTopValue;
    private float mFromRightValue;
    private float mFromBottomValue;

    private float mToLeftValue;
    private float mToTopValue;
    private float mToRightValue;
    private float mToBottomValue;

    /**
     * Constructor used when a ClipRectAnimation is loaded from a resource.
     *
     * @param context Application context to use
     * @param attrs Attribute set from which to read values
     */
    public ClipRectAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                com.android.internal.R.styleable.ClipRectAnimation);

        Description d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_fromLeft));
        mFromLeftType = d.type;
        mFromLeftValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_fromTop));
        mFromTopType = d.type;
        mFromTopValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_fromRight));
        mFromRightType = d.type;
        mFromRightValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_fromBottom));
        mFromBottomType = d.type;
        mFromBottomValue = d.value;


        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_toLeft));
        mToLeftType = d.type;
        mToLeftValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_toTop));
        mToTopType = d.type;
        mToTopValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_toRight));
        mToRightType = d.type;
        mToRightValue = d.value;

        d = Description.parseValue(a.peekValue(
                com.android.internal.R.styleable.ClipRectAnimation_toBottom));
        mToBottomType = d.type;
        mToBottomValue = d.value;

        a.recycle();
    }

    /**
     * Constructor to use when building a ClipRectAnimation from code
     *
     * @param fromClip the clip rect to animate from
     * @param toClip the clip rect to animate to
     */
    public ClipRectAnimation(Rect fromClip, Rect toClip) {
        if (fromClip == null || toClip == null) {
            throw new RuntimeException("Expected non-null animation clip rects");
        }
        mFromLeftValue = fromClip.left;
        mFromTopValue = fromClip.top;
        mFromRightValue= fromClip.right;
        mFromBottomValue = fromClip.bottom;

        mToLeftValue = toClip.left;
        mToTopValue = toClip.top;
        mToRightValue= toClip.right;
        mToBottomValue = toClip.bottom;
    }

    /**
     * Constructor to use when building a ClipRectAnimation from code
     */
    public ClipRectAnimation(int fromL, int fromT, int fromR, int fromB,
            int toL, int toT, int toR, int toB) {
        this(new Rect(fromL, fromT, fromR, fromB), new Rect(toL, toT, toR, toB));
    }

    public ClipRectAnimation(float fromL, float fromT, float fromR, float fromB,
            float toL, float toT, float toR, float toB) {
        mFromLeftType = RELATIVE_TO_SELF;
        mFromTopType = RELATIVE_TO_SELF;
        mFromRightType = RELATIVE_TO_SELF;
        mFromBottomType = RELATIVE_TO_SELF;

        mToLeftType = RELATIVE_TO_SELF;
        mToTopType = RELATIVE_TO_SELF;
        mToRightType = RELATIVE_TO_SELF;
        mToBottomType = RELATIVE_TO_SELF;

        mFromLeftValue = fromL;
        mFromTopValue = fromT;
        mFromRightValue= fromR;
        mFromBottomValue = fromB;

        mToLeftValue = toL;
        mToTopValue = toT;
        mToRightValue= toR;
        mToBottomValue = toB;
    }

    @Override
    protected void applyTransformation(float it, Transformation tr) {
        int l = mFromRect.left + (int) ((mToRect.left - mFromRect.left) * it);
        int t = mFromRect.top + (int) ((mToRect.top - mFromRect.top) * it);
        int r = mFromRect.right + (int) ((mToRect.right - mFromRect.right) * it);
        int b = mFromRect.bottom + (int) ((mToRect.bottom - mFromRect.bottom) * it);
        tr.setClipRect(l, t, r, b);
    }

    @Override
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mFromRect.set((int) resolveSize(mFromLeftType, mFromLeftValue, width, parentWidth),
                (int) resolveSize(mFromTopType, mFromTopValue, height, parentHeight),
                (int) resolveSize(mFromRightType, mFromRightValue, width, parentWidth),
                (int) resolveSize(mFromBottomType, mFromBottomValue, height, parentHeight));
        mToRect.set((int) resolveSize(mToLeftType, mToLeftValue, width, parentWidth),
                (int) resolveSize(mToTopType, mToTopValue, height, parentHeight),
                (int) resolveSize(mToRightType, mToRightValue, width, parentWidth),
                (int) resolveSize(mToBottomType, mToBottomValue, height, parentHeight));
    }
}
