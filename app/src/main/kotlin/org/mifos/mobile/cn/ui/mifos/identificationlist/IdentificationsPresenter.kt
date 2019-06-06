package org.mifos.mobile.cn.ui.mifos.identificationlist

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject

@ConfigPersistent
class IdentificationsPresenter @Inject constructor(@ApplicationContext context:Context,dataManagerCustomer: DataManagerCustomer):
        BasePresenter<IdentificationsContract.Views>(context),IdentificationsContract.Presenter {

    val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun attachView(mvpView: IdentificationsContract.Views) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun fetchIdentifications(customerIdentifier: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<List<Identification>> {
            FakeRemoteDataSource.getIdentificationsJson()
        }).subscribe({
            getMvpView.hideProgressbar()
            getMvpView.showIdentification(it)
        })
    }


    override fun searchIdentifications(identifier: String, number: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<Identification> {
            FakeRemoteDataSource.getIdentificationsJson().get(0)
        }).subscribe({
            getMvpView.hideProgressbar()
            getMvpView.searchIdentificationList(it)
        })

        }
    }


