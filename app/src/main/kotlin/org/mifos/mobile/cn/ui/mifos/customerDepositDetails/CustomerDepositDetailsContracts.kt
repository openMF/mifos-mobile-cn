package org.mifos.mobile.cn.ui.mifos.customerDepositDetails

import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.ui.base.MvpView

interface CustomerDepositDetailsContracts {
    interface View : MvpView {
        abstract fun showDepositDetails(customerDepositAccounts: DepositAccount)

        abstract fun showProgressbar()

        abstract fun hideProgressbar()

        abstract fun showError(message: String)
    }

    interface Presenter {
        fun fetchDepositAccountDetails(accountIdentifier: String)
    }
}