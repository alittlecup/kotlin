package com.example.hbl.kotlin.dir

import com.example.hbl.kotlin.mvp.BasePresenter

/**
 * Created by hbl on 2017/5/26.
 */
class CodeReadPresenter (val mView:CodeReadContract.View) : BasePresenter<CodeReadContract.View>(),CodeReadContract.Presenter {
}