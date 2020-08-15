package org.mifos.mobile.cn.ui.mifos.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.models.Authentication
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity
import org.mifos.mobile.cn.ui.mifos.signup.SignupActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Toaster
import javax.inject.Inject


class LoginActivity : MifosBaseActivity(), LoginContract.View, View.OnClickListener {

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    @Inject
    internal lateinit var loginPresenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //setToolbarTitle(getString(R.string.login))
        activityComponent.inject(this)
        loginPresenter.attachView(this)
        //preferencesHelper.clear()

        //TODO:remove this while implementing API
        btnLogin.setOnClickListener(this)
        create.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnLogin -> login()
            R.id.create -> signup()
        }

    }

    private fun signup() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun login() {

        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        preferencesHelper.putTenantIdentifier("tn01")
        Log.v("Hello","Hello")
        if(!TextUtils.isEmpty(username)){
           // preferencesHelper.putUsername("interopUser")
        }
        else{
            etUsername.error = "Username required"
            return
        }
        if(!TextUtils.isEmpty(password)){
            //preferencesHelper.putPassword("intop@d1")
        }
        else{
            etPassword.error = "Password required"
            return
        }

        loginPresenter.login(username, password)
        Toaster.show(findViewById(android.R.id.content), getString(R.string.logging_in), Toaster.SHORT)
    }


    //TODO:edit this fun while implementing API

    override fun showUserLoginSuccessfully(user: Authentication) {
        preferencesHelper.putAccessToken(user.accessToken)
        Log.d("Hello",user.accessToken)
        preferencesHelper.putSignInUser(user)
        preferencesHelper.putUsername(etUsername.text.toString().trim())
        preferencesHelper.putLoginStatus(true)
        Toast.makeText(this, R.string.welcome, Toast.LENGTH_SHORT).show()
        startPassCodeActivity()
    }

    fun startPassCodeActivity() {
        val intent = Intent(this, PasscodeActivity::class.java)
        intent.putExtra(ConstantKeys.INITIAL_LOGIN, true)
        startActivity(intent)
        finish()
    }

    override fun showError(errorMessage: String) {
        Toaster.show(findViewById(android.R.id.content), errorMessage, Toaster.SHORT)
    }

    override fun showProgress() {
        showProgressbar()
    }

    override fun hideProgress() {
        hideProgressbar()
    }
    override fun showUserLoginUnSuccessfully(){
      preferencesHelper.putLoginStatus(false)
        Toast.makeText(this,"Invalid Credentials",Toast.LENGTH_SHORT).show()
    }
}