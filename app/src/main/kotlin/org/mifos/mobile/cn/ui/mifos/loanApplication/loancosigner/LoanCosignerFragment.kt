package org.mifos.mobile.cn.ui.mifos.loanApplication.loancosigner

import android.content.Context
import android.os.Bundle
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.loanApplication.BaseFragmentDebtIncome
import org.mifos.mobile.cn.ui.mifos.loanApplication.OnNavigationBarListener
import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.RxEvent

class LoanCosignerFragment : BaseFragmentDebtIncome(), Step {

    private var onNavigationBarListener :OnNavigationBarListener.LoanCoSignerData? = null

    companion object {
        fun newInstance(): LoanCosignerFragment {
            val fragment = LoanCosignerFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override val fragmentLayout: Int
        get() = R.layout.fragment_loan_co_signer


    override fun onSelected() {

    }

    override fun verifyStep(): VerificationError? {
        RxBus.publish(RxEvent.SetCoSignerDebtIncome(creditWorthinessSnapshot))
        onNavigationBarListener!!.setCoSignerDebtIncome(creditWorthinessSnapshot)
        return null
    }

    override fun onError(error: VerificationError) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnNavigationBarListener.LoanCoSignerData){onNavigationBarListener = context}
        else throw  RuntimeException("$context must implement OnNavigationBarListener.LoanCoSignerData")
    }
}