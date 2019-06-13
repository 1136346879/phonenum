package com.hexun.base.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.router.RouterSheet;

import java.util.List;

/**
 * @author yangyi 2017年10月19日10:42:21
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NEWS_INDEX = 0;
    private static final int DISCOVERY_INDEX = 1;
    private static final int MARKET_INDEX = 2;
    private static final int SELF_SELECTION_INDEX = 3;
    private static final int MY_INDEX = 4;
    private static final String NAME = "name";

    private List<String> bottomTabNameList;
    private Context context;

    public MainPagerAdapter(Context context, FragmentManager fm, List<String> bottomTabNameList) {
        super(fm);
        this.bottomTabNameList = bottomTabNameList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == NEWS_INDEX) {
            return (Fragment) ARouter.getInstance().build(RouterSheet.NEWS)
                    .withString(NAME, getPageTitle(position).toString())
                    .navigation();
        } else if (position == DISCOVERY_INDEX) {
            return (Fragment) ARouter.getInstance().build(RouterSheet.DISCOVERY)
                    .withString(NAME, getPageTitle(position).toString())
                    .navigation();
        } else if (position == MARKET_INDEX) {
            return (Fragment) ARouter.getInstance().build(RouterSheet.MARKET)
                    .withString(NAME, getPageTitle(position).toString())
                    .navigation();
        } else if (position == SELF_SELECTION_INDEX) {
            return (Fragment) ARouter.getInstance().build(RouterSheet.SELF_SELECTION)
                    .withString(NAME, getPageTitle(position).toString())
                    .navigation();
        } else if (position == MY_INDEX) {
            return (Fragment) ARouter.getInstance().build(RouterSheet.MY)
                    .withString(NAME, getPageTitle(position).toString())
                    .navigation();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return bottomTabNameList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return bottomTabNameList.get(position);
    }
}
