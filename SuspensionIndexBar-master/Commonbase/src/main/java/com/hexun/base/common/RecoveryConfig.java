package com.hexun.base.common;

import android.app.Activity;
import android.app.Application;

import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

/**
 * Created by hexun on 2017/10/11.
 */

public class RecoveryConfig {

    private boolean debugEnable;                        //是否开启debug模式
    private boolean recoverInBackground;                 //后台状态下出现崩溃,是否回复
    private boolean recoverStack;                       //是否回复整个栈
    private Class<? extends Activity> rootPage;         //在仅回复栈顶的情况下
    private RecoveryCallback callback;                  //
    private boolean silentEnable;
    private Recovery.SilentMode silentMode;

    private RecoveryConfig(Builder builder) {
        debugEnable = builder.debugEnable;
        recoverInBackground = builder.recoverInBackground;
        recoverStack = builder.recoverStack;
        rootPage = builder.rootPage;
        callback = builder.callback;
        silentEnable = builder.silentEnable;
        silentMode = builder.silentMode;
    }


    void prepareRecovery(Application context) {
        Recovery.getInstance()
                .debug(debugEnable)
                .recoverInBackground(recoverInBackground)
                .recoverStack(recoverStack)
                .mainPage(rootPage)
                .callback(callback)
                .recoverEnabled(true)
                .silent(silentEnable, silentMode)
                .init(context);
    }


    public static final class Builder {

        private boolean debugEnable;
        private boolean recoverInBackground;
        private boolean recoverStack;
        private Class<? extends Activity> rootPage;
        private RecoveryCallback callback;
        private boolean silentEnable;
        private Recovery.SilentMode silentMode;


        public Builder() {
            debugEnable = true;
            recoverInBackground = false;
            recoverStack = true;
            silentEnable = false;
            silentMode = Recovery.SilentMode.RECOVER_ACTIVITY_STACK;
        }

        public Builder debugEnable(boolean debugEnable) {
            this.debugEnable = debugEnable;
            return this;
        }

        public Builder recoverInBackgroud(boolean recoverInBackground) {
            this.recoverInBackground = recoverInBackground;
            return this;
        }

        public Builder recoverStack(boolean recoverStack) {
            this.recoverStack = recoverStack;
            return this;
        }


        public Builder rootPage(Class<? extends Activity> page) {
            this.rootPage = page;
            return this;
        }


        public Builder callback(RecoveryCallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder silentEnable(boolean silentEnable) {
            this.silentEnable = silentEnable;
            return this;
        }

        public Builder silentMode(Recovery.SilentMode silentMode) {
            this.silentMode = silentMode;
            return this;
        }

        public RecoveryConfig build() {
            return new RecoveryConfig(this);
        }


    }

}
