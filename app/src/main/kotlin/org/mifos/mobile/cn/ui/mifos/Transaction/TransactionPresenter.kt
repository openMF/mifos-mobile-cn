package org.mifos.mobile.cn.ui.mifos.Transaction

import android.content.Context
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.mifos.mobile.cn.data.datamanager.DataManagerTransaction
import org.mifos.mobile.cn.data.models.entity.Transaction
import org.mifos.mobile.cn.data.models.entity.TransactionInfo
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.ConfigPersistent
import org.mifos.mobile.cn.ui.base.BasePresenter
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import javax.inject.Inject

class TransactionPresenter @Inject
constructor(@ApplicationContext context: Context, dataManagerTransaction: DataManagerTransaction) :
        BasePresenter<TransactionContract.View>(context), TransactionContract.Presenter {
    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var dataManagerTransaction: DataManagerTransaction=dataManagerTransaction

    override fun attachView(mvpView: TransactionContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun transaction(transaction: Transaction) {
        checkViewAttached()
        getMvpView.showProgress()
        compositeDisposable.add(dataManagerTransaction.transaction(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TransactionInfo>() {
                    override fun onComplete() {
                        Log.v("H","H")
                    }

                    override fun onNext(transactionInfo: TransactionInfo) {
                        getMvpView.hideProgress()
                        getMvpView.showTransactionSuccessfully(transactionInfo)
                    }

                    override fun onError(e: Throwable) {
                        getMvpView.hideProgress()
                        getMvpView.showTransactionUnSuccessfully(ConstantKeys.ERROR_FETCHING_ACCOUNTS)
                    }
                }
                )
        )
    }
}