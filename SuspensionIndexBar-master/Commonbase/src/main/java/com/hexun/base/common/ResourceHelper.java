package com.hexun.base.common;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 *
 * @author zhangpeiyuan
 * @date 2017/10/19
 */

public class ResourceHelper {

    private static Application app;

    private ResourceHelper(){}

    public static void init(Application context){
        if (app == null){
            app = context;
        }
    }


    public static String getString(@StringRes int id){
        return app.getResources().getString(id);
    }

    public String[] getStringArray(@ArrayRes int id)
            throws Resources.NotFoundException {
        return app.getResources().getStringArray(id);
    }

    public static Drawable getDrawable(@DrawableRes int id){
        return app.getResources().getDrawable(id);
    }

    public int getColor(@ColorRes int id) {
        return app.getResources().getColor(id);
    }


    public AssetManager getAssets(){
        return app.getResources().getAssets();
    }


}
