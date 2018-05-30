package org.mifos.mobile.cn.injection.component


import dagger.Subcomponent
import org.mifos.mobile.cn.injection.PerActivity
import org.mifos.mobile.cn.injection.module.ActivityModule


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {


}
