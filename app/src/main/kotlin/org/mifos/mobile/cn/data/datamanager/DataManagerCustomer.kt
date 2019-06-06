package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard

import org.mifos.mobile.cn.data.datamanager.contracts.ManagerCustomer
import org.mifos.mobile.cn.data.local.DatabaseHelperCustomer
import javax.inject.Inject


class DataManagerCustomer @Inject constructor(databaseHelperCustomer: DatabaseHelperCustomer) : ManagerCustomer  {
    var databaseHelperCustomer: DatabaseHelperCustomer = databaseHelperCustomer


    override fun fetchCustomer(identifier: String): Observable<Customer> {
       return databaseHelperCustomer.fetchCustomer(identifier)

    }

    override fun updateCustomer(customerIdentifier: String, customer: Customer?): Completable? {
       return null
    }



    override fun customerCommand(identifier: String, command: Command): Completable? {
        return null
    }

    override fun fetchCustomerCommands(customerIdentifier: String): Observable<MutableList<Command>>? {
       return null
    }

    override fun fetchIdentifications(customerIdentifier: String): Observable<MutableList<Identification>>? {
      return null
    }

    override fun createIdentificationCard(identifier: String, identification: Identification): Completable? {
      return null
    }

    override fun updateIdentificationCard(customerIdentifier: String, identificationNumber: String, identification: Identification?): Completable? {
       return null
    }

    override fun fetchIdentificationScanCards(customerIdentifier: String, identificationNumber: String): Observable<MutableList<ScanCard>>? {
        return null
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