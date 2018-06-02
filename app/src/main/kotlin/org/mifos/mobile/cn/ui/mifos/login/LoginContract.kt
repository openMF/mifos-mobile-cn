package org.mifos.mobile.cn.ui.mifos.login

import org.mifos.mobile.cn.ui.base.MvpView

interface LoginContract {
    interface View : MvpView {
        //TODO:edit this for access tokens and other user data
        fun showUserLoginSuccessfully()

        fun showError(errorMessage: String)

        fun showProgress()

        fun hideProgress()
    }

    interface Presenter {

        fun login(username: String, password: String)
    }

}