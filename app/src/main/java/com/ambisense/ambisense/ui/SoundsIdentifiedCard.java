package com.ambisense.ambisense.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ambisense.ambisense.R;


public class SoundsIdentifiedCard extends RelativeLayout {

    public SoundsIdentifiedCard(Context context) {
        super(context);
    }

    public SoundsIdentifiedCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SoundsIdentifiedCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    public SoundsIdentifiedCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }


    private void init(Context context, AttributeSet attrs){
        inflate(context, R.layout.sounds_identified_card_layout, this);
        TypedArray attrArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SoundsIdentifiedCard, 0, 0);
        // RelativeLayout containerLayout = new RelativeLayout(context, attrs);
        String iconSrc = attrArray.getString(R.styleable.SoundsIdentifiedCard_iconSrc);
        String counter = attrArray.getString(R.styleable.SoundsIdentifiedCard_counter);
        String name = attrArray.getString(R.styleable.SoundsIdentifiedCard_name);

        // ImageView code
        ImageView imageView = (ImageView) findViewById(R.id.soundsIdentifiedCard_imageView);
        int imageViewID = generateViewId();
        imageView.setId(imageViewID);
        imageView.setImageResource(context.getResources().getIdentifier("drawable/" + iconSrc, null, "com.ambisense.ambisense"));

        // TextView1 code
        TextView textView1 = (TextView) findViewById(R.id.soundsIdentifiedCard_textView1);

        RelativeLayout.LayoutParams textViewLayout1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView1.setLayoutParams(textViewLayout1);
        textViewLayout1.addRule(RelativeLayout.END_OF, imageViewID);
        textView1.setText(counter);

        int textView1ID = generateViewId();
        textView1.setId(textView1ID);

        // TextView2 code
        TextView textView2 = (TextView) findViewById(R.id.soundsIdentifiedCard_textView2);

        RelativeLayout.LayoutParams textViewLayout2 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView2.setLayoutParams(textViewLayout2);
        textViewLayout2.addRule(RelativeLayout.ALIGN_START, textView1ID);
        textViewLayout2.addRule(RelativeLayout.BELOW, textView1ID);
        textView2.setText(name);

    }

}
