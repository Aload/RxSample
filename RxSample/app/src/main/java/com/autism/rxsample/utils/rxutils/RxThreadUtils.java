package com.autism.rxsample.utils.rxutils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author：Autism on 2017/3/2 15:59
 * Used:
 */
public class RxThreadUtils {
    /**
     * 统一线程切换方法
     *
     * @param <T> Observable
     * @return Transformer对象
     */
    public static <T> Observable.Transformer<T, T> ioMain() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
