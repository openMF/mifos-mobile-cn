package org.mifos.mobile.cn.ui.mifos.plannedPlayment

import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.datamanager.DataManagerIndividualLending
import org.mifos.mobile.cn.data.models.payment.PlannedPayment
import org.mifos.mobile.cn.data.models.payment.PlannedPaymentPage
import org.mifos.mobile.cn.data.models.product.ProductPage
import org.mifos.mobile.cn.exceptions.NoConnectivityException
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject

@ConfigPersistent
class PlannedPaymentPresenter @Inject constructor(@ApplicationContext context:Context,private  val dataManagerIndividualLending: DataManagerIndividualLending):
BasePresenter<PlannedPaymentContract.View>(context),PlannedPaymentContract.Presenter{

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val PAYMENT_LIST_SIZE = 50
    private var loadmore = false
    override fun attachView(mvpView: PlannedPaymentContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }


    override fun fetchPlannedPayment(productIdentifier: String, caseIdentifier: String, pageIndex: Int?, initialDisbursalDate: String, loadmore: Boolean?) {
            this.loadmore = loadmore!!
        fetchPlannedPayment(productIdentifier, caseIdentifier, pageIndex, initialDisbursalDate)
    }

    override fun fetchPlannedPayment(productIdentifier: String, caseIdentifier: String, pageIndex: Int?, initialDisbursalDate: String) {
            checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<LiveData<PlannedPaymentPage>> {
            FakeRemoteDataSource.getPlannedPaymentPage()
        }).subscribe({
            getMvpView.hideProgressbar()
            getMvpView.showPlannedPayment(it.value!!.elements)
        })
    }

    override fun showPlannedPayment(plannedPayments: List<PlannedPayment>) {
        if (loadmore) {
            getMvpView.showMorePlannedPayments(plannedPayments)
        } else {
            getMvpView.showPlannedPayment(plannedPayments)
        }
    }
}