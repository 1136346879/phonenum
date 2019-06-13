package com.hexun.base.ui;

import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

import io.reactivex.disposables.Disposable;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/17 11:17
 * 电子邮箱：
 * 描述:
 */

public interface IBase {
    /**
     * 初始化view
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化监听
     */
    void initListener();

    /**
     * 获取Content的View id
     *
     * @return int{@LayoutRes}
     */
    @LayoutRes
    int getContentViewResources();

    /**
     * 无需强转的findView查找方法
     *
     * @param resourceId 资源id
     * @param <E>        返回类型
     * @return view
     * @throws Resources.NotFoundException 异常时抛出资源未找到
     */
    <E extends View> E findView(@IdRes int resourceId) throws Resources.NotFoundException;

    /**
     * 添加RxJava disposable 以方便回收
     *
     * @param disposable
     */
    void addDisposables(Disposable disposable);

    /**
     * 显示加载页面
     */
    void showLoadingError();

    /**
     * 移除加载异常
     */
    void removeLoadingError();
}
