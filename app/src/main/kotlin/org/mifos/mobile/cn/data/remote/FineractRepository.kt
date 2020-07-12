package org.mifos.mobile.cn.data.remote

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.entity.PartyIdentifiers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FineractRepository @Inject constructor(fineractApiManager: BaseApiManager) {
    private val fineractApiManager: BaseApiManager
    // for Payment-Hub related API calls
    fun getSecondaryIdentifiers(accountExternalId: String): Observable<PartyIdentifiers> {
        return fineractApiManager.getFineractPaymentHubApi()
                .fetchSecondaryIdentifiers(accountExternalId)
    }

    init {
        this.fineractApiManager = fineractApiManager
    }
}