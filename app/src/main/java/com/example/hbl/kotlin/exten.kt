package com.example.hbl.kotlin

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import cn.nekocode.kotgo.component.rx.RxLifecycle
import com.example.hbl.kotlin.mvp.IView
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by hbl on 2017/5/22.
 */

inline fun <reified T> T.log(message: Any) {
    Log.d(T::class.simpleName, message.toString())
}


@Deprecated("使用 T.orElse(orBlock: () -> T)", replaceWith = ReplaceWith("or(orBlock: () -> T)"))
inline fun <R> orElse(block: () -> R): R = block()

inline infix fun <T : Any?> T.orElse(orBlock: () -> Unit) = if (this == null) orBlock() else this

inline fun guard(vararg params: Any?): Boolean? {
    return if (params.all { it != null }) true else null
}

//三目运算
fun <T> select(isTrue: Boolean, param1: () -> T, param2: () -> T) = if (isTrue) param1() else param2()

fun <T> select(isTrue: Boolean, param1: T, param2: T) = if (isTrue) param1 else param2

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
fun snack(view:View,msg:String){
    Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show()
}