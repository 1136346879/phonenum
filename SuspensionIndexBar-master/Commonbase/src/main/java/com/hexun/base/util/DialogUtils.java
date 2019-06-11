package com.hexun.base.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hexun.base.R;

import android.graphics.Color;

/**
 * @author 作者：    shaoshuai
 *         时间：    2017/10/19 09:49
 *         电子邮箱：
 *         描述:
 */

public class DialogUtils {
    private Dialog dialog;
    private DialogConfig config;
    private static DialogUtils dialogUtils;
    private DialogListener listener;

    public static class DialogConfig {
        /**
         * 标题
         */
        private String title;
        /**
         * 内容
         */
        private String content;
        private String positive;
        private String negative;
        private Context context;
        /**
         * 圆角支持，默认支持
         */
        private boolean round = true;
        /**
         * 设置整体字体颜色默认黑色
         */
        private int textColor;
        /**
         * 设置整体字体大小默认系统字体
         */
        private float textSize;
        /**
         * 设置title字体颜色，默认textColor
         */
        private int titleColor;
        /**
         * 设置标题字体大小，默认textSize
         */
        private float titleSize;

        /**
         * 设置背景
         */
        private int backGround;
        /**
         * 设置背景颜色
         */
        private int backGroundColor;
        /**
         * 设置button字体大小默认textSize
         */
        private float buttonSize;
        /**
         * 设置左边按钮字体颜色
         */
        private int positiveColor;
        /**
         * 设置右边按钮字体颜色
         */
        private int negativeColor;
        /**
         * 分割线颜色
         */
        private int lineColor;
        /**
         * 设置夜间模式的config
         */
        private DialogConfig nightConfig;

        public DialogConfig title(String title) {
            this.title = title;
            return this;
        }

        public DialogConfig content(String content) {
            this.content = content;
            return this;
        }

        public DialogConfig positive(String positive) {
            this.positive = positive;
            return this;
        }

        public DialogConfig negative(String negative) {
            this.negative = negative;
            return this;
        }

        public DialogConfig round(boolean round) {
            this.round = round;
            return this;
        }

        public DialogConfig context(Context context) {
            this.context = context;
            return this;
        }

        public DialogConfig textColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        public DialogConfig backGround(@DrawableRes int backGround) {
            this.backGround = backGround;
            return this;
        }

        public DialogConfig backGroundColor(@ColorInt int backGroundColor) {
            this.backGroundColor = backGroundColor;
            return this;
        }

        public DialogConfig positiveColor(@ColorInt int positiveColor) {
            this.positiveColor = positiveColor;
            return this;
        }

        public DialogConfig negativeColor(@ColorInt int negativeColor) {
            this.negativeColor = negativeColor;
            return this;
        }

        public DialogConfig titleColor(@ColorInt int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public DialogConfig textSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public DialogConfig titleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public DialogConfig buttonSize(float buttonSize) {
            this.buttonSize = buttonSize;
            return this;
        }

        public DialogConfig lineColor(@ColorInt int lineColor) {
            this.lineColor = lineColor;
            return this;
        }

        public DialogConfig nightConfig(DialogConfig config) {
            this.nightConfig = config;
            return this;
        }

        public DialogUtils build() {
            if (0 == buttonSize) {
                buttonSize = 15;
            }
            if (0 == textSize) {
                textSize = 13;
            }
            if (0 == titleSize) {
                titleSize = 15;
            }
            if (0 == lineColor) {
                lineColor(Color.argb(0xFF, 0xD2, 0xD2, 0xDA));
            }
            if (0 == backGroundColor) {
                backGroundColor(Color.argb(0xFF, 0xE8, 0xE8, 0xE8));
            }
            if (0 == positiveColor) {
                positiveColor(Color.argb(0, 0, 0, 0));
            }
            if (0 == negativeColor) {
                negativeColor(Color.argb(0, 0, 0, 0));
            }
            if (0 == textColor) {
                textColor(Color.BLACK);
            }
            get().buildDialog(this);
            return DialogUtils.get();
        }

        /**
         * 回收数据
         */
        private void recycle() {
            title = null;
            content = null;
            positive = null;
            negative = null;
            context = null;
            round = false;
            if (nightConfig != null) {
                nightConfig.recycle();
                nightConfig = null;
            }
        }
    }

