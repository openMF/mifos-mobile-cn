package org.mifos.mobile.cn.injection.module

import android.app.Activity
import android.content.Context
import dagger.Module
import dagger.Provides
import org.mifos.mobile.cn.injection.ActivityContext
import org.mifos.mobile.cn.injection.PerActivity


@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    @ActivityContext
    internal fun providesContext(): Context {
        return activity
    }
}
