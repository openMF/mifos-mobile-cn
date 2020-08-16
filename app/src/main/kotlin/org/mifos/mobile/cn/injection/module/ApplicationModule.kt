package org.mifos.mobile.cn.injection.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.data.remote.BaseApiManager
import org.mifos.mobile.cn.data.remote.PaymentHubApiManager
import javax.inject.Singleton

/**
 * Provide application-level dependencies.
 */
@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @Singleton
    internal fun provideBaseApiManager(): BaseApiManager {
        return BaseApiManager(application)
    }
    @Provides
    @Singleton
    internal fun providePaymentHubApiManager(): PaymentHubApiManager {
        return PaymentHubApiManager(application)
    }
}
