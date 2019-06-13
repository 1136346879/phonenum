package com.hexun.base.common;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 *
 * @author hexun
 * @date 2017/10/19
 */

public class HToast {

    private HToast() {
    }

    public static void shortToast(Context context, int resId) {
        showToast(context, resId, 0);
    }

    public static void shortToast(Context context, String text) {
        if(!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            showToast(context, text, 0);
        }

    }

    public static void longToast(Context context, int resId) {
        showToast(context, resId, 1);
    }

    public static void longToast(Context context, String text) {
        if(!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
            showToast(context, text, 1);
        }

    }

    public static void showToast(Context context, int resId, int duration) {
        if(context != null) {
            if(context == null || !(context instanceof Activity) || !((Activity)context).isFinishing()) {
                String text = context.getString(resId);
                showToast(context, text, duration);
            }
        }
    }

    public static void showToast(Context context, String text, int duration) {
        if(context != null) {
            if(context == null || !(context instanceof Activity) || !((Activity)context).isFinishing()) {
                if(!TextUtils.isEmpty(text) && !"".equals(text.trim())) {
                    Toast.makeText(context, text, duration).show();
                }

            }
        }
    }
}