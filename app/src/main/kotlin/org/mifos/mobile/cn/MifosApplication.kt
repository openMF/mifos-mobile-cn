package org.mifos.mobile.cn

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho
import com.mifos.mobile.passcode.utils.ForegroundChecker
import org.mifos.mobile.cn.injection.component.ApplicationComponent
import org.mifos.mobile.cn.injection.component.DaggerApplicationComponent
import org.mifos.mobile.cn.injection.module.ApplicationModule

/**
 * @author Rajan Maurya
 * On 22/01/18.
 */
class MifosApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    companion object {

        lateinit var fineractApplication: MifosApplication

        fun get(context: Context): MifosApplication {
            return context.applicationContext as MifosApplication
        }

        fun getContext(context: Context): Context {
            return context.applicationContext
        }

        fun getContext(): Context {
            return fineractApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        fineractApplication = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // Initializing the Dagger component
        initializeComponent()
        applicationComponent.inject(this)

        // Initialize the Stetho
        Stetho.initializeWithDefaults(this)
        ForegroundChecker.init(this)

    }

    fun getComponent(): ApplicationComponent {
        return applicationComponent
    }

    private fun initializeComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
