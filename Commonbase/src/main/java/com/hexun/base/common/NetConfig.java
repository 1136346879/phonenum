package com.hexun.base.common;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by hexun on 2017/10/11.
 */

public class NetConfig {

    private static final int DEFAULT_TIMEOUT = 40;  //超时时间.单位秒
    private static final int DEFAULT_RETRY = 1;     //统一超时重连次数

    private static int timeout;                     //统一超时时间
    private static int retry;
    private static CacheMode cacheMode;             //全局统一缓存模式
    private static long cacheTime;                  //全局统一缓存时间
    private static HttpHeaders commonHeaders;       //全局统一请求头
    private static HttpParams commonParams;         //全局统一请求参数
    private static Interceptor interceptor;         //请求拦截器
    private static Interceptor networkInterceptor;  //网络请求拦截器
    private static CookieJar cookieJar;             //Cookie存储


    private NetConfig(Builder builder) {
        timeout = builder.timeout;
        cacheMode = builder.mode;
        cacheTime = builder.cacheTime;
        commonHeaders = builder.commonHeaders;
        commonParams = builder.commonParams;
        interceptor = builder.interceptor;
        cookieJar = builder.cookieJar;
        retry = builder.retry;
        networkInterceptor = builder.networkInterceptor;
    }


    void prepareNetwork(Application context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(timeout, TimeUnit.SECONDS);
        builder.writeTimeout(timeout, TimeUnit.SECONDS);
        builder.connectTimeout(timeout, TimeUnit.SECONDS);
        //使用Memory保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(cookieJar);

        if (interceptor != null) {
            builder.addInterceptor(interceptor);
        }
        if (networkInterceptor != null) {
            builder.addNetworkInterceptor(networkInterceptor);
        }

        OkHttpClient client = builder.build();
        //OkGo初始化
        OkGo.getInstance().init(context)
                .setOkHttpClient(client)
                .setCacheMode(cacheMode)
                .setCacheTime(cacheTime)
                .setRetryCount(DEFAULT_RETRY)
                .addCommonHeaders(commonHeaders)
                .addCommonParams(commonParams);
    }


    public static final class Builder {

        private int timeout;
        private CacheMode mode;
        private long cacheTime;
        private HttpHeaders commonHeaders;
        private HttpParams commonParams;
        private Interceptor interceptor;
        private Interceptor networkInterceptor;
        private CookieJar cookieJar;
        private int retry;

        public Builder() {
            timeout = DEFAULT_TIMEOUT;
            this.mode = CacheMode.NO_CACHE;
            this.cacheTime = CacheEntity.CACHE_NEVER_EXPIRE;
            this.retry = DEFAULT_RETRY;
            this.cookieJar = new CookieJarImpl(new MemoryCookieStore());
            this.commonHeaders = new HttpHeaders();
            this.commonParams = new HttpParams();
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Builder mode(CacheMode mode) {
            this.mode = mode;
            return this;
        }

        public Builder cacheTime(long cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public Builder netInterceptor(Interceptor interceptor) {
            this.networkInterceptor = interceptor;
            return this;
        }

        public Builder addCommonHeader(String key, String value) {
            if (key == null || key.isEmpty()) {
                return this;
            }
            if (commonHeaders == null) {
                commonHeaders = new HttpHeaders();
            }
            commonHeaders.put(key, value);
            return this;
        }

        public Builder addCommonParam(String key, String value) {
            if (key == null || key.isEmpty()) {
                return this;
            }
            if (commonParams == null) {
                commonParams = new HttpParams();
            }
            commonParams.put(key, value);
            return this;
        }

        public Builder cookieJar(CookieJarImpl cookieJar) {
            this.cookieJar = cookieJar;
            return this;
        }

        public Builder retry(int retry) {
            if (retry < 0) retry = 0;
            this.retry = retry;
            return this;
        }


        public NetConfig build() {
            if (networkInterceptor == null) {
                networkInterceptor = new StethoInterceptor();
            }
            return new NetConfig(this);
        }


    }

}
