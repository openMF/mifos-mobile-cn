package org.mifos.mobile.cn.ui.mifos.loanApplication

import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor

class BottomSheetEvent {

    data class AddDebt(val creditWorthinessFactor: CreditWorthinessFactor)
    data class EditDebt(val creditWorthinessFactor: CreditWorthinessFactor, val position: Int)
    data class AddIncome(val creditWorthinessFactor: CreditWorthinessFactor)
    data class EditIncome(val creditWorthinessFactor: CreditWorthinessFactor, val position: Int)

}