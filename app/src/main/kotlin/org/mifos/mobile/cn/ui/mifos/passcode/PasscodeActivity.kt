package org.mifos.mobile.cn.ui.mifos.passcode

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.mifos.mobile.passcode.MifosPassCodeActivity
import com.mifos.mobile.passcode.utils.EncryptionUtil
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.utils.MaterialDialog
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
        MaterialDialog.Builder().init(this)
                .setMessage(R.string.login_using_password_confirmation)
                .setPositiveButton(getString(R.string.logout),
                        DialogInterface.OnClickListener { dialog, which ->
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                            //Show successful logout Toast.
                            Toast.makeText(applicationContext, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
                        })
                .setNegativeButton(getString(R.string.cancel),
                        DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
                .createMaterialDialog()
                .show()
    }

    override fun showToaster(view: View, msg: Int) {
        Toaster.show(view, msg)
    }

    override fun getEncryptionType(): Int {
        return  EncryptionUtil.FINERACT_CN
    }

}