package com.example.hbl.kotlin.login

import cn.nekocode.template.data.api.GankApi
import com.example.hbl.kotlin.andex.bindLifecycle
import com.example.hbl.kotlin.log
import com.example.hbl.kotlin.mvp.BasePresenter
import com.example.hbl.kotlin.network.NetWorkUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by hbl on 2017/5/24.
 */
class LoginPresenter(val mView: LoginContract.View) : BasePresenter<LoginContract.View>(), LoginContract.Presenter{
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