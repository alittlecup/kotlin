package com.example.hbl.kotlin

import android.util.Base64

/**
 * Created by hbl on 2017/5/25.
 */
object UserManager {
    lateinit var userNamme: String
    lateinit var password: String
    fun baseAuth(): String? {
        if (!userNamme.isEmpty() && !password.isEmpty()) {
            val encode =String( Base64.encode("$userNamme:$password".toByteArray(), Base64.DEFAULT))
            return "Basic "+encode
        }
        return null
    }

}