package org.mifos.mobile.cn.data.services

import io.reactivex.Observable
import org.mifos.mobile.cn.data.models.Authentication
import org.mifos.mobile.cn.data.remote.EndPoints
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * @author Rajan Maurya
 * On 22/01/18.
 */
interface AuthService {

    /*@GET("/authentication")
    fun login(loginRequest: LoginRequest): Observable<LoginResponse>*/
    //@Headers("X-Tenant-Identifier: tn01")
    @POST(EndPoints.API_IDENTITY_PATH + "/token?grant_type=password")
    abstract fun login(
            @Query("username") username: String,
            @Query("password") password: String): Observable<Authentication>

    @POST(EndPoints.API_IDENTITY_PATH + "/token?grant_type=refresh_token")
    abstract fun refreshToken(): Observable<Authentication>
}