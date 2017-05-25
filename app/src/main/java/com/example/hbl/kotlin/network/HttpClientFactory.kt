package com.example.hbl.kotlin.network

import com.example.hbl.kotlin.BuildConfig
import com.example.hbl.kotlin.network.interceptor.HeaderInterceptor
import com.example.hbl.kotlin.select
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by hbl on 2017/5/22.
 */
class HttpClientFactory {

    companion object {
        val DEFAUT_CONNECT_TIMEOUT = 10L
        val DEFAUT_READ_TIMEOUT = 10L
        val DEFAUT_DOWNLOAD_READ_TIMEOUT = 10
        val builder by lazy {
            OkHttpClient.Builder()
                    .connectTimeout(DEFAUT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAUT_READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(HeaderInterceptor())
                    .addInterceptor(
                        HttpLoggingInterceptor().setLevel(select(
                                BuildConfig.DEBUG,
                                HttpLoggingInterceptor.Level.BODY,
                                HttpLoggingInterceptor.Level.NONE))
                    )
                    .hostnameVerifier { hostname, session -> true }
        }

        fun defaultClientBuilder(): OkHttpClient {
            return builder.build()
        }
    }
}