package com.hexun.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.hexun.base.R;
import com.hexun.base.manager.ActivityGroupManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yangyi 2017年10月19日10:10:53
 *
 * 注意：完善中仅供参考，如果不考虑MVP的话，8成以上代码基本没用
 */

public abstract class BaseMvpActivity extends AppCompatActivity {

    Unbinder unbinder;

    /**
     * @return activity布局ID
     */
    protected int getContentViewId() {
        return R.layout.activity_base;
    }

    /**
     * @return activity中装载fragment的布局ID
     */
    protected int getFragmentContainerId() {
        return R.id.activityContainer;
    }

    /**
     * 栈顶的Fragment
     *
     * @return 获取当前的Fragment
     */
    protected abstract BaseMvpFragment getTopFragment();

    /**
     * 初始化View
     */
    protected void initActivityView() {

    }

    /**
     * 与P绑定
     */
    protected abstract void initPresenter();

    /**
     * @param baseMvpFragment 替换容器中的Fragment
     */
    public void replaceFragment(BaseMvpFragment baseMvpFragment) {
        if (baseMvpFragment == null) {
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(getFragmentContainerId(),
                        baseMvpFragment,
                        baseMvpFragment.getClass().getSimpleName())
                .addToBackStack(baseMvpFragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * @param fromFragment 从哪里来
     * @param toFragment   跳转至哪里
     *                     添加Fragment到容器中
     */
    public void addFragment(BaseMvpFragment fromFragment, BaseMvpFragment toFragment) {
        if (fromFragment != null && toFragment != null) {
            if (toFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fromFragment)
                        .show(toFragment)
                        .addToBackStack(toFragment.getClass().getSimpleName())
                        .commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .hide(fromFragment)
                        .add(getFragmentContainerId(),
                                toFragment,
                                toFragment.getClass().getSimpleName())
                        .addToBackStack(toFragment.getClass().getSimpleName())
                        .commit();
            }
        }
    }

    /**
     * 当Activity中有添加了一个以上Fragment时，返回至上一个Fragment，
     * 当Activity中只有一个Fragment时，结束此activity
     */
    public void popFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentViewId());
        ActivityGroupManager.addActivityToList(this);

        //避免重复添加相同的Fragment
        BaseMvpFragment baseMvpFragment = getTopFragment();
        if (baseMvpFragment != null) {
            replaceFragment(baseMvpFragment);
        }

        unbinder = ButterKnife.bind(this);

        initActivityView();
        initPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityGroupManager.removeActivityFromList(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode &&
                getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
