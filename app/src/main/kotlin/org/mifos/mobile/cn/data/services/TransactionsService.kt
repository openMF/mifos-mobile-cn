package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.entity.Transaction
import org.mifos.mobile.cn.data.models.entity.TransactionInfo
import org.mifos.mobile.cn.data.models.entity.TransactionResponse
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface TransactionsService {
    @POST(EndPoints.TRANSFER)
    fun makeTransaction(@Body transaction: Transaction?): Observable<TransactionInfo>

    @GET(EndPoints.TRANSFER.toString() + "/{transactionId}")
    fun fetchTransactionInfo(
            @Path("transactionId") transactionId: String?): Observable<TransactionResponse>

    @POST(EndPoints.TRANSACTION_REQUEST)
    fun requestTransaction(@Body transactionRequest: Transaction?): Observable<TransactionInfo>
}