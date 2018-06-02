package com.chatlocalybusiness.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anjani on 29/3/17.
 */

public class  EditTextViewReguler extends EditText {
    public EditTextViewReguler(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public EditTextViewReguler(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);

    }

    public EditTextViewReguler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       applyCustomFont(context);

    }


    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("RobotoRegular.ttf", context);
        setTypeface(customFont);
    }
}
