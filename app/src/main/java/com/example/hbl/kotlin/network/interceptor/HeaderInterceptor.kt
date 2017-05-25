package com.example.hbl.kotlin.network.interceptor

import com.example.hbl.kotlin.UserManager
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by hbl on 2017/5/23.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        UserManager.baseAuth()?.let {
            val requestBuilder = original.newBuilder()
                    .header("Authorization", it.trim())
            val request = requestBuilder.build()
            return chain.proceed(request)
        } ?: return chain.proceed(original)
    }

}