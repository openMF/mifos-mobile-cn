package org.mifos.mobile.cn.ui.mifos.signup

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginContract

class SignupActivity: MifosBaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }
}