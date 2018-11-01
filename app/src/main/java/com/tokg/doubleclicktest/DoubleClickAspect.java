package com.tokg.doubleclicktest;

import android.util.Log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by TokG on 18/11/11.
 */
@Aspect
public class DoubleClickAspect {
    private Long mLastClickTime = 0L;
    private final Long TIME_INTERVAL = 1000L;
    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilterHook(ProceedingJoinPoint joinPoint) {
        if (System.currentTimeMillis() - mLastClickTime >= TIME_INTERVAL) {
            mLastClickTime = System.currentTimeMillis();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e("ClickFilterHook", "不要重复点击");
        }
    }
}
