package org.mifos.mobile.cn.ui.base

import android.support.v7.widget.Toolbar

interface BaseActivityCallback {

    fun getToolbar(): Toolbar

    fun showJusticeProgressDialog(message: String)

    fun showTabLayout(show: Boolean)

    fun setToolbarTitle(toolbarTitle: String)

    fun hideJusticeProgressDialog()

    fun logout()
}
