package com.tokg.doubleclicktest;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
/**
 * Created by TokG on 18/11/11.
 */
public class ThreeButton extends AppCompatButton implements View.OnClickListener {
    String TAG = "ThreeButton";

    public ThreeButton(Context context) {
        this(context, null);
    }

    public ThreeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_DOWN:
//                float x = event.getX();
//                float y = event.getY();
//
//
//
//                return true;
//
//
//            case MotionEvent.ACTION_UP:
//
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }
    @Override
    public void onClick(View v){
        Log.i(TAG,"内部消耗");
    }
}
