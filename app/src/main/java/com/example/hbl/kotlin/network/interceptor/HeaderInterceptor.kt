package com.example.hbl.kotlin.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by hbl on 2017/5/23.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain?): Response {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}