package org.mifos.mobile.cn.ui.mifos.debtincomereport

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_debt_income_report.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessSnapshot
import org.mifos.mobile.cn.ui.adapter.ViewPagerAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Utils

class DebtIncomeReportActivity:MifosBaseActivity() {
    private var creditWorthinessSnapshots: List<CreditWorthinessSnapshot>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debt_income_report)
        val debtIncomeString = intent.getStringExtra(
                ConstantKeys.LOAN_CREDITWORTHINESSSNAPSHOTS)
        creditWorthinessSnapshots = Utils.getStringToPoJo(
                object : TypeToken<List<CreditWorthinessSnapshot>>() {

                }, debtIncomeString)

        showBackButton()
        setToolbarTitle(getString(R.string.view_debt_income_ratio))

        setupViewPager(vp_debt_income)
        tl_debt_income.setupWithViewPager(vp_debt_income)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        when (creditWorthinessSnapshots!!.size) {
            0 -> {
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(CreditWorthinessSnapshot()),
                        getString(R.string.customer_ratio, "0.0"))
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(CreditWorthinessSnapshot()),
                        getString(R.string.co_signer_ratio, "0.0"))
            }
            1 -> {
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(creditWorthinessSnapshots!![0]),
                        getString(R.string.customer_ratio,
                                getRatio(creditWorthinessSnapshots!![0])))
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(CreditWorthinessSnapshot()),
                        getString(R.string.co_signer_ratio, "0.0"))
            }
            2 -> {
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(creditWorthinessSnapshots!![0]),
                        getString(R.string.customer_ratio,
                                getRatio(creditWorthinessSnapshots!![0])))
                adapter.addFragment(
                        DebtIncomeReportFragment.newInstance(creditWorthinessSnapshots!![1]),
                        getString(R.string.co_signer_ratio,
                                getRatio(creditWorthinessSnapshots!![1])))
            }
        }
        viewPager.adapter = adapter
    }


    private fun getRatio(creditWorthinessSnapshot: CreditWorthinessSnapshot): String {
        var amountDebt: Double? = 0.0
        var amountIncome: Double? = 0.0
        for (factor in creditWorthinessSnapshot.incomeSources) {
            amountIncome = amountIncome!! + factor.amount!!
        }
        for (factor in creditWorthinessSnapshot.debts) {
            amountDebt = amountDebt!! + factor.amount!!
        }
        return if (amountIncome != 0.0) {
            Utils.getPrecision(amountDebt!! / amountIncome!!)
        } else Utils.getPrecision(0.0)
    }

}