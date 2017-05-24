package com.example.hbl.kotlin.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.mvp.MVPActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MVPActivity<>() {
    override fun setLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initPresenter(): {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview.text="Hello Kotlin"
    }
}
