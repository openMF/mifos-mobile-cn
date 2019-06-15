package org.mifos.mobile.cn.ui.mifos.loanApplication

import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessSnapshot
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.data.models.accounts.loan.PaymentCycle
import org.mifos.mobile.cn.data.models.accounts.loan.TermRange

interface OnNavigationBarListener {
    interface LoanDetailsData {
        fun setLoanDetails(currentState: LoanAccount.State, identifier: String,
                           productIdentifier: String, maximumBalance: Double?, paymentCycle: PaymentCycle,
                           termRange: TermRange, selectedProduct: String)
    }

    interface ReviewLoan {
        val loanAccount: LoanAccount
        val selectedProduct: String
        val creditWorthinessSnapshot: List<CreditWorthinessSnapshot>
    }

    interface LoanDebtIncomeData {
        fun setDebtIncome(debtIncome: CreditWorthinessSnapshot)
    }

    interface LoanCoSignerData {
        fun setCoSignerDebtIncome(coSignerDebtIncome: CreditWorthinessSnapshot)

        fun showProgressbar(message: String)

        fun hideProgressbar()
    }
}