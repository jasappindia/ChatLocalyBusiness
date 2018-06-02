package com.chatlocalybusiness.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by anjani on 29/3/17.
 */

public class EditTextViewBold extends EditText {
    public EditTextViewBold(Context context) {
        super(context);
    }

    public EditTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);

    }

    public EditTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       applyCustomFont(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public EditTextViewBold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);applyCustomFont(context);

    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("RobotoBold.ttf", context);

        setTypeface(customFont);
    }
}
