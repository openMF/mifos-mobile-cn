package org.mifos.mobile.cn.ui.mifos.passcode

import android.content.Intent
import android.view.View
import com.mifos.mobile.passcode.MifosPassCodeActivity
import com.mifos.mobile.passcode.utils.EncryptionUtil
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.utils.Toaster


class PasscodeActivity : MifosPassCodeActivity(){

    override fun getLogo(): Int {
     return R.drawable.mifos_logo_new
    }

    override fun startNextActivity() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun startLoginActivity() {
        val intentLogin = Intent(this, LoginActivity::class.java)
        intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentLogin)
        finish()
    }

    override fun showToaster(view: View, msg: Int) {
        Toaster.show(view, msg)
    }

    override fun getEncryptionType(): Int {
        return  EncryptionUtil.FINERACT_CN
    }

}