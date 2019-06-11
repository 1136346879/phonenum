package com.hexun.base.ui.adapter;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/18 15:08
 * 电子邮箱：
 * 描述:
 */

public class TitleConfig {
    @ColorInt
    int textColor;
    float textSize;
    @ColorInt
    int backGroundColor;
    @DrawableRes
    int backGround;
    int backBackGround;
    int menuBackGround;
    int contentBackGround;

    public TitleConfig textColor(@ColorInt int textColor) {
        this.textColor = textColor;
        return this;
    }

    public TitleConfig backGroundColor(@ColorInt int backGroundColor) {
        this.backGroundColor = backGroundColor;
        return this;
    }

    public TitleConfig backGround(@DrawableRes int backGround) {
        this.backGround = backGround;
        return this;
    }

    public TitleConfig textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public TitleConfig backBackGround(@DrawableRes int id) {
        backBackGround = id;
        return this;
    }

    public TitleConfig menuBackGround(@DrawableRes int id) {
        menuBackGround = id;
        return this;
    }

    public TitleConfig contentBackGround(@DrawableRes int id) {
        contentBackGround = id;
        return this;
    }
}