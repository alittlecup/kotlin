package com.example.hbl.library.mvp

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by hbl on 2017/5/25.
 */
fun <T> Observable<T>.bindLifecycle(V: IView): Observable<T> {
    return compose(CheckUIDestroiedTransformer<T>(V.lifecycle))
}
private class CheckUIDestroiedTransformer<T>(val lifecycle: RxLifecycle) :
        ObservableTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream.takeUntil(
                lifecycle.behavior.skipWhile {
                    it != RxLifecycle.Event.DESTROY_VIEW &&
                            it != RxLifecycle.Event.DESTROY &&
                            it != RxLifecycle.Event.DETACH
                }
        )
    }
}