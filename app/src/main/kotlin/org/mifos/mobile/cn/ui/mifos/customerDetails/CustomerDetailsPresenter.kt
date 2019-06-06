package org.mifos.mobile.cn.ui.mifos.customerDetails

import android.content.Context
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import org.mifos.mobile.cn.data.datamanager.contracts.ManagerCustomer
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject
@ConfigPersistent
class CustomerDetailsPresenter @Inject constructor(@ApplicationContext context: Context, dataManagerCustomer: DataManagerCustomer):
        BasePresenter<CustomerDetailsContract.View>(context), CustomerDetailsContract.Presenter {

    private lateinit var customer : Customer

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var dataManagerCustomer: ManagerCustomer = dataManagerCustomer

    override fun attachView(mvpView: CustomerDetailsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }


    override fun loadCustomerDetails(identifier: String) {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<Customer> {
            FakeRemoteDataSource.getCustomerJson()
        }).subscribe({customer = it
        getMvpView.hideProgressbar()
            getMvpView.showCustomerDetails(it)
        }

        )

    }
}







