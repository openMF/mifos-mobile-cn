package org.mifos.mobile.cn.ui.mifos.launcher

import android.content.Intent
import android.os.Bundle
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import javax.inject.Inject

/**
 * @author Manish Kumar
 * On 31/05/18.
 */

class LauncherActivity : MifosBaseActivity() {

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        //route to different page
        if (preferencesHelper.loginStatus) {
            startActivity(PasscodeActivity::class.java)
        } else {
            startActivity(LoginActivity::class.java)
        }
    }

    private fun startActivity(aClass: Class<*>) {
        val intent = Intent(this, aClass)
        if(aClass.equals(PasscodeActivity::class.java)) {
            intent.putExtra(ConstantKeys.INITIAL_LOGIN, true)
        }
        startActivity(intent)
        finish()
    }

}