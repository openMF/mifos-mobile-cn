package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.payment.PlannedPaymentPage
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IndividualLendingService {
    @GET(EndPoints.API_PORTFOLIO_PATH
            + "/individuallending/products/{productidentifier}"
            + "/cases/{caseidentifier}/plannedpayments")
    fun getPaymentScheduleForCase(
            @Path("productidentifier") productIdentifier: String,
            @Path("caseidentifier") caseIdentifier: String,
            @Query("pageIndex") pageIndex: Int?,
            @Query("size") size: Int?,
            @Query("initialDisbursalDate") initialDisbursalDate: String): Observable<PlannedPaymentPage>
}