package com.ambisense.ambisense.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ambisense.ambisense.R;

public class TitleBar extends LinearLayout {


    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        inflate(context, R.layout.activity_title_bar, this);
        TypedArray attrArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, 0, 0);

        final String title = attrArray.getString(R.styleable.TitleBar_title);

        final TextView textView = findViewById(R.id.pageName);
        textView.setText(title);
    }

    public void setText(String title){
        final TextView textView = findViewById(R.id.pageName);
        textView.setText(title);
    }
}
