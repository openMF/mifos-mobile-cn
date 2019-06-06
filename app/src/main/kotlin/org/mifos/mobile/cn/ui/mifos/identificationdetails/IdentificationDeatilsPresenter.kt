package org.mifos.mobile.cn.ui.mifos.identificationdetails

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
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
        BasePresenter<IdentificationDetailsContract.View>(context),IdentificationDetailsContract.Presenter{


    val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(mvpView: IdentificationDetailsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun fetchIdentificationScanCards(customerIdentifier: String, identificationNumber: String) {
        checkViewAttached()
        Observable.fromCallable(Callable<List<ScanCard>> {
            FakeRemoteDataSource.getScanCards()
        }).subscribe({
            getMvpView.showScanCards(it)
        })
    }

    }
