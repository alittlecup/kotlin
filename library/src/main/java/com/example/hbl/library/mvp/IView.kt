package com.example.hbl.library.mvp


/**
 * Created by hbl on 2017/5/22.
 */
interface IView {
    val lifecycle: RxLifecycle
    val mPresenter: IPresenter
    fun toast(s: String)
    fun showLoading()
    fun dismissLoading()
}