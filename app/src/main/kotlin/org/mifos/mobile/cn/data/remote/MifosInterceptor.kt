package org.mifos.mobile.cn.data.remote

import android.content.Context
import android.text.TextUtils
import androidx.annotation.NonNull
import okhttp3.Interceptor
import okhttp3.Response
import org.mifos.mobile.cn.MifosApplication
import org.mifos.mobile.cn.data.local.PreferenceKey
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.injection.ApplicationContext
import java.io.IOException
import java.util.*
import javax.inject.Inject

class MifosInterceptor @Inject constructor(@ApplicationContext context: Context) : Interceptor {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    init {
        MifosApplication.get(context).getComponent().inject(this)
    }

    companion object {
        val HEADER_ACCESS_TOKEN = "access_token"
        val HEADER_TENANT = "X-Tenant-Identifier"
        val HEADER_AUTH = "Authorization"
        val HEADER_ACCEPT_JSON = "Accept"
        val HEADER_CONTENT_TYPE = "Content-type"
        val HEADER_USER = "User"
    }

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val chainRequest = chain.request()
        val builder = chainRequest.newBuilder()

        //TODO fix call single time instead of calling every request
        val authToken: String? = preferencesHelper.getAccessToken()
        val tenantIdentifier = preferencesHelper.getTenantIdentifier()
        val user: String? = preferencesHelper.getUserName()
        val refreshTokenStatus = preferencesHelper.getBoolean(
                PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, false)

        builder.header(HEADER_ACCEPT_JSON, "application/json")
        builder.header(HEADER_CONTENT_TYPE, "application/json")

        if (refreshTokenStatus) {
            //Add Cookies
            val cookies = preferencesHelper.getStringSet(
                    PreferenceKey.PREF_KEY_COOKIES) as HashSet<String>?
            if (cookies != null) {
                for (cookie in cookies) {
                    builder.addHeader("Cookie", cookie)
                }
            }
        } else {
            if (!TextUtils.isEmpty(authToken)) {
                builder.header(HEADER_AUTH, authToken)
            }
            if (!TextUtils.isEmpty(user)) {
                builder.header(HEADER_USER, user)
            }
        }

        if (!TextUtils.isEmpty(tenantIdentifier)) {
            builder.header(HEADER_TENANT, "tn01")
        }
        /*val accessToken = preferencesHelper.accessToken

        if (!TextUtils.isEmpty(accessToken)) {
            builder.header(HEADER_ACCESS_TOKEN, preferencesHelper.accessToken)
        }*/

        val request = builder.build()
        return chain.proceed(request)
    }
}
