package org.mifos.mobile.cn.ui.mifos.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
        /* Commented this line out as the redesign doesn't require the toolbar but the
        MifosBaseActivity needs it so it can't be deleted. Didn't delete this line for future
        reference if anyone wants to bring back the toolbar.
         */
        //setToolbarTitle(getString(R.string.login))
        activityComponent.inject(this)
        loginPresenter.attachView(this)
        preferencesHelper.clear()

        //TODO:remove this while implementing API
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnLogin -> login()
        }

    }

    internal fun login() {

        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if(!TextUtils.isEmpty(username)){
            preferencesHelper.putUsername("fineractCn")
        }
        else{
            etUsername.error = "Username required"
            return
        }
        if(!TextUtils.isEmpty(password)){
            preferencesHelper.putPassword("password")
        }
        else{
            etPassword.error = "Password required"
            return
        }
        loginPresenter.login(username, password)
        Toaster.show(findViewById(android.R.id.content), getString(R.string.logging_in), Toaster.SHORT)
    }


    //TODO:edit this fun while implementing API
    override fun showUserLoginSuccessfully() {
        preferencesHelper.putLoginStatus(true)
        preferencesHelper.putAccessToken("access_token")
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