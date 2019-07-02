package org.mifos.mobile.cn.ui.mifos.identificationlist

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Predicate
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.R
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


    lateinit var  compositeDisposable : CompositeDisposable
    lateinit var dataManagerCustomer : DataManagerCustomer

    init {
        this.dataManagerCustomer = dataManagerCustomer
        compositeDisposable = CompositeDisposable()
    }

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
        compositeDisposable.add(dataManagerCustomer.fetchIdentifications(customerIdentifier)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Identification>>() {
                    override fun onNext(identifications: List<Identification>) {
                        getMvpView.hideProgressbar()

                        if (identifications.size == 0) {
                            getMvpView.showEmptyIdentifications()
                        } else {
                            getMvpView.showIdentification(identifications)
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        getMvpView.hideProgressbar()
                        showExceptionError(throwable,
                                context.getString(R.string.error_fetching_identification_list))
                    }

                    override fun onComplete() {

                    }
                })
        )
    }


    override fun searchIdentifications(identificationList: List<Identification>, query: String) {
        checkViewAttached()
        getMvpView.searchedIdentifications(Observable.fromIterable<Identification>(identificationList)
                .filter { identification -> identification.type?.toLowerCase()!!.contains(query.toLowerCase()) }.toList().blockingGet())

        }
    }


