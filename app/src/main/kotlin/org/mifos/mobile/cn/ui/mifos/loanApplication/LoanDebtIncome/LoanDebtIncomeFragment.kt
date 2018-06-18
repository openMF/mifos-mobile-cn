package org.mifos.mobile.cn.ui.mifos.loanApplication.LoanDebtIncome

import android.os.Bundle
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.loanApplication.BaseFragmentDebtIncome

class LoanDebtIncomeFragment : BaseFragmentDebtIncome(), Step {

    companion object {
        fun newInstance():LoanDebtIncomeFragment {
            val fragment = LoanDebtIncomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_loan_debt_income_ratio

    override fun onSelected() {

    }

    override fun verifyStep(): VerificationError? {
        return null

    }

    override fun onError(error: VerificationError) {

    }
}