package org.mifos.mobile.cn.data.datamanager

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.models.payment.PlannedPaymentPage
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerIndividualLending @Inject constructor(private  var baseApiManager: BaseApiManager, preferencesHelper: PreferencesHelper, dataManagerAuth:DataManagerAuth):
        MifosBaseDataManager(dataManagerAuth,preferencesHelper) {

    fun getPaymentScheduleForCase(productIdentifier: String,
                                  caseIdentifier: String, pageIndex: Int?, size: Int?, initialDisbursalDate: String): Observable<PlannedPaymentPage> {
        return authenticatedObservableApi(baseApiManager
                .getIndividualLendingService().getPaymentScheduleForCase(
                        productIdentifier, caseIdentifier, pageIndex, size, initialDisbursalDate))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<PlannedPaymentPage>> {
                            Observable.just(
                                    FakeRemoteDataSource.getPlannedPaymentPage().value)
                        })
    }

}