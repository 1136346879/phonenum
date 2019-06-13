package com.hexun.base.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.ALog;
import com.hexun.base.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author yangyi 2017年10月18日11:11:50
 */

public class MainBottomLayout extends LinearLayout {

    private static final int NEWS_INDEX = 0;
    private static final int DISCOVERY_INDEX = 1;
    private static final int MARKET_INDEX = 2;
    private static final int SELF_SELECTION_INDEX = 3;
    private static final int MY_INDEX = 4;

    private Unbinder unbinder;

    private LinearLayout[] bottomTabs;

    private TextView newsText;
    private TextView discoveryText;
    private TextView marketText;
    private TextView selfSelectionText;
    private TextView myText;

    private ImageView newsImg;
    private ImageView discoveryImg;
    private ImageView marketImg;
    private ImageView selfSelectionImg;
    private ImageView myImg;

    public interface OnTabClickListener {
        /**
         * 点击首页底部的tab时触发
         * <p>
         * tabIndex为tab对应的索引ID
         */
        void onTabClick(int tabIndex);
    }

    private OnTabClickListener onTabClickListener;

    public MainBottomLayout(Context context) {
        super(context);
        init();
    }

    public MainBottomLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainBottomLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }

    private void init() {
        initViews();
        initListeners();
    }

    public List<String> getTabNameList() {
        List<String> bottomTabList = new LinkedList<>();
        bottomTabList.add(newsText.getText().toString());
        bottomTabList.add(discoveryText.getText().toString());
        bottomTabList.add(marketText.getText().toString());
        bottomTabList.add(selfSelectionText.getText().toString());
        bottomTabList.add(myText.getText().toString());
        return bottomTabList;
    }

    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_main_bottom,
                this,
                true);
        unbinder = ButterKnife.bind(view);

        LinearLayout newsLayout = (LinearLayout) view.findViewById(R.id.newsLayout);
        LinearLayout discoveryLayout = (LinearLayout) view.findViewById(R.id.discoveryLayout);
        LinearLayout marketLayout = (LinearLayout) view.findViewById(R.id.marketLayout);
        LinearLayout selfSelectionLayout = (LinearLayout) view.findViewById(R.id.selfSelectionLayout);
        LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.myLayout);

        bottomTabs = new LinearLayout[]{newsLayout,
                discoveryLayout,
                marketLayout,
                selfSelectionLayout,
                myLayout
        };

        newsText = (TextView) view.findViewById(R.id.newsText);
        discoveryText = (TextView) view.findViewById(R.id.discoveryText);
        marketText = (TextView) view.findViewById(R.id.marketText);
        selfSelectionText = (TextView) view.findViewById(R.id.selfSelectionText);
        myText = (TextView) view.findViewById(R.id.myText);

        newsImg = (ImageView) view.findViewById(R.id.newsImg);
        discoveryImg = (ImageView) view.findViewById(R.id.discoveryImg);
        marketImg = (ImageView) view.findViewById(R.id.marketImg);
        selfSelectionImg = (ImageView) view.findViewById(R.id.selfSelectionImg);
        myImg = (ImageView) view.findViewById(R.id.myImg);
        switchTab(bottomTabs[0]);
    }

    private void initListeners() {
        ButterKnife.apply(bottomTabs, new ButterKnife.Action<LinearLayout>() {
            @Override
            public void apply(@NonNull LinearLayout linearLayout, final int i) {
                linearLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switchTab(v);
                    }
                });
            }
        });
    }

    private void switchTab(View view) {
        int i = view.getId();
        if (i == R.id.newsLayout) {
            checkSelected(newsImg, newsText);
            clearSelected(discoveryImg, discoveryText);
            clearSelected(marketImg, marketText);
            clearSelected(selfSelectionImg, selfSelectionText);
            clearSelected(myImg, myText);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(0);
            }
        } else if (i == R.id.discoveryLayout) {
            checkSelected(discoveryImg, discoveryText);
            clearSelected(newsImg, newsText);
            clearSelected(marketImg, marketText);
            clearSelected(selfSelectionImg, selfSelectionText);
            clearSelected(myImg, myText);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(1);
            }
        } else if (i == R.id.marketLayout) {
            checkSelected(marketImg, marketText);
            clearSelected(discoveryImg, discoveryText);
            clearSelected(newsImg, newsText);
            clearSelected(selfSelectionImg, selfSelectionText);
            clearSelected(myImg, myText);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(2);
            }
        } else if (i == R.id.selfSelectionLayout) {
            checkSelected(selfSelectionImg, selfSelectionText);
            clearSelected(discoveryImg, discoveryText);
            clearSelected(marketImg, marketText);
            clearSelected(newsImg, newsText);
            clearSelected(myImg, myText);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(3);
            }
        } else if (i == R.id.myLayout) {
            checkSelected(myImg, myText);
            clearSelected(discoveryImg, discoveryText);
            clearSelected(marketImg, marketText);
            clearSelected(selfSelectionImg, selfSelectionText);
            clearSelected(newsImg, newsText);
            if (onTabClickListener != null) {
                onTabClickListener.onTabClick(4);
            }
        } else {
            ALog.e("");
        }
    }

    private void checkSelected(ImageView imageView, TextView textView) {
        if (imageView.isSelected()) {
            clearSelected(imageView, textView);
        } else {
            imageView.setSelected(true);
            textView.setTextColor(Color.parseColor("#f85959"));
        }
    }

    private void clearSelected(ImageView imageView, TextView textView) {
        imageView.setSelected(false);
        textView.setTextColor(Color.parseColor("#494949"));
    }
}
