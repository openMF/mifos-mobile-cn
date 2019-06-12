package org.mifos.mobile.cn.ui.base

import androidx.appcompat.widget.Toolbar

interface BaseActivityCallback {

    fun getToolbar(): Toolbar

    fun showJusticeProgressDialog(message: String)

    fun showTabLayout(show: Boolean)

    fun setToolbarTitle(toolbarTitle: String)

    fun hideJusticeProgressDialog()

    fun logout()
}
