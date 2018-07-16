package org.mifos.mobile.cn.ui.utils

import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.data.models.accounts.loan.*

/**
 * @author Manish Kumar
 * @since 25/July/2018
 */
class RxEvent {

    //Bottom sheet event (Loan Details)
    data class AddDebt(val creditWorthinessFactor: CreditWorthinessFactor)

    data class EditDebt(val creditWorthinessFactor: CreditWorthinessFactor, val position: Int)
    data class AddIncome(val creditWorthinessFactor: CreditWorthinessFactor)
    data class EditIncome(val creditWorthinessFactor: CreditWorthinessFactor, val position: Int)


    //Navigation event (Loan Details)
    data class SetLoanDetails(var currentState: LoanAccount.State, var identifier: String,
                              var productIdentifier: String, var maximumBalance: Double,
                              var paymentCycle: PaymentCycle, var termRange: TermRange)

    data class SetDebtIncome(val debtIncome: CreditWorthinessSnapshot)
    data class SetCoSignerDebtIncome(val coSignerDebtIncome: CreditWorthinessSnapshot)


    //Filter accounts events (Accounts Filter)
    data class GetCurrentFilterList(val checkboxStatus: List<CheckboxStatus>)
    data class SetStatusModelList(val statusModelList: List<CheckboxStatus>)

}