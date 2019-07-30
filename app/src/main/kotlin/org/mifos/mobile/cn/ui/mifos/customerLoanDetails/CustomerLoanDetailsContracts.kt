package org.mifos.mobile.cn.ui.mifos.customerLoanDetails

import androidx.lifecycle.LiveData
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.base.MvpView

interface CustomerLoanDetailsContracts {
    interface View : MvpView {

        fun showLoanAccountDetails(loanAccount: LoanAccount)

        fun showProgressbar()

        fun hideProgressbar()

        fun showError(message: String)
    }

    interface Presenter {

        fun fetchCustomerLoanDetails(productIdentifier: String, caseIdentifier: String)
    }

}