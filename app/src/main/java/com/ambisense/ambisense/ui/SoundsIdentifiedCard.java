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
        ImageView imageView = findViewById(R.id.soundsIdentifiedCard_imageView);
//        int imageViewID = generateViewId();
//        imageView.setId(imageViewID);
        imageView.setImageResource(context.getResources().getIdentifier("drawable/" + iconSrc, null, "com.ambisense.ambisense"));

        // TextView1 code
        TextView textView1 = findViewById(R.id.soundsIdentifiedCard_textView1);

//        RelativeLayout.LayoutParams textViewLayout1 = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        textView1.setLayoutParams(textViewLayout1);
//        textViewLayout1.addRule(RelativeLayout.END_OF, imageViewID);
//        textView1.setText(counter);


        LayoutParams textViewLayout1 = (LayoutParams) textView1.getLayoutParams();
        textViewLayout1.addRule(RelativeLayout.END_OF, R.id.soundsIdentifiedCard_imageView);
        textView1.setText(counter);

//        int textView1ID = generateViewId();
//        textView1.setId(textView1ID);

        // TextView2 code
        TextView textView2 = findViewById(R.id.soundsIdentifiedCard_textView2);

//        LayoutParams textViewLayout2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        textView2.setLayoutParams(textViewLayout2);

        LayoutParams textViewLayout2 = (LayoutParams) textView2.getLayoutParams();
        textViewLayout2.addRule(RelativeLayout.ALIGN_START, R.id.soundsIdentifiedCard_textView1);
        textViewLayout2.addRule(RelativeLayout.BELOW, R.id.soundsIdentifiedCard_textView1);
        textView2.setText(name);

    }

}
