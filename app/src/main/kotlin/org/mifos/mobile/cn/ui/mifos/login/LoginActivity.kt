package org.mifos.mobile.cn.ui.mifos.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Toaster
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : MifosBaseActivity(), LoginContract.View, View.OnClickListener {

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    @Inject
    internal lateinit var loginPresenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setToolbarTitle(getString(R.string.login))
        activityComponent.inject(this)
        loginPresenter.attachView(this)
        preferencesHelper.clear()

        //TODO:remove this while implementing API
        preferencesHelper.putUsername("fineractCn")
        preferencesHelper.putPassword("password")
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> login()
        }

    }

    internal fun login() {

        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()
        loginPresenter.login(username, password)
        Toaster.show(findViewById(android.R.id.content), getString(R.string.logging_in), Toaster.SHORT)
    }


    //TODO:edit this fun while implementing API
    override fun showUserLoginSuccessfully() {
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
}