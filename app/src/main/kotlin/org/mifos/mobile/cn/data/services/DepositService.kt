package org.mifos.mobile.cn.data.services

import io.reactivex.Completable
import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.*

interface DepositService {

    @GET(EndPoints.API_DEPOSIT_PATH + "/instances")
    abstract fun fetchCustomersDeposits(
            @Query("customer") customerIdentifier: String): Observable<List<DepositAccount>>

    @GET(EndPoints.API_DEPOSIT_PATH + "/instances/{accountIdentifier}")
    abstract fun fetchCustomerDepositDetails(
            @Path("accountIdentifier") accountIdentifier: String): Observable<DepositAccount>

    @POST(EndPoints.API_DEPOSIT_PATH + "/instances")
    abstract fun createDepositAccount(@Body depositAccount: DepositAccount): Completable

    @PUT(EndPoints.API_DEPOSIT_PATH + "/instances/{accountIdentifier}")
    abstract fun updateDepositAccount(
            @Path("accountIdentifier") accountIdentifier: String,
            @Body depositAccount: DepositAccount): Completable
}