package org.mifos.mobile.cn.data

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.mifos.mobile.cn.data.datamanager.DataManagerAuth
import org.mifos.mobile.cn.data.datamanager.MifosBaseDataManager
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerLoanDetails @Inject constructor(private  var baseApiManager: BaseApiManager, preferencesHelper: PreferencesHelper, dataManagerAuth: DataManagerAuth):
        MifosBaseDataManager(dataManagerAuth,preferencesHelper){

    fun fetchCustomerLoanDetails(
            productIdentifier: String, caseIdentifier: String): Observable<LoanAccount>{
        return authenticatedObservableApi(baseApiManager.getLoanApi()
                .fetchCustomerLoanDetails(productIdentifier, caseIdentifier))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<LoanAccount>> { Observable.just(FakeRemoteDataSource.getLoanAccountsJson()[0])})
    }
    }


