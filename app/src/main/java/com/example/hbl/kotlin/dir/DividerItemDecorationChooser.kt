/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hbl.kotlin.dir

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

import com.example.hbl.kotlin.R
import com.example.hbl.kotlin.extensions.select

class DividerItemDecorationChooser : RecyclerView.ItemDecoration {

    private var mOrientation: Int = 0
    private var padding: Int = 0
    private var startpadding: Int = 0
    private var endpadding: Int = 0
    private var dividerHeight: Int = 0
    private var mContext: Context? = null
    private val mPaddingPaint:Paint= Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDividerPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    constructor(context: Context, orientation: Int = VERTICAL_LIST, dividerHeight: Int = -1, startpadding: Int = -1, endpadding: Int = -1) {
        setOrientation(orientation)
        mContext = context

        init()
        if (startpadding != -1) this.startpadding = startpadding
        this.endpadding = select(endpadding == -1, startpadding, endpadding)
        if (dividerHeight != -1) this.dividerHeight = dividerHeight
    }

    private fun init() {
        padding = mContext!!.resources.getDimensionPixelSize(R.dimen.medium_padding)
        dividerHeight = DEFAULT_DIVIDER_HEIGHT

        mPaddingPaint.color = ContextCompat.getColor(mContext!!, R.color.item_background)
        mPaddingPaint.style = Paint.Style.FILL

        mDividerPaint.color = ContextCompat.getColor(mContext!!, R.color.color_divider)
        mDividerPaint.style = Paint.Style.FILL
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    fun drawVertical(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 1 - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin +
                    Math.round(ViewCompat.getTranslationY(child))
            val bottom = top + dividerHeight

            c.drawRect(left.toFloat(), top.toFloat(), (left + startpadding).toFloat(), bottom.toFloat(), mPaddingPaint)
            c.drawRect((right - endpadding).toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaddingPaint)
            c.drawRect((left + startpadding).toFloat(), top.toFloat(), (right - endpadding).toFloat(), bottom.toFloat(), mDividerPaint)
        }
    }

    fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val top = parent.paddingTop
        val bottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0..childCount - 1 - 1) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.right + params.rightMargin +
                    Math.round(ViewCompat.getTranslationX(child))
            val right = left + dividerHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), (top + startpadding).toFloat(), mPaddingPaint)
            c.drawRect(left.toFloat(), (bottom - endpadding).toFloat(), right.toFloat(), bottom.toFloat(), mPaddingPaint)
            c.drawRect(left.toFloat(), (top + startpadding).toFloat(), right.toFloat(), (bottom - endpadding).toFloat(), mDividerPaint)
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (mOrientation == VERTICAL_LIST) {
            if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
                outRect.set(0, 0, 0, dividerHeight)
            } else {
                outRect.set(0, 0, 0, 0)
            }
        } else {
            if (parent.getChildAdapterPosition(view) != parent.adapter.itemCount - 1) {
                outRect.set(0, 0, dividerHeight, 0)
            } else {
                outRect.set(0, 0, 0, 0)
            }
        }

    }

    companion object {
        private val DEFAULT_DIVIDER_HEIGHT = 1

        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL

        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }
}
