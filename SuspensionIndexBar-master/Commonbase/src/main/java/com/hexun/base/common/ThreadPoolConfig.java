package com.hexun.base.common;

/**
 * Created by hexun on 2017/10/12.
 */

public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 5;   //默认核心线程数
    private static final int MAX_POOL_SIZE = 10;    //默认最大线程数
    private static final int KEEP_ALIVE_TIME = 60;  //默认存活时间

    private int corePoolSize;   //核心线程数
    private int maxPoolSize;    //最大线程数
    private int keepAliveTime;  //存活时间
    private boolean enableOrderExecutor;            //是否创建一个串行执行队列
    private boolean enableCancelExecutor;            //是否创建一个支持取消任务的队列

    private ThreadPoolConfig(Builder builder){
        corePoolSize = builder.corePoolSize;
        maxPoolSize = builder.maxPoolSize;
        keepAliveTime = builder.keepAliveTime;
        enableOrderExecutor = builder.enableOrderExecutor;
        enableCancelExecutor = builder.enableCancelExecutor;
    }

    void prepareTaskQueue(){
        TaskQueue.init(corePoolSize,maxPoolSize,keepAliveTime,enableOrderExecutor,enableCancelExecutor);
    }


    public static final class Builder{
        private int corePoolSize;   //核心线程数
        private int maxPoolSize;    //最大线程数
        private int keepAliveTime;  //存活时间
        private boolean enableOrderExecutor;
        private boolean enableCancelExecutor;


        public Builder(){
            corePoolSize = CORE_POOL_SIZE;
            maxPoolSize = MAX_POOL_SIZE;
            keepAliveTime = KEEP_ALIVE_TIME;
            enableOrderExecutor = true;
            enableCancelExecutor = false;
        }

        public Builder corePoolSize(int corePoolSize){
            this.corePoolSize = corePoolSize;
            return this;
        }

        public Builder maxPoolSize(int maxPoolSize){
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder keepAliveTime(int keepAliveTime){
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public Builder enableOrderExecutor(boolean enableOrderExecutor){
            this.enableOrderExecutor = enableOrderExecutor;
            return this;
        }

        public Builder enableCancelExecutor(boolean enableCancelExecutor){
            this.enableCancelExecutor = enableCancelExecutor;
            return this;
        }

        public ThreadPoolConfig build(){
            return new ThreadPoolConfig(this);
        }
    }
}
