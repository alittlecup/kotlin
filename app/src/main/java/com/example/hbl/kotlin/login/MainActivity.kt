package com.example.hbl.kotlin.login

import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.mvp.MVPActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivity<LoginPresenter>(), LoginContract.View {
    override val mPresenter = LoginPresenter(this)
    override fun initView() {
        textview.setOnClickListener {
            mPresenter.loadData()
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

}
