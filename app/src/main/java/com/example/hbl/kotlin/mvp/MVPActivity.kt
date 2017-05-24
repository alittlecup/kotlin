package com.example.hbl.kotlin.mvp

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import cn.nekocode.kotgo.component.rx.RxLifecycle

abstract class MVPActivity<P : IPresenter> : AppCompatActivity(), IView {
    override val lifecycle = RxLifecycle()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
        setContentView(setLayoutId())
        initView()
    }
    abstract fun initView()


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


    @CallSuper
    override fun onRestart() {
        super.onRestart()
        lifecycle.onRestart()
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycle.onResume()
    }

    @CallSuper
    override fun onPause() {
        lifecycle.onPause()
        super.onPause()
    }

    @CallSuper
    override fun onStop() {
        lifecycle.onStop()
        super.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycle.onDestroy()
        super.onDestroy()
    }
}

