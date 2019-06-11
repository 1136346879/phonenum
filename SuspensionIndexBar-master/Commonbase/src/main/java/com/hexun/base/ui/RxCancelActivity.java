package com.hexun.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.subjects.BehaviorSubject;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/23 10:24
 * 电子邮箱：
 * 描述:
 */

public class RxCancelActivity extends AppCompatActivity {
    /**
     * 创建subject
     */
    protected BehaviorSubject<Integer> subject = BehaviorSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subject.onNext(ActivityLife.ONCREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subject.onNext(ActivityLife.ONDESTROY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        subject.onNext(ActivityLife.ONRESUME);
    }
}
