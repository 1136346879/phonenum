package com.hexun.base.exception;

import android.app.Application;

import com.hexun.base.common.HToast;

/**
 * Created by hexun on 2017/10/19.
 */

public class ErrorHandler {

    private static ErrorHandler errorHandler;
    private static Application app;

    public static final int NULL_DATA = 10000;
    public static final int PARSE_DATA_EXCEPTION = 10001;
    public static final int NET_EXCEPTION = 10002;

    private ErrorHandler(Application application){
        app = application;
    }

    public static void init(Application application){
        if (errorHandler == null){
            errorHandler = new ErrorHandler(application);
        }
    }


    public static void showNetWorkError(String message){
        HToast.shortToast(app,message);
    }


}
