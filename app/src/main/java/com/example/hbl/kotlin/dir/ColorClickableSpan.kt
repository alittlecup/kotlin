package com.example.hbl.kotlin.dir

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class ColorClickableSpan : ClickableSpan() {

    override fun updateDrawState(tp: TextPaint) {
        tp.color = Color.WHITE
        tp.isUnderlineText = false
    }
}
