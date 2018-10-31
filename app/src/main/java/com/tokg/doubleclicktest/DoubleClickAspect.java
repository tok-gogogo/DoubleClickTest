package com.tokg.doubleclicktest;

import android.util.Log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by Tony Shen on 16/3/28.
 */
@Aspect
public class DoubleClickAspect {


    private Long sLastclick = 0L;
    private final Long FILTER_TIMEM = 1000L;

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilterHook(ProceedingJoinPoint joinPoint) {
        if (System.currentTimeMillis() - sLastclick >= FILTER_TIMEM) {
            sLastclick = System.currentTimeMillis();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e("ClickFilterHook", "重复点击,已过滤");
        }
    }


}
