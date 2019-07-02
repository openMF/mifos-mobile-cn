package org.mifos.mobile.cn.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.mifos.mobile.cn.data.services.AnonymousService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import org.mifos.mobile.cn.data.services.AuthService
import org.mifos.mobile.cn.data.services.CustomerService

class BaseApiManager constructor(context: Context) {

    private lateinit var retrofit: Retrofit
    private lateinit var anonymousRetrofit: Retrofit
    private lateinit var authApi: AuthService
    private lateinit var anonymousService: AnonymousService
    private lateinit var customerApi: CustomerService


    init {
        createService(context)
        createAnonymousService()
    }

    private fun init() {
        authApi = createApi(AuthService::class.java)
        customerApi = createApi(CustomerService::class.java)
    }

    private fun initAnonymous() {
        anonymousService = anonymousRetrofit.create(AnonymousService::class.java)
    }

    private fun <T> createApi(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }

    private fun createService(context: Context) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl.defaultBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(MifosOkHttpClient(context).telescopeOkHttpClient)
                .build()
        init()
    }

    private fun createAnonymousService() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        anonymousRetrofit = Retrofit.Builder()
                .baseUrl(BaseUrl.defaultBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
        initAnonymous()
    }

    fun getAuthApi(): AuthService {
        return authApi
    }

    fun getAnonymousService(): AnonymousService {
        return anonymousService
    }

    fun getCustomerApi(): CustomerService {
        return customerApi
    }
}
