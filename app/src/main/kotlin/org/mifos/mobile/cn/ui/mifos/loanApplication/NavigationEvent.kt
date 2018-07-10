package org.mifos.mobile.cn.ui.mifos.loanApplication

import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessSnapshot
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.data.models.accounts.loan.PaymentCycle
import org.mifos.mobile.cn.data.models.accounts.loan.TermRange

class NavigationEvent {

    data class SetLoanDetails(var currentState: LoanAccount.State, var identifier: String,
                              var productIdentifier: String, var maximumBalance: Double,
                              var paymentCycle: PaymentCycle, var termRange: TermRange)

    data class SetDebtIncome(val debtIncome: CreditWorthinessSnapshot)

    data class SetCoSignerDebtIncome(val coSignerDebtIncome: CreditWorthinessSnapshot)

}