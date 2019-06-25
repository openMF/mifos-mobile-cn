package org.mifos.mobile.cn.ui.mifos.loanApplication

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessSnapshot
import org.mifos.mobile.cn.ui.adapter.LoanDebtIncomeAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.RxEvent
import java.util.*
import javax.inject.Inject

abstract class BaseFragmentDebtIncome : MifosBaseFragment(),
        LoanDebtIncomeAdapter.OnClickEditDeleteListener, View.OnClickListener {


    @Inject
    lateinit var debtAdapter: LoanDebtIncomeAdapter

    @Inject
    lateinit var incomeAdapter: LoanDebtIncomeAdapter

    lateinit var rootView: View

    private var totalDebts: Double? = 0.0
    private var totalIncome: Double? = 0.0
    private var ratio: Double? = 00.00

    private lateinit var debtCreditWorthinessFactors: MutableList<CreditWorthinessFactor>
    private lateinit var incomeCreditWorthinessFactors: MutableList<CreditWorthinessFactor>

    private lateinit var rvDebt: RecyclerView
    private lateinit var rvIncome: RecyclerView
    private lateinit var tvDebtIncomeRatio: TextView
    private lateinit var tvTotalDebt: TextView
    private lateinit var tvTotalIncome: TextView
    private lateinit var tvEmptyDebtList: TextView
    private lateinit var tvEmptyIncomeList: TextView
    private lateinit var btnAddDebt : Button
    private lateinit var btnAddIncome : Button

    /**
     * Every fragment has to inflate a layout in the onCreateView method. We have added this method
     * to
     * avoid duplicate all the inflate code in every fragment. You only have to return the layout
     * to
     * inflate in this method when extends BaseFragment.
     */
    protected abstract val fragmentLayout: Int

    val creditWorthinessSnapshot: CreditWorthinessSnapshot
        get() {
            val creditWorthinessSnapshot = CreditWorthinessSnapshot()
            creditWorthinessSnapshot.incomeSources = incomeCreditWorthinessFactors
            creditWorthinessSnapshot.debts = debtCreditWorthinessFactors
            return creditWorthinessSnapshot
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debtCreditWorthinessFactors = mutableListOf()
        incomeCreditWorthinessFactors = mutableListOf()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(fragmentLayout, container, false)
        initialiseId(rootView)

        (activity as MifosBaseActivity).activityComponent.inject(this)
        showUserInterface()

        RxBus.listen(RxEvent.AddDebt::class.java).subscribe({
            addBottomSheetDebt(it.creditWorthinessFactor)
        })

        RxBus.listen(RxEvent.AddIncome::class.java).subscribe({
            addBottomSheetIncome(it.creditWorthinessFactor)
        })

        RxBus.listen(RxEvent.EditDebt::class.java).subscribe({
            editBottomSheetDebt(it.creditWorthinessFactor, it.position)
        })
        RxBus.listen(RxEvent.EditIncome::class.java).subscribe({
            editBottomSheetIncome(it.creditWorthinessFactor, it.position)
        })

        return rootView
    }

    private fun initialiseId(view: View) {
        rvDebt = view.findViewById(R.id.rv_debt)
        rvIncome = view.findViewById(R.id.rv_income)
        tvDebtIncomeRatio = view.findViewById(R.id.tv_debt_income_ratio)
        tvTotalDebt = view.findViewById(R.id.tv_total_debt)
        tvTotalIncome = view.findViewById(R.id.tv_total_income)
        tvEmptyDebtList = view.findViewById(R.id.tv_empty_debt_list)
        tvEmptyIncomeList = view.findViewById(R.id.tv_empty_income_list)
        btnAddDebt = view.findViewById(R.id.btn_add_debt)
        btnAddDebt.setOnClickListener(this)
        btnAddIncome = view.findViewById(R.id.btn_add_income)
        btnAddIncome.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_add_debt -> addDebt()
            R.id.btn_add_income -> addIncome()
        }
    }


    private fun addDebt() {
        showDebtIncomeBottomSheet(CreditWorthinessSource.DEBT, null, null)
    }

    private fun addIncome() {
        showDebtIncomeBottomSheet(CreditWorthinessSource.INCOME, null, null)
    }

    private fun showUserInterface() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvDebt.layoutManager = layoutManager
        rvDebt.setHasFixedSize(true)
        debtAdapter.setCreditWorthinessSource(CreditWorthinessSource.DEBT)
        debtAdapter.setOnClickEditDeleteListener(this)
        debtAdapter.setCreditWorthinessFactors(debtCreditWorthinessFactors)
        rvDebt.adapter = debtAdapter

        val layoutManagerIncome = LinearLayoutManager(activity)
        layoutManagerIncome.orientation = LinearLayoutManager.VERTICAL
        rvIncome.layoutManager = layoutManagerIncome
        rvIncome.setHasFixedSize(true)
        incomeAdapter.setCreditWorthinessSource(CreditWorthinessSource.INCOME)
        incomeAdapter.setOnClickEditDeleteListener(this)
        incomeAdapter.setCreditWorthinessFactors(incomeCreditWorthinessFactors)
        rvIncome.adapter = incomeAdapter

        tvTotalDebt.text = getString(R.string.total_debt, setPrecision(totalDebts))
        tvTotalIncome.text = getString(R.string.total_income, setPrecision(totalIncome))
        tvDebtIncomeRatio.text = getString(R.string.ratio, setPrecision(ratio))
    }

    fun addBottomSheetDebt(creditWorthinessFactor: CreditWorthinessFactor) {
        if (debtAdapter.itemCount == 0) {
            rvDebt.visibility = View.VISIBLE
            tvEmptyDebtList.visibility = View.GONE
        }
        debtCreditWorthinessFactors.add(creditWorthinessFactor)
        debtAdapter.notifyDataSetChanged()
        updateDebtsAndRatio()
    }

    fun editBottomSheetDebt(creditWorthinessFactor: CreditWorthinessFactor, position: Int) {
        debtCreditWorthinessFactors[position] = creditWorthinessFactor
        debtAdapter.notifyDataSetChanged()
        updateDebtsAndRatio()
    }

    fun addBottomSheetIncome(creditWorthinessFactor: CreditWorthinessFactor) {
        if (incomeAdapter.itemCount == 0) {
            rvIncome.visibility = View.VISIBLE
            tvEmptyIncomeList.visibility = View.GONE
        }
        incomeCreditWorthinessFactors.add(creditWorthinessFactor)
        incomeAdapter.notifyDataSetChanged()
        updateIncomeAndRatio()
    }

    fun editBottomSheetIncome(creditWorthinessFactor: CreditWorthinessFactor, position: Int) {
        incomeCreditWorthinessFactors[position] = creditWorthinessFactor
        incomeAdapter.notifyDataSetChanged()
        updateIncomeAndRatio()
    }


    override fun onClickEdit(creditWorthinessSource: CreditWorthinessSource?, position: Int) {
        when (creditWorthinessSource) {
            CreditWorthinessSource.DEBT -> showDebtIncomeBottomSheet(CreditWorthinessSource.EDIT_DEBT,
                    debtCreditWorthinessFactors[position], position)
            CreditWorthinessSource.INCOME -> showDebtIncomeBottomSheet(CreditWorthinessSource.EDIT_INCOME,
                    incomeCreditWorthinessFactors[position], position)
        }
    }

    override fun onClickDelete(creditWorthinessSource: CreditWorthinessSource?, position: Int) {
        when (creditWorthinessSource) {
            CreditWorthinessSource.DEBT -> {
                debtCreditWorthinessFactors.removeAt(position)
                debtAdapter.notifyDataSetChanged()
                if (debtAdapter.itemCount == 0) {
                    tvEmptyDebtList.visibility = View.VISIBLE
                    rvDebt.visibility = View.GONE
                }
                updateDebtsAndRatio()
            }
            CreditWorthinessSource.INCOME -> {
                incomeCreditWorthinessFactors.removeAt(position)
                incomeAdapter.notifyDataSetChanged()
                if (incomeAdapter.itemCount == 0) {
                    tvEmptyIncomeList.visibility = View.VISIBLE
                    rvIncome.visibility = View.GONE
                }
                updateIncomeAndRatio()
            }
        }
    }


    private fun showDebtIncomeBottomSheet(creditWorthinessSource: CreditWorthinessSource,
                                          creditWorthinessFactor: CreditWorthinessFactor?, position: Int?) {
        val addDebtIncomeBottomSheet = AddDebtIncomeBottomSheet()
        addDebtIncomeBottomSheet.setCreditWorthinessSource(creditWorthinessSource)
        when (creditWorthinessSource) {
            CreditWorthinessSource.EDIT_DEBT -> {
                addDebtIncomeBottomSheet.setCreditWorthinessFactor(creditWorthinessFactor!!)
                addDebtIncomeBottomSheet.setPosition(position!!)
            }
            CreditWorthinessSource.EDIT_INCOME -> {
                addDebtIncomeBottomSheet.setCreditWorthinessFactor(creditWorthinessFactor!!)
                addDebtIncomeBottomSheet.setPosition(position!!)

            }
        }
        addDebtIncomeBottomSheet.show(childFragmentManager, getString(R.string.debt_income))
    }

    private fun showTotalDebts() {
        totalDebts = 0.0
        for (creditWorthinessFactor in debtCreditWorthinessFactors) {
            totalDebts = creditWorthinessFactor.amount?.plus(totalDebts!!)
        }
        tvTotalDebt.text = getString(R.string.total_debt, setPrecision(totalDebts))
    }

    private fun showTotalIncome() {
        totalIncome = 0.0
        for (creditWorthinessFactor in incomeCreditWorthinessFactors) {
            totalIncome = creditWorthinessFactor.amount?.plus(totalIncome!!)
        }
        tvTotalIncome.text = getString(R.string.total_income, setPrecision(totalIncome))
    }

    private fun showRatio() {
        if (totalIncome != 0.0) {
            ratio = totalDebts!! / totalIncome!!
            tvDebtIncomeRatio.text = getString(R.string.ratio, setPrecision(ratio))
        } else {
            tvDebtIncomeRatio.text = getString(R.string.ratio, setPrecision(0.0))
        }
    }

    private fun updateDebtsAndRatio() {
        showTotalDebts()
        showRatio()
    }

    private fun updateIncomeAndRatio() {
        showTotalIncome()
        showRatio()
    }

    private fun setPrecision(aDouble: Double?): String {
        return String.format(Locale.ENGLISH, "%.2f", aDouble)
    }

}
