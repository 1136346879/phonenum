package com.news.extend;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * Created by hexun on 2017/9/19.
 */
@Aspect
public class LifeCycleAOP {

    private static final String lifeCycleMethod = "* com.zhangpy.**.*Activity.on**(..)";

    private static final String TAG = "LifeCycle";


    @Around("execution(* com.zhangpy.base.AppApplication.onCreate(..))")
    public void onApplicationCreate(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().toString();
        long start = System.currentTimeMillis();
        proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        Log.d(TAG, key + " Time: " + (end - start) + "ms");
    }

    @Around("execution("+lifeCycleMethod+")")
    public void onActivityMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().toString();
        long start = System.currentTimeMillis();
        proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        Log.d(TAG, key + " Time: " + (end - start) + "ms");
    }

}
