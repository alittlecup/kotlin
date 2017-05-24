package cn.nekocode.template.data.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
internal interface GankApi {

    @GET("Android/{count}/{pageNum}")
    fun getAndroid(@Path("count") count: Int, @Path("pageNum") pageNum: Int): Observable<ResponseBody>
}