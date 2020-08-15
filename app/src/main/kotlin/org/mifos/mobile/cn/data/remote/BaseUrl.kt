package org.mifos.mobile.cn.data.remote

import org.mifos.mobile.cn.BuildConfig

/**
 * @author Rajan Maurya
 * On 22/01/18.
 */
object BaseUrl {

    private const val PROTOCOL_HTTPS = "http://"
    private const val API_TEST_ENDPOINT = "buffalo.mifos.io"
    private const val API_PRODUCTION_ENDPOINT = "example.com"
    private const val PORT = "4200"
    // "/" in the last of the base url always

    val defaultBaseUrl: String
        get() = "$PROTOCOL_HTTPS$apiEndpoint:$PORT"

    val apiEndpoint: String
        get() = if (BuildConfig.DEBUG) {
            API_TEST_ENDPOINT
        } else {
            API_PRODUCTION_ENDPOINT
        }
}
