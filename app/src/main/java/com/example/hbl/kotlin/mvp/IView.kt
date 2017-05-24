package com.example.hbl.kotlin.mvp

import cn.nekocode.kotgo.component.rx.RxLifecycle

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