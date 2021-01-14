package org.mifos.mobile.cn.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import org.mifos.mobile.cn.exceptions.NoConnectivityException
import org.mifos.mobile.cn.ui.utils.Network
import java.io.IOException

/**
 * @author Rajan Maurya
 * On 23/09/17.
 */
class ConnectivityInterceptor(private val context: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Network.isConnected(context)) {
            throw NoConnectivityException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}