package org.mifos.mobile.cn.ui.mifos.customerDetails

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import org.mifos.mobile.cn.data.datamanager.contracts.ManagerCustomer
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
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
        compositeDisposable.add(dataManagerCustomer.fetchCustomer(identifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Customer>() {
                    override fun onNext(customer: Customer) {
                        getMvpView.hideProgressbar()
                        getMvpView.showCustomerDetails(customer)
                    }

                    override fun onError(throwable: Throwable) {
                        getMvpView.hideProgressbar()
                        showExceptionError(throwable,
                                context.getString(R.string.error_fetching_customer_details))
                    }

                    override fun onComplete() {

                    }
                })
        )


    }
}







