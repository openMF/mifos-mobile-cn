package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerDepositDetails @Inject constructor(private var baseApiManager: BaseApiManager, preferencesHelper: PreferencesHelper, dataManagerAuth: DataManagerAuth) :
        MifosBaseDataManager(dataManagerAuth, preferencesHelper) {

    fun getCustomerDepositAccountDetails(
            accountIdentifier: String): Observable<DepositAccount> {
        return authenticatedObservableApi(baseApiManager.getDepositApi()
                .fetchCustomerDepositDetails(accountIdentifier))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<DepositAccount>> { Observable.just(FakeRemoteDataSource.getCustomerDepositAccounts()[0]) })
    }
}
