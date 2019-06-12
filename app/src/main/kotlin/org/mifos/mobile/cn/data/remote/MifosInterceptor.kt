package org.mifos.mobile.cn.data.remote

import android.content.Context
import androidx.annotation.NonNull
import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.Response
import org.mifos.mobile.cn.MifosApplication
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.injection.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class MifosInterceptor @Inject constructor(@ApplicationContext context: Context) : Interceptor {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    init {
        MifosApplication.get(context).getComponent().inject(this)
    }

    companion object {
        val HEADER_ACCESS_TOKEN = "access_token"
    }

    @Throws(IOException::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {
        val chainRequest = chain.request()
        val builder = chainRequest.newBuilder()

        val accessToken = preferencesHelper.accessToken

        if (!TextUtils.isEmpty(accessToken)) {
            builder.header(HEADER_ACCESS_TOKEN, preferencesHelper.accessToken)
        }

        val request = builder.build()
        return chain.proceed(request)
    }
}
