package org.mifos.mobile.cn.ui.mifos.loanApplication.loanActivity

import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.base.MvpView

/**
 * @author Manish Kumar
 * @since 01/August/2018
 */
class LoanApplicationContract {

    interface View : MvpView {
        fun applicationCreatedSuccessfully()
        fun showError(message: String)
    }

    interface Presenter {
        fun createLoan(loanAccount: LoanAccount)
    }

}