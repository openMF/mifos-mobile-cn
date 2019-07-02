package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Function
import okhttp3.MultipartBody
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard

import org.mifos.mobile.cn.data.datamanager.contracts.ManagerCustomer
import org.mifos.mobile.cn.data.local.DatabaseHelperCustomer
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.data.datamanager.MifosBaseDataManager
import org.mifos.mobile.cn.data.local.PreferencesHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerCustomer @Inject constructor(private var baseApiManager: BaseApiManager, private  var preferencesHelper: PreferencesHelper, dataManagerAuth: DataManagerAuth, private  var databaseHelper: DatabaseHelperCustomer)
    : ManagerCustomer, MifosBaseDataManager(dataManagerAuth, preferencesHelper) {


    override fun fetchCustomer(identifier: String): Observable<Customer> {
        return authenticatedObservableApi(baseApiManager.getCustomerApi()
                .fetchCustomer(identifier))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<Customer>>()
                        {Observable.just(FakeRemoteDataSource.getCustomerJson()) })
    }
    override fun updateCustomer(customerIdentifier: String, customer: Customer?): Completable? {
       return null
    }



    override fun customerCommand(identifier: String, command: Command): Completable? {
        return null
    }

    override fun fetchCustomerCommands(customerIdentifier: String): Observable<List<Command>> {
        return authenticatedObservableApi(baseApiManager.getCustomerApi()
                .fetchCustomerCommands(customerIdentifier))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<List<Command>>> { Observable.just(FakeRemoteDataSource.getCustomerCommandJson()) })
    }

    override fun fetchIdentifications(customerIdentifier: String): Observable<List<Identification>> {
        return authenticatedObservableApi(baseApiManager.getCustomerApi()
                .fetchIdentification(customerIdentifier))
                .onErrorResumeNext(Function<Throwable, ObservableSource<List<Identification>>> { Observable.just(FakeRemoteDataSource.getIdentificationsJson()) })
    }

    override fun createIdentificationCard(identifier: String, identification: Identification): Completable? {
      return null
    }

    override fun updateIdentificationCard(customerIdentifier: String, identificationNumber: String, identification: Identification?): Completable? {
       return null
    }

    override fun fetchIdentificationScanCards(customerIdentifier: String,
                                     identificationNumber: String): Observable<List<ScanCard>> {
        return authenticatedObservableApi(baseApiManager.getCustomerApi()
                .fetchIdentificationScanCards(customerIdentifier, identificationNumber))
                .onErrorResumeNext(
                        Function<Throwable, ObservableSource<List<ScanCard>>> { Observable.just(FakeRemoteDataSource.getScanCards()) })
    }

    override fun uploadIdentificationCardScan(customerIdentifier: String, identificationNumber: String, scanIdentifier: String, description: String, file: MultipartBody.Part): Completable? {
     return null
    }

    override fun deleteIdentificationCardScan(customerIdentifier: String, identificationNumber: String, scanIdentifier: String): Completable? {
       return null
    }

    override fun deleteIdentificationCard(customerIdentifier: String, identificationnumber: String): Completable? {
        return null
    }

    override fun uploadCustomerPortrait(customerIdentifier: String, file: MultipartBody.Part): Completable? {
       return null
    }

    override fun deleteCustomerPortrait(customerIdentifier: String): Completable? {
     return null
    }
}