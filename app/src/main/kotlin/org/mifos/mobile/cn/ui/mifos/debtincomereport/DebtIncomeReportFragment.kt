package org.mifos.mobile.cn.ui.mifos.debtincomereport

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_add_loan_review.*
import kotlinx.android.synthetic.main.fragment_debt_income_report.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessSnapshot
import org.mifos.mobile.cn.ui.adapter.DebtIncomeReportAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Utils
import javax.annotation.Nonnull
import javax.annotation.Nullable
import javax.inject.Inject

class DebtIncomeReportFragment: MifosBaseFragment() {

    lateinit var rootView:View

    @Inject
    lateinit var debtAdapter:DebtIncomeReportAdapter

    @Inject
    lateinit var incomeAdapter:DebtIncomeReportAdapter

    private  var creditWorthinessSnapshot: CreditWorthinessSnapshot? = null

    companion object {
        fun newInstance(snapshot: CreditWorthinessSnapshot):DebtIncomeReportFragment{
            val fragment = DebtIncomeReportFragment()
            val args = Bundle()
            args.putParcelable(ConstantKeys.LOAN_CREDITWORTHINESSSNAPSHOTS, snapshot)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            creditWorthinessSnapshot = arguments!!.getParcelable(
                    ConstantKeys.LOAN_CREDITWORTHINESSSNAPSHOTS)
        }
    }

        @Nullable
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            rootView = inflater.inflate(R.layout.fragment_debt_income_report, container, false)
            (activity as MifosBaseActivity).activityComponent.inject(this)
            return rootView

        }
    @SuppressLint("WrongConstant")
    fun showUserInterface() {
        val debtLayoutManager = LinearLayoutManager(activity)
        debtLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_debt.layoutManager = debtLayoutManager
        rv_debt.setHasFixedSize(true)
        debtAdapter.setCreditWorthinessFactors(creditWorthinessSnapshot!!.debts)
        rv_debt.adapter = debtAdapter

        val incomeLayoutManager = LinearLayoutManager(activity)
        incomeLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rvIncome.layoutManager = incomeLayoutManager
        rvIncome.setHasFixedSize(true)
        incomeAdapter.setCreditWorthinessFactors(creditWorthinessSnapshot!!.incomeSources)
        rvIncome.adapter = incomeAdapter

        tv_debt_ratio.text = getString(R.string.total_debt,
                getTotalAmount(creditWorthinessSnapshot!!.debts))
        tv_income_ratio.text = getString(R.string.total_income,
                getTotalAmount(creditWorthinessSnapshot!!.incomeSources))

        if (creditWorthinessSnapshot!!.debts.isEmpty()) {
            rvDebt.visibility = View.GONE
        }

        if (creditWorthinessSnapshot!!.incomeSources.isEmpty()) {
            rvIncome.visibility = View.GONE
        }
    }

    fun getTotalAmount(creditWorthinessFactors: List<CreditWorthinessFactor>): String {
        var amount: Double? = 0.0
        for (factor in creditWorthinessFactors) {
            amount = amount!! + factor.amount!!
        }
        return Utils.getPrecision(amount)
    }

}