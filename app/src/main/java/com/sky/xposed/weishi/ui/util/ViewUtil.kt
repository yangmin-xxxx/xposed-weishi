/*
 * Copyright (c) 2018 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.xposed.weishi.ui.util

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.sky.xposed.weishi.Constant
import com.sky.xposed.weishi.ui.view.SimpleItemView
import com.sky.xposed.weishi.ui.view.SwitchItemView
import com.sky.xposed.weishi.util.Alog

object ViewUtil {

    fun setVisibility(view: View?, visibility: Int) {

        if (view == null || view.visibility == visibility) return

        view.visibility = visibility
    }

    fun setVisibility(visibility: Int, vararg views: View) {

        if (views == null) return

        for (view in views) {
            setVisibility(view, visibility)
        }
    }

    fun setTypeface(typeface: Typeface?, vararg textViews: TextView) {

        if (typeface == null || textViews == null) return

        for (textView in textViews) {
            textView.typeface = typeface
        }
    }

    fun getText(textView: TextView?): String? {
        return if (textView != null) charSequenceToString(textView.text) else null
    }

    fun charSequenceToString(charSequence: CharSequence?): String? {
        return charSequence?.toString()
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText  需要判断的EditText
     * @return  true：可以滚动   false：不可以滚动
     */
    fun canVerticalScroll(editText: EditText?): Boolean {

        if (editText == null) return false

        //滚动的距离
        val scrollY = editText.scrollY
        //控件内容的总高度
        val scrollRange = editText.layout.height
        //控件实际显示的高度
        val scrollExtent = editText.height - editText.compoundPaddingTop - editText.compoundPaddingBottom
        //控件内容总高度与实际显示高度的差值
        val scrollDifference = scrollRange - scrollExtent

        return if (scrollDifference == 0) {
            false
        } else scrollY > 0 || scrollY < scrollDifference - 1

    }

    fun newDefaultBackgroundDrawable(): StateListDrawable {
        return newBackgroundDrawable(0xFFDFDFDF.toInt(), Color.WHITE)
    }

    fun newBackgroundDrawable(): StateListDrawable {
        return newBackgroundDrawable(0x66666666, 0x00000000)
    }

    fun newBackgroundDrawable(pressed: Int, background: Int): StateListDrawable {

        val drawable = StateListDrawable()

        drawable.addState(intArrayOf(android.R.attr.state_pressed), ColorDrawable(pressed))
        drawable.addState(intArrayOf(), ColorDrawable(background))

        return drawable
    }

    fun newLineView(context: Context): View {

        val lineView = View(context)
        lineView.setBackgroundColor(0xFFDFDFDF.toInt())
        lineView.layoutParams = LayoutUtil.newViewGroupParams(
                FrameLayout.LayoutParams.MATCH_PARENT, 2)
        return lineView
    }

    fun newSwitchItemView(context: Context, name: String): SwitchItemView {

        val itemView = SwitchItemView(context)
        itemView.name = name

        return itemView
    }

    fun newSimpleItemView(context: Context, name: String): SimpleItemView {

        val itemView = SimpleItemView(context)
        itemView.setName(name)

        return itemView
    }

    fun findFirstView(viewGroup: ViewGroup, className: String): View? {

        for (index in 0 until viewGroup.childCount) {

            val view = viewGroup.getChildAt(index)

            if (view.javaClass.name == className) {
                return view
            }
        }
        return null
    }

    fun setInputType(editText: EditText, inputType: Int) {

        when (inputType) {
            Constant.InputType.NUMBER -> editText.inputType = EditorInfo.TYPE_CLASS_NUMBER
            Constant.InputType.NUMBER_DECIMAL -> editText.inputType = EditorInfo.TYPE_NUMBER_FLAG_DECIMAL or EditorInfo.TYPE_CLASS_NUMBER
            Constant.InputType.NUMBER_SIGNED -> editText.inputType = EditorInfo.TYPE_NUMBER_FLAG_SIGNED or EditorInfo.TYPE_CLASS_NUMBER
            Constant.InputType.PHONE -> editText.inputType = EditorInfo.TYPE_CLASS_PHONE
            Constant.InputType.TEXT -> editText.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE
            Constant.InputType.TEXT_PASSWORD -> editText.inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            Constant.InputType.NUMBER_PASSWORD -> editText.inputType = EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD
        }
    }
}