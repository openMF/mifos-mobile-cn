package org.mifos.mobile.cn.data.remote

import io.reactivex.Observable
import okhttp3.ResponseBody
import org.mifos.mobile.cn.data.models.entity.RegistrationEntity
import org.mifos.mobile.cn.data.models.entity.Transaction
import org.mifos.mobile.cn.data.models.entity.TransactionInfo
import org.mifos.mobile.cn.data.models.entity.TransactionResponse
import javax.inject.Inject
import javax.inject.Singleton
/*
@Singleton
class PaymentHubRepository @Inject constructor(
        private val paymentHubApiManager: PaymentHubApiManager) {

    fun createTransaction(transaction: Transaction): Observable<TransactionInfo> =
            paymentHubApiManager.getTransactionsApi().makeTransaction(transaction)

    fun fetchTransactionInfo(transactionId: String): Observable<TransactionResponse> =
            paymentHubApiManager.getTransactionsApi().fetchTransactionInfo(transactionId)

    fun registerSecondaryIdentifier(registrationEntity: RegistrationEntity):
            Observable<ResponseBody> =
            paymentHubApiManager.getRegistrationApi().registerSecondaryIdentifier(registrationEntity)

    fun requestTransaction(transactionRequest: Transaction): Observable<TransactionInfo> =
            paymentHubApiManager.getTransactionsApi().requestTransaction(transactionRequest)

}*/