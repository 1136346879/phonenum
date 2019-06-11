package com.hexun.base.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.hexun.base.R;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/18 15:28
 * 电子邮箱：
 * 描述:
 */

public abstract class TitleAdapter {
    private int barHeight = 120;
    private View content;

    protected abstract View getContentView();

    protected abstract View getBackView();

    protected abstract View getMenuView();

    protected abstract View getTitleView();

    public abstract TitleConfig getConfig();

    public void notifyDataChange() {

    }


    protected Context context;

    public TitleAdapter(Context context) {
        this.context = context;
    }

    /**
     * 是否拦截
     *
     * @param view back
     * @return true拦截，false不拦截
     */
    protected abstract boolean onBackClick(View view);

    /**
     * menu点击事件
     *
     * @param view
     */
    protected abstract void onMenuClick(View view);

    /**
     * 设置bar的高度
     *
     * @param height
     */
    protected void setBarHeight(int height) {
        this.barHeight = height;
    }

    public int getBarHeight() {
        return barHeight;
    }

    public void startAdapter() {
        content = getContentView();
        if (null == content) {
            content = createDefaultContent();
        }
    }

    public View getView() {
        return content;
    }


    private View createDefaultContent() {
        RelativeLayout content = new RelativeLayout(context);
        int background = getConfig().backGround;
        if (0 != background) {
            content.setBackgroundResource(background);
        } else {
            int temp = getConfig().backGroundColor;
            if (0 != temp) {
                content.setBackgroundColor(getConfig().backGroundColor);
            }
        }
        View back = getBackView();
        RelativeLayout.LayoutParams params;
        if (null != back) {
            back.setId(R.id.id_title_back);
            content.addView(back);
            params = (RelativeLayout.LayoutParams) back.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.height = barHeight;
            back.setLayoutParams(params);
            int temp = getConfig().backBackGround;
            if (0 != temp) {
                back.setBackgroundResource(temp);
            }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean dispatch = onBackClick(v);
                    if (!dispatch) {
                        ((Activity) context).finish();
                    }
                }
            });
        }
        View menu = getMenuView();
        if (null != menu) {
            menu.setId(R.id.id_title_menu);
            content.addView(menu);
            params = (RelativeLayout.LayoutParams) menu.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.height = barHeight;
            menu.setLayoutParams(params);
            int temp = getConfig().menuBackGround;
            if (0 != temp) {
                menu.setBackgroundResource(temp);
            }
            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMenuClick(v);
                }
            });
        }
        View title = getTitleView();
        if (null != title) {
            content.addView(title);
            params = (RelativeLayout.LayoutParams) title.getLayoutParams();
            if (null != back && null != menu) {
                params.addRule(RelativeLayout.RIGHT_OF, back.getId());
                params.addRule(RelativeLayout.LEFT_OF, menu.getId());
                params.addRule(RelativeLayout.CENTER_VERTICAL);
            } else {
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
            }
            title.setLayoutParams(params);
            int temp = getConfig().contentBackGround;
            if (0 != temp) {
                title.setBackgroundResource(temp);
            }
        }
        return content;
    }
}