    private DialogUtils() {
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static DialogConfig get(Context context) {
        dialogUtils = get();
        dialogUtils.getConfig().context(context);
        return dialogUtils.getConfig();
    }

    private static DialogUtils get() {
        if (null == dialogUtils) {
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }


    public interface DialogListener {
        /**
         * 左侧按钮点击（一般确定）
         *
         * @param dialog
         */
        void onDialogPositiveClick(Dialog dialog);

        /**
         * 右侧按钮点击（一般取消）
         *
         * @param dialog
         */
        void onDialogNegativeClick(Dialog dialog);
    }

    public DialogConfig getConfig() {
        if (null == config) {
            config = new DialogConfig();
        }
        return config;
    }

    public void showDialog(DialogListener listener) {
        if (null != listener) {
            this.listener = listener;
        }
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        } else {
            throw new RuntimeException("是否调用getConfig().build()初始化dialog？或者当前dialog正在显示？");
        }
    }

    public void showDialog() {
        showDialog(null);
    }

    public View createSampleView(DialogConfig config) {
        //初始化View
        RelativeLayout layout = new RelativeLayout(config.context);
        if (0 != config.backGround) {
            layout.setBackgroundResource(config.backGround);
        } else {
            if (config.round) {
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(dp2px(config, 10));
                if (0 != config.backGroundColor) {
                    drawable.setColor(config.backGroundColor);
                }
                layout.setBackground(drawable);
            } else {
                if (0 != config.backGroundColor) {
                    layout.setBackgroundColor(config.backGroundColor);
                }
            }
        }
        View title = createTitle(config);
        if (null != title) {
            title.setId(R.id.id_dialog_title);
            layout.addView(title);
            title.setPadding(dp2px(config, 15), 0, dp2px(config, 15), 0);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) title.getLayoutParams();
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            params.topMargin = dp2px(config, 10);
//            params.bottomMargin = dp2px(config, 10);
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            title.setLayoutParams(params);
        }
        View contentView = createContentView(config);
        contentView.setId(R.id.id_dialog_content);
        layout.addView(contentView);
        contentView.setPadding(dp2px(config, 15), 0, dp2px(config, 15), 0);
        RelativeLayout.LayoutParams contentParams = (RelativeLayout.LayoutParams) contentView.getLayoutParams();
        if (null != title) {
            contentParams.addRule(RelativeLayout.BELOW, R.id.id_dialog_title);
        }
        contentParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        contentParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        contentParams.bottomMargin = dp2px(config, 20);
        contentParams.topMargin = dp2px(config, 20);
        contentView.setLayoutParams(contentParams);
        View buttonLayout = createButtonLayoutView(config);
        if (null != buttonLayout) {
            View line = new View(config.context);
            line.setId(R.id.id_dialog_line);
            if (0 != config.lineColor) {
                line.setBackgroundColor(config.lineColor);
            }
            layout.addView(line);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) line.getLayoutParams();
            params.height = dp2px(config, 0.6f);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.addRule(RelativeLayout.BELOW, R.id.id_dialog_content);
            line.setLayoutParams(params);
            buttonLayout.setId(R.id.id_dialog_button);
            layout.addView(buttonLayout);
            RelativeLayout.LayoutParams buttonParams = (RelativeLayout.LayoutParams) buttonLayout.getLayoutParams();
            buttonParams.addRule(RelativeLayout.BELOW, R.id.id_dialog_line);
            buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            buttonParams.height = dp2px(config, 42.5f);
            buttonParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            buttonLayout.setLayoutParams(buttonParams);
        }
        return layout;
    }

    /**
     * 创建标题View
     *
     * @param config
     * @return
     */
    private View createTitle(DialogConfig config) {
        TextView titleView = null;
        if (null != config.title) {
            titleView = new TextView(config.context);
            titleView.setText(config.title);
            if (0 != config.titleColor) {
                titleView.setTextColor(config.titleColor);
            } else if (0 != config.textColor) {
                titleView.setTextColor(config.textColor);
            }
            if (0 != config.titleSize) {
                titleView.setTextSize(config.titleSize);
            } else if (0 != config.textSize) {
                titleView.setTextSize(config.textSize);
            }
        }
        return titleView;
    }

    /**
     * 创建ContentView
     *
     * @param config
     * @return
     */
    private View createContentView(DialogConfig config) {
        TextView contentView = new TextView(config.context);
        contentView.setGravity(Gravity.CENTER);
        if (0 != config.textColor) {
            contentView.setTextColor(config.textColor);
        }
        if (0 != config.textSize) {
            contentView.setTextSize(config.textSize);
        }
        contentView.setText(config.content);
        return contentView;
    }

