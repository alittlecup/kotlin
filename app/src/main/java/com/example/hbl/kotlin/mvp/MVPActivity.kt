package com.example.hbl.kotlin.mvp

import android.os.Bundle
import cn.nekocode.kotgo.component.rx.LifecycleActivity

abstract class MVPActivity<T : IView, P : BasePresenter<T>> : LifecycleActivity(), IView {
    lateinit var mPresenter: P
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayoutId())
        mPresenter=initPresenter()
    }


    override fun toast(s: String) {
        toast(s)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    abstract fun setLayoutId(): Int
    abstract fun initPresenter(): P
}
