package com.hexun.base.router;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * Created by hexun on 2017/9/20.
 */
@Interceptor(priority = 5, name = "未登录拦截跳转登陆")
public class LoginInterceptor implements IInterceptor {

    private Context app;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        app = context;
    }
}
