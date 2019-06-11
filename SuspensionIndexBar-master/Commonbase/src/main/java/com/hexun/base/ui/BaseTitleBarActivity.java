package com.hexun.base.ui;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hexun.base.common.CancellableTask;
import com.hexun.base.common.RxTask;
import com.hexun.base.common.Task;
import com.hexun.base.common.TaskQueue;
import com.hexun.base.ui.adapter.SampleDefaultAdapter;
import com.hexun.base.ui.adapter.TitleAdapter;
import com.hexun.base.util.DialogUtils;

import java.util.concurrent.Future;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/20 09:26
 * 电子邮箱：
 * 描述:
 */

public abstract class BaseTitleBarActivity extends AppCompatActivity implements IBase {
    protected CompositeDisposable disposables;
    protected TitleAdapter titleAdapter;
    protected View contentViewTemp;

    /**
     * 添加rxJava disposable以便onDestroy时统一取消订阅.
     *
     * @param disposable 传入disposable
     */
    @Override
    public void addDisposables(Disposable disposable) {
        if (null == disposables) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (0 != getContentViewResources()) {
            setContentView(LayoutInflater.from(this).inflate(getContentViewResources(), null));
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        contentViewTemp = view;
        this.initView();
        this.initData();
        this.initListener();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(getContentViewResources(), null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != disposables) {
            disposables.dispose();
        }
    }

    @Override
    public <E extends View> E findView(@IdRes int resourceId) throws Resources.NotFoundException {
        return (E) findViewById(resourceId);
    }

    /**
     * 返回当前ContentView
     *
     * @return ContentView
     */
    protected View getContentView() {
        return contentViewTemp;
    }

    /**
     * 显示dialog
     *
     * @param content
     */
    protected void showDialog(String content) {
        DialogUtils.get(this).content(content).build().showDialog();
    }

    protected void showDialog(String content, String positive) {
        DialogUtils.get(this).content(content).positive(positive).build().showDialog();
    }

    protected void showDialog(String content, String positive, String negative) {
        DialogUtils.get(this).content(content).positive(positive).negative(negative).build().showDialog();
    }

    protected void showDialog(String content, String positive, String negative, DialogUtils.DialogListener listener) {
        DialogUtils.get(this).content(content).positive(positive).negative(negative).build().showDialog(listener);
    }

    /**
     * 设置全屏
     */
    protected void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 添加头部view
     *
     * @param view
     */
    private void addTitleView(View view) {
        if (0 == getContentViewResources()) {
            return;
        }
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        content.addView(view, 0);
        View userRoot = content.getChildAt(1);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) userRoot.getLayoutParams();
        params.setMargins(0, titleAdapter.getBarHeight(), 0, 0);
        userRoot.setLayoutParams(params);
    }

    /**
     * 移除View
     */
    private void removeContentView() {
        if (0 == getContentViewResources()) {
            return;
        }
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        View userRoot;
        if (null != titleAdapter && content.getChildAt(0).equals(titleAdapter.getView())) {
            userRoot = content.getChildAt(1);
        } else {
            userRoot = content.getChildAt(0);
        }
        content.removeView(userRoot);
    }

    /**
     * 添加根view
     *
     * @param view
     */
    private void addRootView(View view) {
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        if (null != titleAdapter && content.getChildAt(0).equals(titleAdapter.getView())) {
            content.addView(view, 1);
        } else {
            content.addView(view, 0);
        }
    }


