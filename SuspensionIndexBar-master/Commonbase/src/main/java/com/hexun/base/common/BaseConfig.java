package com.hexun.base.common;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hexun.base.BuildConfig;
import com.hexun.base.exception.ErrorHandler;
import com.hexun.base.util.Utils;

import skin.support.SkinCompatManager;
import skin.support.design.app.SkinMaterialViewInflater;

/**
 * Created by hexun on 2017/10/11.
 */

public class BaseConfig {

    final NetConfig netConfig;
    final RecoveryConfig recoveryConfig;
    final DebugConfig debugConfig;
    final ThreadPoolConfig threadPoolConfig;

    private BaseConfig(Builder builder) {
        this.netConfig = builder.netConfig;
        this.recoveryConfig = builder.recoveryConfig;
        this.debugConfig = builder.debugConfig;
        this.threadPoolConfig = builder.threadPoolConfig;
    }


    public NetConfig getNetConfig() {
        return netConfig;
    }

    public RecoveryConfig getRecoveryConfig() {
        return recoveryConfig;
    }

    public DebugConfig getDebugConfig() {
        return debugConfig;
    }

    /**
     * 根据配置初始化
     */
    void initProject(Application context) {
        Utils.init(context);
        LogConfig.initLog(context);
        ResourceHelper.init(context);
        ErrorHandler.init(context);

        //Router
        if (BuildConfig.DEBUG) {                                // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();                                  // 打印日志
            ARouter.openDebug();                                // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.printStackTrace();                          // 打印日志的时候打印线程堆栈
        }
        ARouter.init(context);                                  // 尽可能早，推荐在Application中初始化

        netConfig.prepareNetwork(context);                      // 初始化网络部分

        if (recoveryConfig != null) {                            // 仅在主动配置recoveryConfig时初始化,否则不开启功能
            recoveryConfig.prepareRecovery(context);
        }

        if (threadPoolConfig != null) {
            threadPoolConfig.prepareTaskQueue();
        }

        if (debugConfig != null) {
            debugConfig.prepareDebugTool(context);
        }
        SpManager.init(context);

        SkinCompatManager.withoutActivity(context)                      // 基础控件换肤初始化
                .addInflater(new SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
                .setSkinStatusBarColorEnable(true)                      // 关闭状态栏换肤，默认打开[可选]
                .setSkinWindowBackgroundEnable(true)                    // 关闭windowBackground换肤，默认打开[可选]
                .loadSkin();
    }


    /**
     * 切换日夜间模式
     */
    public void switchDayNight(){
        String skinName = SkinCompatManager.getInstance().getCurSkinName();
        if ("".equals(skinName)){
            SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
        }else {
            SkinCompatManager.getInstance().restoreDefaultTheme();
        }
    }

    /**
     * 判断当前主题是否是夜间主题
     * @return
     */
    public boolean isNightModeActive(){
        String skinName = SkinCompatManager.getInstance().getCurSkinName();
        if ("".equals(skinName)){
            return false;
        }else {
            return true;
        }
    }


    public static final class Builder {
        private NetConfig netConfig;
        private RecoveryConfig recoveryConfig;
        private DebugConfig debugConfig;
        private ThreadPoolConfig threadPoolConfig;

        public Builder() {

        }

        /**
         * @param netConfig 网络配置
         * @return
         */
        public Builder netConfig(NetConfig netConfig) {
            this.netConfig = netConfig;
            return this;
        }

        /**
         * @param recoveryConfig 异常恢复配置
         * @return
         */
        public Builder recoveryConfig(RecoveryConfig recoveryConfig) {
            this.recoveryConfig = recoveryConfig;
            return this;
        }

        /**
         * @param debugConfig 调试配置
         * @return
         */
        public Builder debugConfig(DebugConfig debugConfig) {
            if (BuildConfig.DEBUG) {
                this.debugConfig = debugConfig;
            }
            return this;
        }

        /**
         * @param threadPoolConfig 线程池配置
         * @return
         */
        public Builder threadPoolConfig(ThreadPoolConfig threadPoolConfig) {
            this.threadPoolConfig = threadPoolConfig;
            return this;
        }


        public BaseConfig build() {
            if (netConfig == null) {
                netConfig = new NetConfig.Builder().build();
            }
            if (debugConfig == null) {
                debugConfig = new DebugConfig.Builder().build();
            }
            if (threadPoolConfig == null){
                threadPoolConfig = new ThreadPoolConfig.Builder().build();
            }
            return new BaseConfig(this);
        }

    }

}
