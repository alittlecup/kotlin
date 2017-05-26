package com.example.hbl.kotlin.mvp

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author nekocode (nekocode.cn@gmail.com)
 */
abstract class MVPFragment<P : IPresenter> : Fragment(), IView {
    override val lifecycle = RxLifecycle()

    override fun toast(s: String) {
        toast(s)
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }




    @CallSuper
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        lifecycle.onAttach()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lifecycle.onCreateView()
        return super.onCreateView(inflater, container, savedInstanceState)
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
    override fun onDestroyView() {
        lifecycle.onDestroyView()
        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        lifecycle.onDestroy()
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycle.onDetach()
        super.onDetach()
    }
}