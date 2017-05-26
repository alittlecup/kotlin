package com.example.hbl.kotlin.login

import cn.nekocode.template.data.api.GankApi
import com.example.hbl.kotlin.GitHubConfig
import com.example.hbl.kotlin.UserManager
import com.example.hbl.kotlin.data.CreateAuthorization
import com.example.hbl.kotlin.network.NetWorkUtil
import com.example.hbl.kotlin.extensions.log
import com.example.hbl.kotlin.mvp.BasePresenter
import com.example.hbl.kotlin.mvp.bindLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by hbl on 2017/5/24.
 */
class LoginPresenter(val mView: LoginContract.View) : BasePresenter<LoginContract.View>(), LoginContract.Presenter{
    override fun login(name: String, password: String) {
        UserManager.userNamme=name
        UserManager.password=password
        val ca=CreateAuthorization()
        ca.note=GitHubConfig.NOTE
        ca.client_id=GitHubConfig.CLIENT_ID
        ca.client_secret=GitHubConfig.CLIENT_SECRET
        ca.scopes=GitHubConfig.SCOPES
        NetWorkUtil.getService(AccountService::class.java)
                .createAuthorization(ca)
                .flatMap {
                    log(it.token)
                    NetWorkUtil.getService(AccountService::class.java).getUserInfo(it.token)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .bindLifecycle(mView)
                .subscribe {
                    log(it.toString())
                    mView.logingSuccess()
                }
    }

    override fun loadData() {
        NetWorkUtil.getService(GankApi::class.java)
                .getAndroid(10,1)
                .subscribeOn(Schedulers.io())
                .bindLifecycle(mView)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    log(it.string())
                }
        Observable.interval(1,TimeUnit.SECONDS)
                .bindLifecycle(mView)
                .subscribe {
                    log(1)
                }
    }

}