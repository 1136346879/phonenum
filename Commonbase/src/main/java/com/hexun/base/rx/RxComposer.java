package com.hexun.base.rx;

import com.hexun.base.R;
import com.hexun.base.common.ResourceHelper;
import com.hexun.base.data.ApiResult;
import com.hexun.base.exception.BusinessException;
import com.hexun.base.exception.ErrorHandler;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 * @author zhangpeiyuan
 * @date 2017/10/18
 */

public class RxComposer {


    /**
     * 切换操作符
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> ioToMain() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 分离业务异常,保持onNext纯净
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> handleError() {

        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream.flatMap(new Function<ApiResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ApiResult<T> apiResult) throws Exception {
                        if (apiResult.isSuccess()) {
                            return Observable.just(apiResult.data);
                        }else if (apiResult.isSuccess() && apiResult.data == null) {
                            return Observable.error(new BusinessException(ErrorHandler.NULL_DATA, ResourceHelper.getString(R.string.null_data_error)));
                        }else {
                            return Observable.error(new BusinessException(apiResult.getstatus(),
                                    apiResult.getErrorMessage()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 切换线程,并分离业务异常
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<ApiResult<T>, T> dealThreadError() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ApiResult<T>> upstream) {
                return upstream.flatMap(new Function<ApiResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull ApiResult<T> apiResult) throws Exception {
                        if (apiResult.isSuccess() && apiResult.data != null) {
                            return Observable.just(apiResult.data);
                        } else if (apiResult.isSuccess() && apiResult.data == null) {
                            return Observable.error(new BusinessException(ErrorHandler.NULL_DATA, ResourceHelper.getString(R.string.null_data_error)));
                        } else {
                            return Observable.error(new BusinessException(apiResult.getstatus()
                                    ,apiResult.getErrorMessage()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