    /**
     * 创建底部button布局
     *
     * @param config
     * @return
     */
    private View createButtonLayoutView(DialogConfig config) {
        if (null == config.positive && null == config.negative) {
            return null;
        }
        LinearLayout layout = new LinearLayout(config.context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        TextView positive = null;
        if (null != config.positive) {
            //左侧按钮文案
            positive = new TextView(config.context);
            positive.setText(config.positive);
            positive.setGravity(Gravity.CENTER);
            positive.setPadding(dp2px(config, 10), dp2px(config, 10), dp2px(config, 10), dp2px(config, 10));
            if (0 != config.positiveColor) {
                positive.setTextColor(config.positiveColor);
            } else if (0 != config.textColor) {
                positive.setTextColor(config.textColor);
            }
            if (0 != config.buttonSize) {
                positive.setTextSize(config.buttonSize);
            } else if (0 != config.textSize) {
                positive.setTextSize(config.textSize);
            }
            if (0 != config.positiveColor) {
                positive.setBackgroundColor(config.positiveColor);
            }
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != listener) {
                        listener.onDialogPositiveClick(dialog);
                    }
                }
            });
        }
        if (null != positive) {
            layout.addView(positive);
        }
        TextView negative = null;
        if (null != config.negative) {
            negative = new TextView(config.context);
            negative.setText(config.negative);
            negative.setGravity(Gravity.CENTER);
            negative.setPadding(dp2px(config, 10), dp2px(config, 10), dp2px(config, 10), dp2px(config, 10));
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (null != listener) {
                        listener.onDialogNegativeClick(dialog);
                    }
                }
            });
            if (0 != config.negativeColor) {
                negative.setBackgroundColor(Color.BLUE);
            }
            if (0 != config.negativeColor) {
                negative.setTextColor(config.negativeColor);
            } else if (0 != config.textColor) {
                negative.setTextColor(config.textColor);
            }
            if (0 != config.buttonSize) {
                negative.setTextSize(config.buttonSize);
            } else if (0 != config.textSize) {
                negative.setTextSize(config.textSize);
            }
        }
        if (null != negative) {
            layout.addView(negative);
        }
        buttonLayout(layout, config.lineColor);
        return layout;
    }

    /**
     * 设置按钮的params以及添加分割线
     *
     * @param layout
     */
    private void buttonLayout(LinearLayout layout, @ColorInt int lineColor) {
        //两个button都存在时添加分割线，只有一个button时不需要添加分割线
        int count = layout.getChildCount();
        if (count == 0) {
            return;
        }
        if (count == 1) {
            View view = layout.getChildAt(0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.weight = 1;
            params.height = dp2px(config, 42.5f);
            view.setLayoutParams(params);
            return;
        }
        if (count == 2) {
            //说明包含两个按钮，添加分割线并设置weight
            View first = layout.getChildAt(0);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) first.getLayoutParams();
            params.width = 0;
            params.weight = 1;
            params.height = dp2px(config, 42.5f);
            first.setLayoutParams(params);
            View second = layout.getChildAt(1);
            params = (LinearLayout.LayoutParams) second.getLayoutParams();
            params.width = 0;
            params.weight = 1;
            params.height = dp2px(config, 42.5f);
            second.setLayoutParams(params);
            View line = new View(layout.getContext());
            //设置分割线的颜色
            if (0 != lineColor) {
                line.setBackgroundColor(lineColor);
            }
            layout.addView(line, 1);
            params = (LinearLayout.LayoutParams) line.getLayoutParams();
            params.width = dp2px(config, 0.6f);
            params.height = (int) (config.context.getResources().getDisplayMetrics().density * 42.5 + 0.5);
            line.setPadding(0, dp2px(config, 5), 0, dp2px(config, 5));
            line.setLayoutParams(params);
        }
    }


    /**
     * 创建dialog
     */
    private void buildDialog(DialogConfig config) {
        if (null == config.context) {
            throw new RuntimeException("必须传入context，调用get(Context)传入context");
        }
        if (null == config.content) {
            throw new RuntimeException("没有内容，请调用getConfig().content(String)传入显示内容");
        }
        dialog = new Dialog(config.context, R.style.BaseDialog);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //是否开启夜间模式
        boolean nightOpen = false;
        View content;
        if (nightOpen) {
            DialogConfig nightConfig;
            if (null != config.nightConfig) {
                nightConfig = config.nightConfig;
                if (null == nightConfig.context) {
                    nightConfig.context(config.context);
                }
                content = createSampleView(nightConfig);
                window.setContentView(content);
                limitHeight(content);
                return;
            }
        }
        content = createSampleView(config);
        window.setContentView(content);
        limitHeight(content);
    }

    private void limitHeight(View content) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) content.getLayoutParams();
        params.width = (int) (config.context.getResources().getDisplayMetrics().density * 250 + 0.5);
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        content.setLayoutParams(params);
    }

    public Dialog getDialog() {
        return dialog;
    }

    /**
     * 取消dialog
     */
    public void dismiss() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    private static int dp2px(DialogConfig config, float dp) {
        return (int) (config.context.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    private static int sp2px(DialogConfig config, float sp) {
        return (int) (config.context.getResources().getDisplayMetrics().scaledDensity * sp + 0.5);
    }
}
