package com.hexun.base.common;

/**
 * Created by hexun on 2017/10/18.
 */

public abstract class RxTask<T> {

    public abstract T run();


    public void onNext(T t) {}

    public void onError(Throwable e) {}

    public void onComplete() {}

}
