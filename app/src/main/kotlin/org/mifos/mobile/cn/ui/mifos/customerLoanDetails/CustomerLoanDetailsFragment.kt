package org.mifos.mobile.cn.ui.mifos.customerLoanDetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_customer_loan_details.*
import kotlinx.android.synthetic.main.fragment_loan_co_signer.*
import kotlinx.android.synthetic.main.fragment_loan_details.*
import kotlinx.android.synthetic.main.item_header_planned_payment.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.debtincomereport.DebtIncomeReportActivity
import org.mifos.mobile.cn.ui.mifos.plannedPlayment.PlannedPaymentActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.mifos.mobile.cn.ui.utils.StatusUtils
import org.mifos.mobile.cn.ui.utils.Utils
import javax.inject.Inject

class CustomerLoanDetailsFragment : MifosBaseFragment(), CustomerLoanDetailsContracts.View,View.OnClickListener {


    @Inject
    internal lateinit var customerLoanDetailsPresenter: CustomerLoanDetailsPresenter

    lateinit var rootView: View

    private lateinit var productIdentifier: String
    private lateinit var caseIdentifier: String
    private lateinit var loanAccount: LoanAccount
    private lateinit var weeksName: Array<String>
    private lateinit var repayOnMonths: Array<String>
    private lateinit var timeSlots: Array<String>
    private lateinit var monthsName: Array<String>

    companion object {
        fun newInstance(productIdentifier: String,
                        caseIdentifier: String): CustomerLoanDetailsFragment {
            val fragment = CustomerLoanDetailsFragment()
            val args = Bundle()
            args.putString(ConstantKeys.PRODUCT_IDENTIFIER, productIdentifier)
            args.putString(ConstantKeys.CASE_IDENTIFIER, caseIdentifier)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            productIdentifier = arguments!!.getString(ConstantKeys.PRODUCT_IDENTIFIER)!!
            caseIdentifier = arguments!!.getString(ConstantKeys.CASE_IDENTIFIER)!!
        }
        weeksName = activity!!.resources.getStringArray(R.array.week_names)
        repayOnMonths = activity!!.resources.getStringArray(R.array.repay_on_months)
        timeSlots = activity!!.resources.getStringArray(R.array.time_slots)
        monthsName = activity!!.resources.getStringArray(R.array.month_names)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_customer_loan_details, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        customerLoanDetailsPresenter.attachView(this)

        customerLoanDetailsPresenter.fetchCustomerLoanDetails(productIdentifier, caseIdentifier)


        return rootView
    }


    override fun showLoanAccountDetails(loanAccount: LoanAccount) {
        this.loanAccount = loanAccount
        ncv_customer_loan_details.visibility = View.VISIBLE
        setToolbarTitle(loanAccount.identifier!!)

        tv_principal_amount.text = loanAccount.getLoanParameters().maximumBalance.toString()
        val term = loanAccount.getLoanParameters().termRange!!.maximum.toString() + " " +
                loanAccount.getLoanParameters().termRange!!.temporalUnit
        tv_term.text = term

        val paymentCycle = loanAccount.getLoanParameters().paymentCycle
        val repayUnitType = LoanAccount.RepayUnitType.valueOf(paymentCycle!!.temporalUnit!!)

        when (repayUnitType) {
            LoanAccount.RepayUnitType.WEEKS -> tv_repayment_cycle.text = getString(R.string.payment_cycle_week, paymentCycle.period,
                    paymentCycle.temporalUnit!!.toLowerCase(),
                    weeksName[paymentCycle.alignmentDay!!])

            LoanAccount.RepayUnitType.MONTHS -> if (paymentCycle.alignmentWeek == null) {
                tv_repayment_cycle.text = getString(R.string.payment_cycle_month_day, paymentCycle.period,
                        paymentCycle.temporalUnit?.toLowerCase(),
                        repayOnMonths[paymentCycle.alignmentDay!!])
            } else {
                tv_repayment_cycle.text = getString(R.string.payment_cycle_month_day_week,
                        paymentCycle.period,
                        paymentCycle.temporalUnit?.toLowerCase(),
                        timeSlots[paymentCycle.alignmentWeek!!],
                        weeksName[paymentCycle.alignmentDay!!])
            }
            LoanAccount.RepayUnitType.YEARS -> if (paymentCycle.alignmentWeek == null) {
                tv_repayment_cycle.text = getString(R.string.payment_cycle_month_day, paymentCycle.period,
                        paymentCycle.temporalUnit?.toLowerCase(),
                        repayOnMonths[paymentCycle.alignmentDay!!])
            } else {
                tv_repayment_cycle.text = getString(R.string.payment_cycle_year_day_week,
                        paymentCycle.period,
                        paymentCycle.temporalUnit?.toLowerCase(),
                        timeSlots[paymentCycle.alignmentWeek!!],
                        weeksName[paymentCycle.alignmentDay!!],
                        monthsName[paymentCycle.alignmentMonth!!])
            }
        }
        tv_loan_current_status.text = loanAccount.currentState!!.name
        StatusUtils.setLoanAccountStatusIcon(loanAccount.currentState!!,
                iv_loan_current_status, this.context!!)

        if (loanAccount.accountAssignments.size !== 0) {
            tv_customer_deposit_account.text = loanAccount.accountAssignments.get(0).accountIdentifier
        } else {
            tv_customer_deposit_account.setText(R.string.no_deposit_account)
        }
        tv_create_by.text = getString(R.string.loan_created_by, loanAccount.createdBy,
                DateUtils.getDateTime(loanAccount.createdOn!!))

        tv_last_modified_by.text = getString(R.string.loan_last_modified_by,
                loanAccount.lastModifiedBy,
                DateUtils.getDateTime(loanAccount.lastModifiedOn!!))
        ll_planned_payment_details.setOnClickListener(this)
        ll_debt_income_report.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ll_planned_payment_details->
            {showPlannedPayment() }
            R.id.ll_debt_income_report -> {
                showDebtIncomeReport()
            }
            R.id.btn_try_again ->{
                ncv_customer_loan_details.visibility = View.GONE
                customerLoanDetailsPresenter.fetchCustomerLoanDetails(productIdentifier,caseIdentifier)
            }
            }
        }

    fun showPlannedPayment(){
        val intent = Intent(activity, PlannedPaymentActivity::class.java)
        intent.putExtra(ConstantKeys.PRODUCT_IDENTIFIER, productIdentifier)
        intent.putExtra(ConstantKeys.CASE_IDENTIFIER, caseIdentifier)
        startActivity(intent)
    }
    fun showDebtIncomeReport(){
        val intent = Intent(activity, DebtIncomeReportActivity::class.java)
        intent.putExtra(ConstantKeys.LOAN_CREDITWORTHINESSSNAPSHOTS, Gson().toJson(
                loanAccount.getLoanParameters().creditWorthinessSnapshots))
        startActivity(intent)
    }

    override fun showProgressbar() {
    super.showProgressBar()
    }

    override fun hideProgressbar() {
    super.hideProgressBar()
    }


    override fun showError(message: String) {
        ncv_customer_details.visibility = View.GONE
        Log.e(context.toString(), message)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (inflater != null) {
            inflater.inflate(R.menu.menu_loan_account_details, menu)
        }

        Utils.setToolbarIconColor(context!!, menu!!, R.color.white)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressbar()
        customerLoanDetailsPresenter.detachView()
    }


}