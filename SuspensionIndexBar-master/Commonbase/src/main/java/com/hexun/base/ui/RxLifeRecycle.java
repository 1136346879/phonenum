package com.hexun.base.ui;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.SparseArray;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/24 14:30
 * 电子邮箱：
 * 描述:
 */

public class RxLifeRecycle implements Application.ActivityLifecycleCallbacks {
    private SparseArray<CompositeDisposable> disposables = new SparseArray<>();
    private static RxLifeRecycle recycle;
    private int hashcode = -1;

    private RxLifeRecycle() {
    }

    public static RxLifeRecycle get() {
        if (recycle == null) {
            recycle = new RxLifeRecycle();
        }
        return recycle;
    }

    /**
     * 绑定activity
     *
     * @param taskId
     */
    public void registerActivity(int taskId) {
        CompositeDisposable disposable = disposables.get(taskId);
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        disposables.put(taskId, disposable);
    }

    /**
     * 添加disposable
     *
     * @param disposable
     */
    public void addDisposables(Disposable disposable) {
        CompositeDisposable compositeDisposable = disposables.get(hashcode);
        compositeDisposable.add(disposable);
    }

    /**
     * 取消页面相关的订阅
     *
     * @param taskId
     */
    public void cancelDisposable(int taskId) {
        CompositeDisposable compositeDisposable = disposables.get(taskId);
        if (null != compositeDisposable) {
            compositeDisposable.dispose();
        }
        disposables.remove(taskId);
    }

    /**
     * 绑定页面结束时取消
     *
     * @return
     */
    public Consumer<Disposable> canCancel() {
        return new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addDisposables(disposable);
            }
        };
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        hashcode = activity.hashCode();
        registerActivity(hashcode);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        cancelDisposable(hashcode);
        hashcode = -1;
    }
}
