package org.mifos.mobile.cn.ui.mifos.recentTransactions

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Predicate
import org.mifos.mobile.cn.data.models.customer.AccountEntriesPage
import org.mifos.mobile.cn.data.models.customer.AccountEntry
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import javax.inject.Inject

class RecentTransactionsPresenter @Inject constructor(@ApplicationContext context:Context)
    :BasePresenter<RecentTransactionsContracts.View>(context),RecentTransactionsContracts.Presenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getEntriesPage() {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable({
            FakeRemoteDataSource.getRecentTransactions()
        }).subscribe({
            getMvpView.hideProgressbar()
        getMvpView.showAccountEntries(it)})

    }

    override fun attachView(mvpView: RecentTransactionsContracts.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

    override fun searchTransaction(transactionList: List<AccountEntry>, type: String) {
        checkViewAttached()
        getMvpView.searchedTransaction(Observable.fromIterable(transactionList)
                .filter(object : Predicate<AccountEntry> {
                    override fun test(t: AccountEntry): Boolean {
                        return t.type.toLowerCase()
                                .contains(type.toLowerCase()).toString().toBoolean()
                    }
                }).toList().blockingGet())
    }



}
