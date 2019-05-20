package org.mifos.mobile.cn.ui.mifos.login

import android.content.Context
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject
constructor(@ApplicationContext context: Context) :
        BasePresenter<LoginContract.View>(context), LoginContract.Presenter {

    @Inject
    internal lateinit var preferencesHelper: PreferencesHelper

    //TODO: edit this while implementing API
    override fun login(username: String, password: String) {
        checkViewAttached()
        getMvpView.showProgress()
        if (isCredentialValid(username, password)) {
            getMvpView.hideProgress()
            if (preferencesHelper.password.equals(password)
                    && preferencesHelper.username.equals(username)) {
                getMvpView.showUserLoginSuccessfully()
            } else {
                getMvpView.hideProgress()
                getMvpView.showUserLoginUnSuccessfully()
            }

        }
    }

    override fun attachView(mvpView: LoginContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
    }

    fun isCredentialValid(username: String, password: String): Boolean {

        val resources = context.resources
        val correctUsername = username.trim()

        if (username.isEmpty()) {
            getMvpView.showError(context.getString(R.string.error_validation_blank,
                    context.getString(R.string.username)))
            return false
        } else if (username.length < 5) {
            getMvpView.showError(context.getString(R.string.error_validation_minimum_chars,
                    resources.getString(R.string.username),
                    resources.getInteger(R.integer.username_minimum_length)))
            return false
        } else if (correctUsername.contains(" ")) {
            getMvpView.showError(context.getString(
                    R.string.error_validation_cannot_contain_spaces,
                    resources.getString(R.string.username)))
            return false
        } else if (password.isEmpty()) {
            getMvpView.showError(context.getString(R.string.error_validation_blank,
                    context.getString(R.string.password)))
            return false
        } else if (password.length < 6) {
            getMvpView.showError(context.getString(R.string.error_validation_minimum_chars,
                    resources.getString(R.string.password),
                    resources.getInteger(R.integer.password_minimum_length)))
            return false
        }

        return true
    }
}