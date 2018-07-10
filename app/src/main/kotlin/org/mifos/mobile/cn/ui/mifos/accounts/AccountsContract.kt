package org.mifos.mobile.cn.ui.mifos.accounts

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.base.MvpView

interface AccountsContract {

    interface View : MvpView {

        fun showLoanAccounts(loanAccounts: List<LoanAccount>)
        fun showDepositAccounts(depositAccounts: List<DepositAccount>)
        fun showEmptyAccounts(feature: String)
        fun showError(message: String)
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter {

        //TODO: edit this while setting up fakeDB
        fun loadLoanAccounts()
        fun loadDepositAccounts()

    }
}