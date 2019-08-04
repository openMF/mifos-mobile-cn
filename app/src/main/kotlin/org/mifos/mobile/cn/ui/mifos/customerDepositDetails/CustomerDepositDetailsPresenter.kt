package org.mifos.mobile.cn.ui.mifos.customerDepositDetails

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.datamanager.DataManagerDepositDetails
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
class CustomerDepositDetailsPresenter @Inject constructor(@ApplicationContext context: Context, private val dataManagerDepositDetails: DataManagerDepositDetails) :
        BasePresenter<CustomerDepositDetailsContracts.View>(context), CustomerDepositDetailsContracts.Presenter {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(mvpView: CustomerDepositDetailsContracts.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun fetchDepositAccountDetails(accountIdentifier: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        compositeDisposable.add(dataManagerDepositDetails.getCustomerDepositAccountDetails(
                accountIdentifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<DepositAccount>() {
                    override fun onNext(customerDepositAccounts: DepositAccount) {
                        getMvpView.hideProgressbar()
                        getMvpView.showDepositDetails(customerDepositAccounts)
                    }

                    override fun onError(throwable: Throwable) {
                        getMvpView.hideProgressbar()
                        showExceptionError(throwable,
                                context.getString(R.string.error_loading_deposit_details))
                    }

                    override fun onComplete() {

                    }
                })
        )
    }
}