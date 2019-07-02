package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.Authentication
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.POST

/**
 * @author Rajan Maurya
 * On 22/01/18.
 */
interface AuthService {

    /*@GET("/authentication")
    fun login(loginRequest: LoginRequest): Observable<LoginResponse>*/
    @POST(EndPoints.API_IDENTITY_PATH + "/token?grant_type=refresh_token")
    abstract fun refreshToken(): Observable<Authentication>
}