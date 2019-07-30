package org.mifos.mobile.cn.ui.mifos.customerLoanDetails

import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.DataManagerLoanDetails
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject

@ConfigPersistent
class CustomerLoanDetailsPresenter @Inject constructor(@ApplicationContext context: Context, private val dataManagerLoanDetails: DataManagerLoanDetails) :
        BasePresenter<CustomerLoanDetailsContracts.View>(context),CustomerLoanDetailsContracts.Presenter {

    private  val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun attachView(mvpView: CustomerLoanDetailsContracts.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()

    }

    override fun fetchCustomerLoanDetails(productIdentifier: String, caseIdentifier: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        compositeDisposable.add(dataManagerLoanDetails.fetchCustomerLoanDetails(
                productIdentifier, caseIdentifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LoanAccount>() {
                    override fun onNext(loanaccount: LoanAccount) {
                        getMvpView.hideProgressbar()
                        getMvpView.showLoanAccountDetails(loanaccount)
                    }
                    override fun onError(throwable: Throwable) {
                        getMvpView.hideProgressbar()
                        showExceptionError(throwable,
                                context.getString(R.string.error_loading_customer_loan_details))
                    }

                    override fun onComplete() {

                    }
                })
        )
    }


}