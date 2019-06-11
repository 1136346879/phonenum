package com.hexun.base.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/18 15:11
 * 电子邮箱：
 * 描述:
 */
public class SampleDefaultAdapter extends TitleAdapter {
    private String titleText;
    private TitleConfig config;
    /**
     * 是否显示back
     */
    private boolean backShow;

    public SampleDefaultAdapter(Context context, String titleText, boolean backShow) {
        super(context);
        this.titleText = titleText;
        this.backShow = backShow;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected View getBackView() {
        if (backShow) {
            ImageView back = new ImageView(context);
            back.setBackgroundResource(android.R.mipmap.sym_def_app_icon);
            return back;
        }
        return null;
    }

    @Override
    protected View getMenuView() {
        return null;
    }

    @Override
    protected View getTitleView() {
        TextView title = new TextView(context);
        int temp = getConfig().textColor;
        float size = getConfig().textSize;
        if (0 != temp) {
            title.setTextColor(temp);
        }
        if (0 != size) {
            title.setTextSize(size);
        }
        title.setText(titleText);
        return title;
    }

    @Override
    public TitleConfig getConfig() {
        if (config == null) {
            config = new TitleConfig().backGroundColor(Color.BLUE).textColor(Color.WHITE).textSize(18.0f);
        }
        return config;
    }

    @Override
    protected boolean onBackClick(View view) {
        return false;
    }

    @Override
    protected void onMenuClick(View view) {

    }
}