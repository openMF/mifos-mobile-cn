package org.mifos.mobile.cn.ui.mifos.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_signup.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity

class SignupActivity: MifosBaseActivity(){
    var login: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        login = findViewById(R.id.already_have_an_account)

        already_have_an_account.setOnClickListener(View.OnClickListener {
            val R = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(R)
            finish()
        })

    }
}