package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.entity.Transaction
import org.mifos.mobile.cn.data.models.entity.TransactionInfo
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.data.remote.PaymentHubApiManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManagerTransaction @Inject constructor(private val paymentHubApiManager: PaymentHubApiManager) {
    fun transaction(transaction: Transaction): Observable<TransactionInfo> {
        return paymentHubApiManager.getTransactionsApi().makeTransaction(transaction)
    }
}