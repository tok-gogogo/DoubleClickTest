package com.tokg.doubleclicktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
/**
 * Created by TokG on 18/11/11.
 */
public class MainActivity extends AppCompatActivity implements RxView.Action1<View> {
    static String TAG = "MainActivity";
    private static long mLastClickTime = 0;
    public static final long TIME_INTERVAL = 1000L;
    private Button btTest1, btTest2, btTest3, btTest5;
    private Button btTest4;

    private void initView() {
        btTest1 = findViewById(R.id.btn1);
        btTest2 = findViewById(R.id.btn2);
        btTest3 = findViewById(R.id.btn3);
        btTest4 = findViewById(R.id.btn4);
        btTest5 = findViewById(R.id.btn5);

        btTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long nowTime = System.currentTimeMillis();
                if (nowTime - mLastClickTime > TIME_INTERVAL) {
                    // do something
                    mLastClickTime = nowTime;
                } else {
                    Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btTest2.setOnClickListener(new IClickListener() {
            @Override
            protected void onIClick(View v) {
                //do something
            }

            @Override
            protected void onReClick(View v) {
                Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
            }
        });

        btTest3.setOnClickListener(new ClickProxy(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
            }
        }, new ClickProxy.IreClick() {
            @Override
            public void onReClick() {
                Toast.makeText(MainActivity.this, "不要重复点击", Toast.LENGTH_SHORT).show();
            }
        }));

        //  ClickFilter.setFilter(btTest4);
        RxView.setOnClickListeners(this, btTest4);

        btTest5.setOnClickListener(new View.OnClickListener() {
            @DoubleCLickMethod
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btn5 click");
            }
        });

    }


    @Override
    public void onMyClick(View v) {

        switch (v.getId()) {
            case R.id.btn4:
                Log.i(TAG, "btn4 click");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public abstract class IClickListener implements View.OnClickListener {
        @Override
        public final void onClick(View v) {
            if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
                onIClick(v);
                mLastClickTime = System.currentTimeMillis();
            } else {
                onReClick(v);
            }
        }

        protected abstract void onIClick(View v);

        protected abstract void onReClick(View v);
    }

    public static class ClickProxy implements View.OnClickListener {

        private View.OnClickListener origin;
        private IreClick mIreClick;

        public ClickProxy(View.OnClickListener origin, IreClick mIreClick) {
            this.origin = origin;
            this.mIreClick = mIreClick;
        }

        public ClickProxy(View.OnClickListener origin) {
            this.origin = origin;
        }

        @Override
        public void onClick(View v) {
            if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
                Log.i(TAG, "ClickProxy in");
                origin.onClick(v);
                mLastClickTime = System.currentTimeMillis();
            } else {
                if (mIreClick != null) mIreClick.onReClick();
            }
        }

        public interface IreClick {
            void onReClick();//重复点击
        }
    }

//    //提供一个静态方法
//    public static class ClickFilter {
//        public static void setFilter(View view) {
//            try {
//                Field field = View.class.getDeclaredField("mListenerInfo");
//                field.setAccessible(true);
//                Class listInfoType = field.getType();
//                Object listinfo = field.get(view);
//                Field onclickField = listInfoType.getField("mOnClickListener");
//                View.OnClickListener origin = (View.OnClickListener) onclickField.get(listinfo);
//                onclickField.set(listinfo, new ClickProxy(origin));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


}


