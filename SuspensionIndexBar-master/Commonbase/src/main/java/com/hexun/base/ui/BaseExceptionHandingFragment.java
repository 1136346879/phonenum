package com.hexun.base.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/20 09:30
 * 电子邮箱：
 * 描述:
 */

public abstract class BaseExceptionHandingFragment extends Fragment implements IBase {
    protected View contentView;
    protected CompositeDisposable disposables;
    private boolean isResume;
    private boolean isShowing;
    private boolean isCovering;
    private Context context;
    private View errorView;

    /**
     * 该fragment是否可见，调用onHiddenChanged()和setUserVisibleHint()时改变
     */
    protected boolean visibleToUser = false;
    /**
     * fragment的onCreateView()是否调用过。调用过置为true，调用过firstLoad()后置为false
     */
    protected boolean isPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = checkContentView(contentView);
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        contentView = LayoutInflater.from(getActivity()).inflate(getContentViewResources(), null);
        isResume = false;
        isShowing = false;
        isCovering = false;
    }

    /**
     * 变换根View
     * 如果是LinearLayout则嵌套一层FrameLayout
     *
     * @param contentView
     */
    private View checkContentView(View contentView) {
        if (!(contentView instanceof ViewGroup) || contentView instanceof LinearLayout) {
            FrameLayout layout = new FrameLayout(context);
            layout.addView(contentView);
            return layout;
        }
        return contentView;
    }

    @Override
    public void addDisposables(Disposable disposable) {
        if (null == disposable) {
            disposables = new CompositeDisposable();
        }
        disposables.add(disposable);
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        checkShow();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        checkShow();
        visibleToUser = !hidden;
        if (visibleToUser && isPrepared) {
            firstLoad();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
        checkShow();
    }

    /**
     * 被遮盖
     *
     * @param isCovering
     */
    protected final void setCover(boolean isCovering) {
        this.isCovering = isCovering;
        checkShow();
    }

    private void checkShow() {
        if ((isAdded() && !isHidden() && !isCovering && isResume) != isShowing) {
            isShowing = !isShowing;
        }
    }

    /***
     * 当前fragment可见并且fragment的onCreateView()调用过之后调用
     * 在该方法调用之后isPrepared置为false，以确保该方法只在fragment
     * 初始化成功之后并且显示时调用一次
     */
    protected void firstLoad() {

    }

    protected boolean isShowing() {
        return isShowing;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visibleToUser = isVisibleToUser;
        if (visibleToUser && isPrepared) {
            firstLoad();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != disposables) {
            disposables.dispose();
        }
    }

    protected View getLoadingErrorView() {
        TextView error = new TextView(context);
        error.setText("this is error page");
        error.setBackgroundColor(Color.WHITE);
        error.setGravity(Gravity.CENTER);
        error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                removeLoadingError();
            }
        });
        return error;
    }

    @Override
    public void showLoadingError() {
        if (errorView == null) {
            errorView = getLoadingErrorView();
        }
        ((ViewGroup) contentView).addView(errorView);
        ViewGroup.LayoutParams params = errorView.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        errorView.setLayoutParams(params);
    }

    @Override
    public void removeLoadingError() {
        ((ViewGroup) contentView).removeView(errorView);
    }

    @Override
    public <E extends View> E findView(@IdRes int resourceId) throws Resources.NotFoundException {
        return (E) contentView.findViewById(resourceId);
    }
}
