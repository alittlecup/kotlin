package com.example.hbl.kotlin

import android.util.Log

/**
 * Created by hbl on 2017/5/22.
 */

inline fun <reified T> T.log(message: Any) {
    Log.d(T::class.simpleName, message.toString())
}


