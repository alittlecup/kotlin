package com.example.hbl.kotlin.mvp
/**
 * Created by hbl on 2017/5/22.
 */
open class BasePresenter<V: IView>(protected val mView:V):IPresenter{

}