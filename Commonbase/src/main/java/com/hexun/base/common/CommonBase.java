package com.hexun.base.common;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by zhangpeiyuan on 2017/9/19.
 */
public class CommonBase {

    private static Application app;
    public static CommonBase instance = new CommonBase();
    static BaseConfig config;


    private CommonBase() {
    }


    public static void init(@NonNull Application context){
        init(context,null);
    }


    public static void init(@NonNull Application context, BaseConfig config) {
        app = context;
        initBase(config);
    }


    private static void initBase(BaseConfig initConfig){
        if (config == null) {
            config = new BaseConfig.Builder()
                    .build();
        }
        config = initConfig;
        config.initProject(app);
    }


    public static BaseConfig getConfig(){
        if (config == null){
            throw new RuntimeException("Init First");
        }
        return config;
    }

}
