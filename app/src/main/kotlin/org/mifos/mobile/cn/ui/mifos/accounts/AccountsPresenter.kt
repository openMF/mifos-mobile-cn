package org.mifos.mobile.cn.ui.mifos.accounts

import android.content.Context
import io.reactivex.Observable
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter

import javax.inject.Inject

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

class AccountsPresenter @Inject constructor(@ApplicationContext context: Context) :
        BasePresenter<AccountsContract.View>(context), AccountsContract.Presenter {

    override fun attachView(mvpView: AccountsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun loadDepositAccounts() {
        checkViewAttached()
        getMvpView.showProgress()
        Observable.fromCallable({ FakeRemoteDataSource.getDepositAccountsJson() }).subscribe({
            if (it.isEmpty()) {
                getMvpView.showEmptyAccounts(context.getString(R.string.deposit))
            } else {
                getMvpView.showDepositAccounts(it)
            }
        })

    }

    override fun loadLoanAccounts() {
        checkViewAttached()
        getMvpView.showProgress()
        Observable.fromCallable({ FakeRemoteDataSource.getLoanAccountsJson() }).subscribe({
            getMvpView.hideProgress()
            if (it.isEmpty()) {
                getMvpView.showEmptyAccounts(context.getString(R.string.loan))
            } else {
                getMvpView.showLoanAccounts(it)
            }
        })
    }
}