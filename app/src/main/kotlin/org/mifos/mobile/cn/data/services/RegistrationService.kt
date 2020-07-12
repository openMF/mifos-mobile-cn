package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import org.mifos.mobile.cn.data.models.entity.RegistrationEntity
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.Body
import retrofit2.http.POST


interface RegistrationService {
    @POST(EndPoints.PARTY_REGISTRATION)
    fun registerSecondaryIdentifier(
            @Body registrationEntity: RegistrationEntity?): Observable<ResponseBody>
}