package org.mifos.mobile.cn.data.services

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.models.customer.CustomerPage
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.*

interface CustomerService {
    @GET(EndPoints.API_CUSTOMER_PATH + "/customers")
    abstract fun fetchCustomers(
            @Query("pageIndex") integer: Int?,
            @Query("size") size: Int?): Observable<CustomerPage>

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}")
    abstract fun fetchCustomer(@Path("identifier") identifier: String): Observable<Customer>

    @PUT(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}")
    abstract fun updateCustomer(
            @Path("identifier") identifier: String,
            @Body customer: Customer): Completable

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers")
    abstract fun searchCustomer(
            @Query("pageIndex") pageIndex: Int?,
            @Query("size") size: Int?,
            @Query("term") term: String): Observable<CustomerPage>

    @POST(EndPoints.API_CUSTOMER_PATH + "/customers")
    abstract fun createCustomer(@Body customer: Customer): Completable

    @POST(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/commands")
    abstract fun customerCommand(@Path("identifier") identifier: String, @Body command: Command): Completable

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers/{customerIdentifier}/commands")
    abstract fun fetchCustomerCommands(
            @Path("customerIdentifier") customerIdentifier: String): Observable<List<Command>>

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications")
    abstract fun fetchIdentification(
            @Path("identifier") identifier: String): Observable<List<Identification>>

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{number}")
    abstract fun searchIdentification(
            @Path("identifier") identifier: String, @Path("number") number: String): Observable<Identification>

    @POST(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications")
    abstract fun createIdentificationCard(@Path("identifier") identifier: String,
                                          @Body identification: Identification): Completable

    @PUT(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{identificationNumber}")
    abstract fun updateIdentificationCard(
            @Path("identifier") identifier: String,
            @Path("identificationNumber") identificationNumber: String,
            @Body identification: Identification): Completable

    @GET(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{identificationnumber}/scans")
    abstract fun fetchIdentificationScanCards(
            @Path("identifier") identifier: String,
            @Path("identificationnumber") identificationnumber: String): Observable<List<ScanCard>>

    @Multipart
    @POST(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{identificationnumber}/scans")
    abstract fun uploadIdentificationCardScan(
            @Path("identifier") identifier: String,
            @Path("identificationnumber") identificationnumber: String,
            @Query("scanIdentifier") scanIdentifier: String,
            @Query("description") description: String,
            @Part file: MultipartBody.Part): Completable

    @DELETE(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{identificationnumber}/scans/{scanIdentifier}")
    abstract fun deleteIdentificationCardScan(
            @Path("identifier") identifier: String,
            @Path("identificationnumber") identificationnumber: String,
            @Path("scanIdentifier") scanIdentifier: String): Completable

    @DELETE(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/identifications/{identificationnumber}")
    abstract fun deleteIdentificationCard(
            @Path("identifier") identifier: String,
            @Path("identificationnumber") identificationnumber: String): Completable

    @Multipart
    @POST(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/portrait")
    abstract fun uploadCustomerPortrait(
            @Path("identifier") customerIdentifier: String,
            @Part file: MultipartBody.Part): Completable

    @DELETE(EndPoints.API_CUSTOMER_PATH + "/customers/{identifier}/portrait")
    abstract fun deleteCustomerPortrait(@Path("identifier") customerIdentifier: String): Completable
}