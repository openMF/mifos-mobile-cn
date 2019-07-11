package org.mifos.mobile.cn.ui.mifos.identificationdetails

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject

@ConfigPersistent
class IdentificationDeatilsPresenter @Inject constructor(@ApplicationContext context: Context, dataManagerCustomer: DataManagerCustomer):
        BasePresenter<IdentificationDetailsContract.View>(context),IdentificationDetailsContract.Presenter {


    lateinit var compositeDisposable: CompositeDisposable
    lateinit var dataManagerCustomer: DataManagerCustomer

    init {
        this.dataManagerCustomer = dataManagerCustomer
        compositeDisposable = CompositeDisposable()
    }

    override fun attachView(mvpView: IdentificationDetailsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun fetchIdentificationScanCards(customerIdentifier: String, identificationNumber: String) {
        checkViewAttached()
        compositeDisposable.add(dataManagerCustomer.fetchIdentificationScanCards(customerIdentifier, identificationNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<ScanCard>>() {
                    override fun onNext(scanCards: List<ScanCard>) {
                        if (scanCards.size == 0) {
                            getMvpView.showScansStatus(
                                    context.getString(R.string.empty_scans_to_show))
                        } else {
                            getMvpView.showScanCards(scanCards)
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        showExceptionError(throwable,
                                context.getString(R.string.error_fetching_scans))
                    }

                    override fun onComplete() {

                    }
                })
        )

    }
}
