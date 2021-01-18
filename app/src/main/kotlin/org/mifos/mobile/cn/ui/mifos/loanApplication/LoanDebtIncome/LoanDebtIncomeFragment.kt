package org.mifos.mobile.cn.ui.mifos.loanApplication.LoanDebtIncome

import android.content.Context
import android.os.Bundle
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.loanApplication.BaseFragmentDebtIncome
import org.mifos.mobile.cn.ui.mifos.loanApplication.OnNavigationBarListener
import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.RxEvent

class LoanDebtIncomeFragment : BaseFragmentDebtIncome(), Step {

    private var onNavigationBarListner: OnNavigationBarListener.LoanDebtIncomeData? = null

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
        RxBus.publish(RxEvent.SetDebtIncome(creditWorthinessSnapshot))
        onNavigationBarListner!!.setDebtIncome(creditWorthinessSnapshot)
        return null

    }

    override fun onError(error: VerificationError) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNavigationBarListener.LoanDebtIncomeData){
            onNavigationBarListner = context
        } else throw  RuntimeException("$context must implement OnNavigationBarListener.DebtIncome")
    }
}