package org.mifos.mobile.cn.ui.mifos.loanApplication.loancosigner

import android.os.Bundle
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.loanApplication.BaseFragmentDebtIncome

class LoanCosignerFragment : BaseFragmentDebtIncome(), Step {


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
        return null
    }

    override fun onError(error: VerificationError) {

    }
}