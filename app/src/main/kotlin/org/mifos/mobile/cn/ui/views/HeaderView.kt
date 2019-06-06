package org.mifos.mobile.cn.ui.views

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_collapsing_header_view.view.*

 open class HeaderView : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?,attrs:AttributeSet):super(context,attrs)
    constructor(context: Context?,attrs: AttributeSet,defStyleAttrs:Int):super(context,attrs,defStyleAttrs)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?,attrs: AttributeSet,defStyleAttrs:Int,defStyleRes:Int):super(context,attrs,defStyleAttrs,defStyleRes)

    fun bindTo(title:String){bindTo(title," ")}

    fun bindTo(title: String, subtitle: String){
            hideOrSetText(header_view_title,title)
            hideOrSetText(header_view_sub_title,subtitle)
    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "") {
            tv.visibility = View.GONE
        } else {
            tv.text = text
        }
    }
}