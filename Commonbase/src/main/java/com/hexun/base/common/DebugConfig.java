package com.hexun.base.common;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.wanjian.sak.SAK;

/**
 * Created by hexun on 2017/10/12.
 */

public class DebugConfig {

    private boolean stethoOn;
    private boolean leakCananryOn;
    private boolean blockCananryOn;
    private boolean swissArmyOn;


    private DebugConfig(Builder builder){
        stethoOn = builder.stethoOn;
        leakCananryOn = builder.leakCananryOn;
        blockCananryOn = builder.blockCananryOn;
        swissArmyOn = builder.swissArmyOn;
    }


    void prepareDebugTool(Application context){
        if (stethoOn){
            Stetho.initializeWithDefaults(context);
        }

//        if (leakCananryOn){
//            if (LeakCanary.isInAnalyzerProcess(context)) {
//                return;
//            }
//            LeakCanary.install(context);
//        }
//
//        if (blockCananryOn){
//            BlockCanary.install(context, new AppBlockCanaryContext()).start();
//        }

        if (swissArmyOn){
            SAK.init(context);
        }
    }


    public static final class Builder{
        private boolean stethoOn;
        private boolean leakCananryOn;
        private boolean blockCananryOn;
        private boolean swissArmyOn;


        public Builder(){
            stethoOn = true;
            leakCananryOn = true;
            blockCananryOn = true;
            swissArmyOn = true;
        }

        public Builder stethoOn(boolean stethoOn){
            this.stethoOn = stethoOn;
            return this;
        }

        public Builder leakCananryOn(boolean leakCananryOn){
            this.leakCananryOn = leakCananryOn;
            return this;
        }

        public Builder blockCananryOn(boolean blockCananryOn){
            this.blockCananryOn = blockCananryOn;
            return this;
        }

        public Builder swissArmyOn(boolean swissArmyOn){
            this.swissArmyOn = swissArmyOn;
            return this;
        }

        public DebugConfig build(){
            return new DebugConfig(this);
        }

    }

}
