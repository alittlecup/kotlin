package com.example.hbl.kotlin.login

import com.example.hbl.kotlin.mvp.IPresenter
import com.example.hbl.kotlin.mvp.IView


/**
 * Created by hbl on 2017/5/24.
 */
interface LoginContract {
    interface View : IView {
        fun logingSuccess()
    }
    interface Presenter : IPresenter {
        fun loadData()
        fun login(name:String,password:String)
    }
}