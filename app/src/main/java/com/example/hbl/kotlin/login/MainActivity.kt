package com.example.hbl.kotlin.login

import android.content.Intent
import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.dir.CodeReadActivity
import com.example.hbl.kotlin.extensions.snack
import com.example.hbl.kotlin.mvp.MVPActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivity<LoginPresenter>(), LoginContract.View {
    override fun logingSuccess() {
        snack(singIn,"Login Success")
        startActivity(Intent(this,CodeReadActivity::class.java))
    }

    override val mPresenter = LoginPresenter(this)
    override fun initView() {
        singIn.setOnClickListener {
           if(!edName.text.isEmpty()&&!edPwd.text.isEmpty()){
               mPresenter.login(edName.text.toString(),edPwd.text.toString())
               singIn.requestFocus()
           }
        }
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_main
    }

}
