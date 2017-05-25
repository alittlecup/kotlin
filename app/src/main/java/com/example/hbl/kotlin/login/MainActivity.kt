package com.example.hbl.kotlin.login

import com.example.hbl.kotlin.R
import com.example.hbl.library.extensions.snack
import com.example.hbl.library.mvp.MVPActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivity<LoginPresenter>(), LoginContract.View {
    override fun logingSuccess() {
        snack(singIn,"Login Success")
    }

    override val mPresenter = LoginPresenter(this)
    override fun initView() {
        singIn.setOnClickListener {
           if(!edName.text.isEmpty()&&!edPwd.text.isEmpty()){
               mPresenter.login(edName.text.toString(),edPwd.text.toString())
           }
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

}