    /**
     * 移除title的View
     */
    protected void removeTitleView() {
        View titleView = titleAdapter.getView();
        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        if (content.indexOfChild(titleView) != -1) {
            content.removeView(titleView);
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setSampleTitle(String title) {
        setTitleAdapter(new SampleDefaultAdapter(this, title, true));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setSampleTitle(String title, boolean backShow) {
        setTitleAdapter(new SampleDefaultAdapter(this, title, backShow));
    }

    /**
     * 设置标题文字 颜色
     *
     * @param title     标题
     * @param textColor 文字颜色
     */
    protected void setSampleTitle(String title, @ColorInt int textColor, boolean backShow) {
        SampleDefaultAdapter adapter = new SampleDefaultAdapter(this, title, backShow);
        adapter.getConfig().textColor(textColor);
        setTitleAdapter(adapter);
    }

    /**
     * 设置标题 文字颜色 大小
     *
     * @param title     标题
     * @param textColor 文字颜色
     * @param size      文字大小
     */
    protected void setSampleTitle(String title, int textColor, float size, boolean backShow) {
        SampleDefaultAdapter adapter = new SampleDefaultAdapter(this, title, backShow);
        adapter.getConfig().textColor(textColor);
        adapter.getConfig().textSize(size);
        setTitleAdapter(adapter);
    }

    /**
     * 设置标题 文字颜色 大小
     *
     * @param title     标题
     * @param textColor 文字颜色
     * @param size      文字大小
     */
    protected void setSampleTitle(String title, int textColor, float size, boolean backShow, int backGroundColor) {
        SampleDefaultAdapter adapter = new SampleDefaultAdapter(this, title, backShow);
        adapter.getConfig().textColor(textColor);
        adapter.getConfig().textSize(size);
        adapter.getConfig().backGroundColor(backGroundColor);
        setTitleAdapter(adapter);
    }

    /**
     * 获取标题适配器
     *
     * @return
     * @throws NullPointerException
     */
    protected SampleDefaultAdapter getSampleDefaultTitleAdapter() throws NullPointerException {
        if (titleAdapter instanceof SampleDefaultAdapter) {
            return (SampleDefaultAdapter) titleAdapter;
        }
        return null;
    }

    /**
     * 设置自定义Title适配器
     *
     * @param adapter title适配器{@TitleAdapter}
     */
    protected void setTitleAdapter(TitleAdapter adapter) {
        titleAdapter = adapter;
        titleAdapter.startAdapter();
        View titleView = adapter.getView();
        addTitleView(titleView);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) titleView.getLayoutParams();
        params.height = adapter.getBarHeight();
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        titleView.setLayoutParams(params);
    }

    /**
     * 通知标题改变
     * 设置完页面之后需要动态改变title需调用此方法
     */
    protected void notifyTitleChange() {
        removeTitleView();
        setTitleAdapter(titleAdapter);
    }

    /**
     * 获取异常页面
     *
     * @return
     */
    protected View getLoadingErrorView() {
        TextView textView = new TextView(this);
        textView.setText("this is error page");
        textView.setBackgroundColor(Color.LTGRAY);
        textView.setGravity(Gravity.CENTER);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLoadingError();
                initData();
            }
        });
        return textView;
    }

    /**
     * 页面异常时添加
     */
    @Override
    public void showLoadingError() {
        removeContentView();
        addRootView(getLoadingErrorView());
    }

    /**
     * 移除页面异常的view
     */
    @Override
    public void removeLoadingError() {
        if (null == contentViewTemp) {
            return;
        }
        removeContentView();
        addRootView(contentViewTemp);
    }



    /**
     * 执行优先级异步任务(与UI不相关)
     *
     * @param task
     */
    protected void executeTask(Task task) {
        TaskQueue.getInstance().execute(task);
    }

    /**
     * 执行优先级异步任务(与UI不相关)
     *
     * @param cancellableTask
     */
    protected <T> Future<T> submitCancelTask(CancellableTask<T> cancellableTask) {
        return TaskQueue.getInstance().submit(cancellableTask);
    }

    /**
     * 执行串行异步任务(与UI不相关)
     *
     * @param runnable
     */
    protected void enqueueTask(Runnable runnable) {
        TaskQueue.getInstance().enqueue(runnable);
    }


    /**
     * 和页面绑定的异步任务, 方便compositeDisposable管理
     */
    protected <T> void runRxTask(final RxTask<T> rxTask) {
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                T t = rxTask.run();
                if (t != null){
                    e.onNext(t);
                }
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposables(d);
                    }

                    @Override
                    public void onNext(T t) {
                        rxTask.onNext(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        rxTask.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        rxTask.onComplete();
                    }
                });
    }
}
