package org.mifos.mobile.cn.injection.component

import android.app.Application
import android.content.Context
import dagger.Component
import org.mifos.mobile.cn.MifosApplication
import org.mifos.mobile.cn.data.DataManagerLoanDetails
import org.mifos.mobile.cn.data.databasehelper.DataBaseHelperLoan
import org.mifos.mobile.cn.data.datamanager.*
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.data.remote.MifosInterceptor
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.injection.module.ApplicationModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    @ApplicationContext
    fun context(): Context

    fun application(): Application
    fun preferencesHelper(): PreferencesHelper

    fun dataManagerAuth(): DataManagerAuth
    fun dataManagerLoan(): DataManagerLoan
    fun databaseHelperLoan(): DataBaseHelperLoan
    fun dataManagerCustomer(): DataManagerCustomer
    fun dataManagerLoanDetails(): DataManagerLoanDetails
    fun dataManagerIndividualLending():DataManagerIndividualLending
    fun dataManagerDepositDetails():DataManagerDepositDetails


    fun inject(fineractInterceptor: MifosInterceptor)
    fun inject(fineractApplication: MifosApplication)
}
