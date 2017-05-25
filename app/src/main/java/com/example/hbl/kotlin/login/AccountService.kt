package com.example.hbl.kotlin.login

import com.example.hbl.kotlin.data.AuthorizationResp
import com.example.hbl.kotlin.data.CreateAuthorization
import com.example.hbl.kotlin.data.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by hbl on 2017/5/25.
 */
interface AccountService {

    @POST("/authorizations")
    fun createAuthorization(@Body createAuthorization: CreateAuthorization): Observable<AuthorizationResp>

    @GET("/user")
    fun getUserInfo(@Query("access_token") accessToken: String): Observable<User>
}