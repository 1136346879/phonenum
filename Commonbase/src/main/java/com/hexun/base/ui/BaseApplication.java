package com.hexun.base.ui;

import android.app.Application;
import android.content.Context;

/**
 * @author yangyi 2017年10月19日10:12:30
 */

public abstract class BaseApplication extends Application {

    /**
     * 初始化应用相关配置  如第三方SDK的各种初始化操作
     */
    protected abstract void initApplicationConfigOnCreate();

    /**
     * 初始化应用相关配置  有的第三方的初始化操作需要在attachBaseContext周期中进行
     */
    protected abstract void initApplicationConfigAttach();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        initApplicationConfigAttach();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplicationConfigOnCreate();
    }
}
