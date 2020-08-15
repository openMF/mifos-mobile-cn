package org.mifos.mobile.cn.ui.mifos.login

import android.content.Context
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.datamanager.DataManagerAuth
import org.mifos.mobile.cn.data.datamanager.contracts.ManagerCustomer
import org.mifos.mobile.cn.data.models.Authentication
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject

class LoginPresenter @Inject
constructor(@ApplicationContext context: Context,  dataManagerAuth: DataManagerAuth) :
        BasePresenter<LoginContract.View>(context), LoginContract.Presenter {
    val LOG_TAG = LoginPresenter::class.java.simpleName
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var dataManagerAuth: DataManagerAuth = dataManagerAuth
    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }
   /* @Inject
    internal lateinit var preferencesHelper: PreferencesHelper
*/
    //TODO: edit this while implementing API
    override fun login(username: String, password: String) {
        checkViewAttached()
        getMvpView.showProgress()
        compositeDisposable.add(dataManagerAuth.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Authentication>() {
                    override fun onNext(user: Authentication) {
                        getMvpView.hideProgress()
                        getMvpView.showUserLoginSuccessfully(user)
                        /*if (isCredentialValid(username, password)) {
                            getMvpView.hideProgress()
                            if (preferencesHelper.password.equals(password)
                                    && preferencesHelper.getUserName().equals(username)) {
                                getMvpView.showUserLoginSuccessfully(user)
                            } else {
                                getMvpView.hideProgress()
                                getMvpView.showUserLoginSuccessfully(user)
                            }

                        }*/

                    }

                    override fun onComplete() {}
                    override fun onError(e: Throwable) {
                        getMvpView.hideProgress()
                        Toast.makeText(context, R.string.error_logging_in, Toast.LENGTH_SHORT).show()
                    }
                })
        )

    }

    override fun attachView(mvpView: LoginContract.View) {
        super.attachView(mvpView)
    }

    /*fun isCredentialValid(username: String, password: String): Boolean {

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
    }*/
}