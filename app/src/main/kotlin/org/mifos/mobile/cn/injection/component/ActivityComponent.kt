package org.mifos.mobile.cn.injection.component


import dagger.Subcomponent
import org.mifos.mobile.cn.injection.PerActivity
import org.mifos.mobile.cn.injection.module.ActivityModule
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import org.mifos.mobile.cn.ui.mifos.launcher.LauncherActivity
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject (loginActivity: LoginActivity)

    fun inject (passcodeActivity: PasscodeActivity)

    fun inject (launcherActivity: LauncherActivity)

    fun inject (dashboardActivity: DashboardActivity)
}
