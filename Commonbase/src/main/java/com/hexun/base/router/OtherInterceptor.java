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
@Interceptor(priority = 3, name = "业务拦截器")
public class OtherInterceptor implements IInterceptor {

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
