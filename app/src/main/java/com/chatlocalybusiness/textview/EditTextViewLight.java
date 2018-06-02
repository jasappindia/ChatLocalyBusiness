package com.chatlocalybusiness.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by anjani on 29/3/17.
 */

public class EditTextViewLight extends EditText {
    public EditTextViewLight(Context context) {
        super(context);
        applyCustomFont(context);

    }

    public EditTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public EditTextViewLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditTextViewLight(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);

    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("RobotoCondensedLight.ttf", context);
        setTypeface(customFont);
    }
}
