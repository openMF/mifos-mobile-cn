package org.mifos.mobile.cn.ui.utils

import android.R.attr
import android.R.id
import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams


/**
 * @author Rajan Maurya
 */
class ProgressBarHandler constructor(context: Context) {

    private val progressBar: ProgressBar

    init {

        val layout = (context as Activity).findViewById<View>(id.content).rootView as ViewGroup

        progressBar = ProgressBar(context, null, attr.progressBarStyleInverse)
        progressBar.isIndeterminate = true

        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        val relativeLayout = RelativeLayout(context)

        relativeLayout.gravity = Gravity.CENTER
        relativeLayout.addView(progressBar)

        layout.addView(relativeLayout, params)

        hide()
    }

    fun show() {
        progressBar.visibility = View.VISIBLE
    }

    fun hide() {
        progressBar.visibility = View.INVISIBLE
    }
}