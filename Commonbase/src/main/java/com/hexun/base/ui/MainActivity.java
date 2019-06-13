package com.hexun.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hexun.base.R;
import com.hexun.base.ui.adapter.MainPagerAdapter;
import com.hexun.base.view.MainBottomLayout;
import com.hexun.base.view.NoScrollingViewPager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hexun.base.router.RouterSheet.MAIN;

/**
 * @author yangyi 2017年10月18日16:01:20
 */
@Route(path = MAIN)
public class MainActivity extends AppCompatActivity {

    MainBottomLayout mainBottomLayout;
    NoScrollingViewPager noScrollingViewPager;

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_base);
        mainBottomLayout = (MainBottomLayout) findViewById(R.id.mainBottomLayout);
        noScrollingViewPager = (NoScrollingViewPager) findViewById(R.id.noScrollingViewPager);
        unbinder = ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void init() {
        List<String> tabNameList = mainBottomLayout.getTabNameList();
        noScrollingViewPager.setAdapter(new MainPagerAdapter(getBaseContext(),
                getSupportFragmentManager(),
                tabNameList));
        noScrollingViewPager.setOffscreenPageLimit(tabNameList.size());
        noScrollingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                noScrollingViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mainBottomLayout.setOnTabClickListener(new MainBottomLayout.OnTabClickListener() {
            @Override
            public void onTabClick(int tabIndex) {
                noScrollingViewPager.setCurrentItem(tabIndex);
            }
        });
        noScrollingViewPager.setCurrentItem(0);
    }
}
