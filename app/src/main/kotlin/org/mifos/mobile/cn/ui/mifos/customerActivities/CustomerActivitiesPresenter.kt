package org.mifos.mobile.cn.ui.mifos.customerActivities

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject

@ConfigPersistent
class CustomerActivitiesPresenter @Inject constructor(@ApplicationContext context: Context,  var dataManagerCustomer: DataManagerCustomer) :
        BasePresenter<CustomerAcitivitesContracts.View>(context),CustomerAcitivitesContracts.Presenter {


    var compositeDisposable: CompositeDisposable = CompositeDisposable()



    override fun attachView(mvpView: CustomerAcitivitesContracts.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }
    override fun fetchCustomerCommands(customerIdentifier: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<List<Command>> {
            FakeRemoteDataSource.getCustomerCommandJson()
        }).subscribe({
            getMvpView.hideProgressbar()
            getMvpView.showCustomerCommands(it)
        }

        )

    }



}
