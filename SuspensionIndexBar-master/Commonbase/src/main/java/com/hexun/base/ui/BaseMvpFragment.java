package com.hexun.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yangyi 2017年10月19日10:09:29
 *
 * 注意：完善中仅供参考，如果不考虑MVP的话，8成以上代码基本没用
 */

public abstract class BaseMvpFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    private BaseMvpActivity baseActivity;
    private Unbinder unbinder;

    public BaseMvpActivity getBaseActivity() {
        return baseActivity;
    }

    /**
     * 初始化Fragment的数据
     */
    public abstract void initFragmentData();

    /**
     * 初始化Fragment的布局
     */
    public abstract void initFragmentView();

    /**
     * fragment对应的视图ID  相当于原生activity中的setContentView
     *
     * @return fragment的布局视图
     */
    public abstract int getFragmentViewId();

    /**
     * 替换Fragment
     */
    protected void replaceFragment(BaseMvpFragment fragment) {
        if (fragment == null) {
            return;
        }
        getBaseActivity().replaceFragment(fragment);
    }

    /**
     * 添加Fragment
     */
    protected void addFragment(BaseMvpFragment fromFragment, BaseMvpFragment toFragment) {
        if (fromFragment != null && toFragment != null) {
            if (!toFragment.isAdded()) {
                getBaseActivity().addFragment(fromFragment, toFragment);
            }
        }
    }

    /**
     * 移除Fragment
     */
    public void popFragment() {
        getBaseActivity().popFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof BaseMvpActivity) {
            baseActivity = (BaseMvpActivity) getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if (isHidden) {
                fragmentTransaction.hide(BaseMvpFragment.this);
            } else {
                fragmentTransaction.show(BaseMvpFragment.this);
            }
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(getFragmentViewId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFragmentData();
        initFragmentView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}

