package com.example.hbl.kotlin.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by hbl on 2017/5/22.
 */
class NetWorkUtil {

    companion object {
        private val builder by lazy {
            Retrofit.Builder()
        }
        private val gsonDateFormat by lazy {
            GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create()
        }

        fun <T> getService(client: OkHttpClient, cls: Class<T>): T {
            var retrofit = builder
                    .client(client)
                    .baseUrl("http://gank.io/api/data/")
                    .addConverterFactory(GsonConverterFactory.create(gsonDateFormat))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            return retrofit.create(cls)
        }

        fun <T> getService(cls: Class<T>): T {
            return getService(HttpClientFactory.defaultClientBuilder(), cls)
        }

    }
}
