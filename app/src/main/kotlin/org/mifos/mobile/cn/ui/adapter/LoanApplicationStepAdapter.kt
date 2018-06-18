package org.mifos.mobile.cn.ui.adapter

import android.support.v4.app.FragmentManager
import android.content.Context
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.mifos.loanApplication.LoanDebtIncome.LoanDebtIncomeFragment
import org.mifos.mobile.cn.ui.mifos.loanApplication.loanDetails.LoanDetailsFragment
import org.mifos.mobile.cn.ui.mifos.loanApplication.loancosigner.LoanCosignerFragment


class LoanApplicationStepAdapter(fragmentManager: FragmentManager, context: Context)
    : AbstractFragmentStepAdapter(fragmentManager, context) {

    var loanApplicationSteps: Array<String>

    init {

        loanApplicationSteps = context.resources.getStringArray(R.array.loan_application_steps)
    }

    override fun getViewModel(position: Int): StepViewModel {
        return StepViewModel.Builder(context)
                .setTitle(loanApplicationSteps[position])
                .create()
    }

    override fun createStep(position: Int): Step? {
        when (position) {
            0 -> {
                return LoanDetailsFragment.newInstance()
            }
            1 -> {
                return LoanDebtIncomeFragment.newInstance()
            }
            2 -> {
                return LoanCosignerFragment.newInstance()
            }

        }
        return null
    }

    override fun getCount(): Int {
        return loanApplicationSteps.size
    }


}

