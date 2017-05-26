package com.example.hbl.kotlin.coderead

import android.app.Activity
import android.os.Bundle

import com.example.hbl.kotlin.R
import kotlinx.android.synthetic.main.activity_code_read.*

class CodeReadActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_read)
        val settings = webview.settings
        settings.javaScriptEnabled=true
        settings.domStorageEnabled=true
        settings.setSupportZoom(true)
        settings.builtInZoomControls=true
        webview.loadUrl("file:///android_asset/code.html")
    }

}
