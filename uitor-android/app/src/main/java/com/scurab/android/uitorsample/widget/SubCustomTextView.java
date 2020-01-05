package com.scurab.android.uitorsample.widget;

import android.content.Context;
import android.util.AttributeSet;

public class SubCustomTextView extends CustomTextView {

    private String mCustomValue = "CustomValue";

    public SubCustomTextView(Context context) {
        super(context);
    }

    public SubCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
