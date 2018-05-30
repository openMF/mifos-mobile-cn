package org.mifos.mobile.cn.ui.mifos.launcher

import android.content.Intent
import android.os.Bundle
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import javax.inject.Inject

/**
 * @author Manish Kumar
 * On 31/05/18.
 */

class LauncherActivity:MifosBaseActivity() {

    @Inject
    lateinit var preferencesHelper:PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(DashboardActivity::class.java)

    }


     private fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        startActivity(intent)
        finish()
    }



}