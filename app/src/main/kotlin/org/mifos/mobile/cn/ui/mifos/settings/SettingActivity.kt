package org.mifos.mobile.cn.ui.mifos.settings


import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_customer_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import org.mifos.mobile.cn.ui.utils.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import org.mifos.mobile.cn.ui.mifos.aboutus.AboutUsActivity
import org.mifos.mobile.cn.ui.mifos.customerProfile.CustomerProfileContract
import org.mifos.mobile.cn.ui.mifos.help.HelpActivity
import org.mifos.mobile.cn.ui.mifos.help.HelpFragment
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import javax.inject.Inject


class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    fun notificationAct(view: View) {

    }
    fun about_ac(view: View) {
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)
    }
    fun helpAc(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    fun logout(view: View) {
        MaterialDialog.Builder().init(this)
                .setMessage(R.string.dialog_logout)
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
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(applicationContext, "Canceled Successfully!", Toast.LENGTH_SHORT).show()

                        })
                .createMaterialDialog()
                .show()
    }

    fun share(view: View) {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_link))
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.share_message))
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_the_app_link)))
    }

    fun back(view: View) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

