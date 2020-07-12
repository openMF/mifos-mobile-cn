package org.mifos.mobile.cn.injection.component


import dagger.Subcomponent
import org.mifos.mobile.cn.injection.PerActivity
import org.mifos.mobile.cn.injection.module.ActivityModule
import org.mifos.mobile.cn.ui.mifos.Account
import org.mifos.mobile.cn.ui.mifos.DashboardActivity
import org.mifos.mobile.cn.ui.mifos.Transaction.TransactionActivity
import org.mifos.mobile.cn.ui.mifos.aboutus.AboutUsFragment
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsFragment
import org.mifos.mobile.cn.ui.mifos.accountsFilter.AccountsFilterBottomSheet
import org.mifos.mobile.cn.ui.mifos.customerAccounts.CustomerAccountFragment
import org.mifos.mobile.cn.ui.mifos.customerActivities.CustomerActivitiesFragment
import org.mifos.mobile.cn.ui.mifos.customerDepositDetails.CustomerDepositDetailsFragment
import org.mifos.mobile.cn.ui.mifos.customerDetails.CustomerDetailsFragment
import org.mifos.mobile.cn.ui.mifos.identificationdetails.IdentificationDetailsFragment
import org.mifos.mobile.cn.ui.mifos.identificationlist.IdentificationsFragment
import org.mifos.mobile.cn.ui.mifos.launcher.LauncherActivity
import org.mifos.mobile.cn.ui.mifos.loanApplication.BaseFragmentDebtIncome
import org.mifos.mobile.cn.ui.mifos.loanApplication.loanActivity.LoanApplicationActivity
import org.mifos.mobile.cn.ui.mifos.loanApplication.loanDetails.LoanDetailsFragment
import org.mifos.mobile.cn.ui.mifos.customerLoanDetails.CustomerLoanDetailsFragment
import org.mifos.mobile.cn.ui.mifos.debtincomereport.DebtIncomeReportFragment
import org.mifos.mobile.cn.ui.mifos.login.LoginActivity
import org.mifos.mobile.cn.ui.mifos.passcode.PasscodeActivity
import org.mifos.mobile.cn.ui.mifos.plannedPlayment.PlannedPaymentFragment
import org.mifos.mobile.cn.ui.mifos.products.ProductFragment
import org.mifos.mobile.cn.ui.review.AddLoanReviewFragment
import org.mifos.mobile.cn.ui.mifos.recentTransactions.RecentTransactionsFragment


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(loginActivity: LoginActivity)

    fun inject(transactionActivity: TransactionActivity)

    fun inject(passcodeActivity: PasscodeActivity)

    fun inject(account :Account)

    fun inject(launcherActivity: LauncherActivity)

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(loanApplicationActivity: LoanApplicationActivity)

    fun inject(loanDebtIncomeFragment: BaseFragmentDebtIncome)

    fun inject(loanDetailsFragment: LoanDetailsFragment)

    fun inject(customerAccountFragment: CustomerAccountFragment)

    fun inject(accountsFragment: AccountsFragment)

    fun inject(accountsFilterBottomSheet: AccountsFilterBottomSheet)

    fun inject(customerDetailsFragment: CustomerDetailsFragment)

    fun inject(customerActivitiesFragment: CustomerActivitiesFragment)

    fun inject(identificationsFragment: IdentificationsFragment)

    fun inject(identificationDetailsFragment: IdentificationDetailsFragment)

    fun inject(productFragment: ProductFragment)

    fun inject(addLoanReviewFragment: AddLoanReviewFragment)

    fun inject(recentTransactionsFragment: RecentTransactionsFragment)

    fun inject(customerLoanDetailsFragment: CustomerLoanDetailsFragment)

    fun inject(plannedPaymentFragment: PlannedPaymentFragment)

    fun inject(debtIncomeReportFragment: DebtIncomeReportFragment)

    fun inject(customerDepositDetailsFragment: CustomerDepositDetailsFragment)

    fun inject(aboutUsFragment: AboutUsFragment)
}
