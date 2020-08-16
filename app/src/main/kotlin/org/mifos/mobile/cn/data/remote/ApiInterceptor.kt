package org.mifos.mobile.cn.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Request.Builder
import org.mifos.mobile.cn.data.datamanager.DataManagerCustomer
import java.io.IOException

class ApiInterceptor constructor(headerTenant: String): Interceptor  {
    val HEADER_TENANT = "Platform-TenantId"
    lateinit var headerTenant: String
    init {
        this.headerTenant=headerTenant
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        val chainRequest: Request = chain.request()
        val builder: Builder = chainRequest.newBuilder()
                .header(HEADER_TENANT,headerTenant)
        val request: Request = builder.build()
        return chain.proceed(request)
    }
}