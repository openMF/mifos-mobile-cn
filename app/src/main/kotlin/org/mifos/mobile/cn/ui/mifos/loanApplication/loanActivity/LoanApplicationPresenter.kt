package org.mifos.mobile.cn.ui.mifos.loanApplication.loanActivity

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.data.datamanager.DataManagerLoan
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject


/**
 * @author Manish Kumar
 * @since 01/August/2018
 */
class LoanApplicationPresenter
@Inject constructor(@ApplicationContext context: Context, var dataManagerLoan: DataManagerLoan)
    : BasePresenter<LoanApplicationContract.View>(context), LoanApplicationContract.Presenter {

    val compositeDisposable: CompositeDisposable
    init {
            compositeDisposable = CompositeDisposable()
    }

    override fun createLoan(loanAccount: LoanAccount) {
        isViewAttached()
        compositeDisposable.add(dataManagerLoan.saveLoanResponse(loanAccount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<LoanAccount>() {
                    override fun onNext(t: LoanAccount) {

                    }
                    override fun onError(e: Throwable) {
                     getMvpView.showError(e.localizedMessage)
                    }
                    override fun onComplete() {
                     getMvpView.applicationCreatedSuccessfully()
                    }
                })
        )
    }
}