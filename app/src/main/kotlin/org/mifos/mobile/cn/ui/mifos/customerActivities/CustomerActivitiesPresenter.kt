package org.mifos.mobile.cn.ui.mifos.customerActivities

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
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
        compositeDisposable.add(dataManagerCustomer.fetchCustomerCommands(customerIdentifier)?.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Command>>() {
                    override fun onNext(commands: List<Command>) {
                        getMvpView.hideProgressbar()
                        if (!commands.isEmpty()) {
                            getMvpView.showCustomerCommands(commands)
                        } else {
                            getMvpView.showEmptyCommands(
                                    context.getString(R.string.empty_customer_activities))
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        getMvpView.hideProgressbar()
                        showExceptionError(throwable,
                                context.getString(R.string.error_fetching_customer_activities))
                    }

                    override fun onComplete() {

                    }
                })
        )
    }


}
