package com.example.hbl.library.extensions

import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View


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


fun snack(view:View,msg:String){
    Snackbar.make(view,msg,Snackbar.LENGTH_SHORT).show()
}