package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.entity.PartyIdentifiers
import retrofit2.http.GET
import retrofit2.http.Path
import org.mifos.mobile.cn.data.remote.EndPoints.ACCOUNTS
import org.mifos.mobile.cn.data.remote.EndPoints.INTEROPERATION
interface FineractPaymentHubService {
    @GET("$INTEROPERATION/$ACCOUNTS/{accountExternalId}/identifiers")
    fun fetchSecondaryIdentifiers(@Path("accountExternalId") accountExternalId: String):
            Observable<PartyIdentifiers>
}