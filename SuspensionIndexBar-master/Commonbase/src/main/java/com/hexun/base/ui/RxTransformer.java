package com.hexun.base.ui;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.BehaviorSubject;

/**
 * 作者：    shaoshuai
 * 时间：    2017/10/23 10:29
 * 电子邮箱：
 * 描述:
 */

public class RxTransformer {
    /**
     * 获取变换后的Observable
     *
     * @param subject
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> getTransform(final BehaviorSubject<Integer> subject) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.takeUntil(subject.filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        if (integer.equals(ActivityLife.ONDESTROY)) {
                            //destroy时停止发送.
                            return true;
                        }
                        return false;
                    }
                }));
            }
        };
    }
}
