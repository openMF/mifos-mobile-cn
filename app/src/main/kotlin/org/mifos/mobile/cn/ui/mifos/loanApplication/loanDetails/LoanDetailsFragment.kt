package org.mifos.mobile.cn.ui.mifos.loanApplication.loanDetails

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import androidx.core.widget.NestedScrollView
import androidx.appcompat.widget.AppCompatSpinner
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.data.models.accounts.loan.PaymentCycle
import org.mifos.mobile.cn.data.models.accounts.loan.TermRange
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.loanApplication.OnNavigationBarListener

import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class LoanDetailsFragment : MifosBaseFragment(), Step, AdapterView.OnItemSelectedListener,
        View.OnClickListener, LoanDetailsContract.View, TextWatcher {

    private var products: MutableList<String>? = null
    private var repayUnitType: List<String>? = null
    private var termUnitType: MutableList<String>? = null
    private var timeSlots: List<String>? = null
    private var weeks: List<String>? = null
    private var months: List<String>? = null
    private var monthsNumber: List<String>? = null
    private lateinit var productsAdapter: ArrayAdapter<String>
    private lateinit var termUnitTypeAdapter: ArrayAdapter<String>
    private lateinit var rootView: View
    private lateinit var product: Product
    private lateinit var errorHandler: SweetUIErrorHandler

    private lateinit var ncvLoanDetails: NestedScrollView
    private lateinit var spProducts: AppCompatSpinner
    private lateinit var tilShortName: TextInputLayout
    private lateinit var etShortName: EditText
    private lateinit var etPrincipalAmount: EditText
    private lateinit var tilPrincipalAmount: TextInputLayout
    private lateinit var etTerm: EditText
    private lateinit var tilTerm: TextInputLayout
    private lateinit var spTermUnitType: AppCompatSpinner
    private lateinit var etRepay: EditText
    private lateinit var tilRepay: TextInputLayout
    private lateinit var spRepayUnitType: AppCompatSpinner
    private lateinit var llRepayUnitWeek: LinearLayout
    private lateinit var llRepayUnitYear: LinearLayout
    private lateinit var spRepayUnitTypeWeek: AppCompatSpinner
    private lateinit var llRepayUnitMonth: LinearLayout
    private lateinit var rbRepayOnDay: RadioButton
    private lateinit var rbRepayOnSpecificDay: RadioButton
    private lateinit var spRepayMonthDayInNumber: AppCompatSpinner
    private lateinit var spRepayTimeSlots: AppCompatSpinner
    private lateinit var spRepayWeekDays: AppCompatSpinner
    private lateinit var spRepayYearMonth: AppCompatSpinner
    private lateinit var layoutError: View
    private lateinit var btnRetry: Button

    companion object {
        fun newInstance(): LoanDetailsFragment {
            val fragment = LoanDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var loanDetailsPresenter: LoanDetailsPresenter

    private var onNavigationBarListner: OnNavigationBarListener.LoanDetailsData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_loan_details, container, false)
        retainInstance = true
        loanDetailsPresenter.attachView(this)
        errorHandler = SweetUIErrorHandler(activity, rootView)
        initialiseComponents(rootView)
        showUserInterface()
        loanDetailsPresenter.fetchProducts()
        return rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        product = Product()
        products = ArrayList()
        termUnitType = ArrayList()
        repayUnitType = activity!!
                .resources.getStringArray(R.array.repay_unit_type).toCollection(ArrayList())
        timeSlots = activity!!.resources.getStringArray(R.array.time_slots).toCollection(ArrayList())
        weeks = activity!!.resources.getStringArray(R.array.week_names).toCollection(ArrayList())
        months = activity!!.resources.getStringArray(R.array.month_names).toCollection(ArrayList())
        monthsNumber = activity!!
                .resources.getStringArray(R.array.repay_on_months).toCollection(ArrayList())

    }

    private fun initialiseComponents(view: View) {
        ncvLoanDetails = view.findViewById(R.id.ncv_loan_details)
        spProducts = view.findViewById(R.id.sp_products)
        tilShortName = view.findViewById(R.id.til_short_name)
        etShortName = view.findViewById(R.id.et_short_name)
        etPrincipalAmount = view.findViewById(R.id.et_principal_amount)
        tilPrincipalAmount = view.findViewById(R.id.til_principal_amount)
        etTerm = view.findViewById(R.id.et_term)
        tilTerm = view.findViewById(R.id.til_term)
        spTermUnitType = view.findViewById(R.id.sp_term_unit_type)
        etRepay = view.findViewById(R.id.et_repay)
        tilRepay = view.findViewById(R.id.til_repay)
        spRepayUnitType = view.findViewById(R.id.sp_repay_unit_type)
        llRepayUnitWeek = view.findViewById(R.id.ll_repay_unit_week)
        llRepayUnitYear = view.findViewById(R.id.ll_repay_unit_year)
        spRepayUnitTypeWeek = view.findViewById(R.id.sp_repay_unit_week)
        llRepayUnitMonth = view.findViewById(R.id.ll_repay_unit_month)
        spRepayTimeSlots = view.findViewById(R.id.sp_repay_time_slots)
        spRepayWeekDays = view.findViewById(R.id.sp_repay_week_days)
        spRepayYearMonth = view.findViewById(R.id.sp_repay_year_month)
        spRepayMonthDayInNumber = view.findViewById(R.id.sp_repay_month_day_in_number)
        rbRepayOnDay = view.findViewById(R.id.rb_repay_on_day)
        rbRepayOnSpecificDay = view.findViewById(R.id.rb_repay_on_specific_week_day)
        rbRepayOnDay.setOnClickListener(this)
        rbRepayOnSpecificDay.setOnClickListener(this)
        layoutError = view.findViewById(R.id.layout_error)
        btnRetry = view.findViewById(R.id.btn_try_again)
        btnRetry.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rb_repay_on_day -> repayOnDay()
            R.id.rb_repay_on_specific_week_day -> repayOnSpecificDay()
        }
    }

    private fun repayOnSpecificDay() {
        spRepayMonthDayInNumber.isEnabled = false
        spRepayTimeSlots.isEnabled = true
        spRepayWeekDays.isEnabled = true
    }

    private fun repayOnDay() {
        spRepayMonthDayInNumber.isEnabled = true
        spRepayTimeSlots.isEnabled = false
        spRepayWeekDays.isEnabled = false
    }

    override fun showUserInterface() {
        productsAdapter = ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, products)
        productsAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spProducts.adapter = productsAdapter
        spProducts.onItemSelectedListener = this

        val repayUnitAdapter = ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, repayUnitType)
        repayUnitAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayUnitType.adapter = repayUnitAdapter
        spRepayUnitType.onItemSelectedListener = this

        val repayUnitTypeWeekAdapter = ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, weeks)
        repayUnitTypeWeekAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayUnitTypeWeek.adapter = repayUnitTypeWeekAdapter
        spRepayUnitTypeWeek.onItemSelectedListener = this

        val repayUnitTypeMonthTimeSlotsAdapter = ArrayAdapter(activity,
                android.R.layout.simple_gallery_item, timeSlots)
        repayUnitTypeMonthTimeSlotsAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayTimeSlots.adapter = repayUnitTypeMonthTimeSlotsAdapter
        spRepayTimeSlots.onItemSelectedListener = this

        val repayUnitTypeMonthWeeksAdapter = ArrayAdapter(activity,
                android.R.layout.simple_gallery_item, weeks)
        repayUnitTypeMonthWeeksAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayWeekDays.adapter = repayUnitTypeMonthWeeksAdapter
        spRepayWeekDays.onItemSelectedListener = this

        val repayUnitTypeYearMonthsAdapter = ArrayAdapter(activity,
                android.R.layout.simple_spinner_item, months)
        repayUnitTypeYearMonthsAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayYearMonth.adapter = repayUnitTypeYearMonthsAdapter
        spRepayYearMonth.onItemSelectedListener = this

        val repayMonthDayInNumberAdapter = ArrayAdapter(activity,
                android.R.layout.simple_gallery_item, monthsNumber)
        repayMonthDayInNumberAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spRepayMonthDayInNumber.adapter = repayMonthDayInNumberAdapter
        spRepayMonthDayInNumber.onItemSelectedListener = this

        termUnitTypeAdapter = ArrayAdapter(activity,
                android.R.layout.simple_gallery_item, termUnitType)
        termUnitTypeAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item)
        spTermUnitType.adapter = termUnitTypeAdapter
        spTermUnitType.onItemSelectedListener = this

        etPrincipalAmount.addTextChangedListener(this)
        etTerm.addTextChangedListener(this)
        etRepay.addTextChangedListener(this)
        etShortName.addTextChangedListener(this)

        spRepayMonthDayInNumber.isEnabled = true
        spRepayTimeSlots.isEnabled = false
        spRepayWeekDays.isEnabled = false
        rbRepayOnDay.isChecked = true
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        when (parent.id) {
            R.id.sp_products -> {
                loanDetailsPresenter.setProductPositionAndValidateViews(position)
            }
            R.id.sp_repay_unit_type -> when (position) {
                0 -> {
                    llRepayUnitWeek.visibility = View.VISIBLE
                    llRepayUnitMonth.visibility = View.GONE
                    llRepayUnitYear.visibility = View.GONE

                }
                1 -> {
                    llRepayUnitWeek.visibility = View.GONE
                    llRepayUnitMonth.visibility = View.VISIBLE
                    llRepayUnitYear.visibility = View.GONE
                }
                2 -> {
                    llRepayUnitWeek.visibility = View.GONE
                    llRepayUnitMonth.visibility = View.GONE
                    llRepayUnitYear.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun verifyStep(): VerificationError? {

        if (!validateShortName() || !validateTerm() || !validateRepay()
                || !validatePrincipalAmount()) run { return VerificationError(null) } else {
            val paymentCycle = PaymentCycle()
            paymentCycle.period = etRepay.text.toString().trim { it <= ' ' }.toInt()
            paymentCycle.temporalUnit =
                    spRepayUnitType.selectedItem.toString().toUpperCase()
            when (spRepayUnitType.selectedItemPosition) {
                0 -> paymentCycle.alignmentDay = spRepayUnitTypeWeek.selectedItemPosition
                1 -> if (rbRepayOnDay.isChecked) {
                    paymentCycle.alignmentDay =
                            spRepayMonthDayInNumber.selectedItemPosition
                } else if (rbRepayOnSpecificDay.isChecked) {
                    paymentCycle.alignmentDay = spRepayWeekDays.selectedItemPosition
                    paymentCycle.alignmentMonth = null
                    paymentCycle.alignmentWeek = spRepayTimeSlots.selectedItemPosition
                }
                2 -> {
                    if (rbRepayOnDay.isChecked) {
                        paymentCycle.alignmentDay =
                                spRepayMonthDayInNumber.selectedItemPosition
                    } else if (rbRepayOnSpecificDay.isChecked) {
                        paymentCycle.alignmentDay = spRepayWeekDays.selectedItemPosition
                        paymentCycle.alignmentMonth = null
                        paymentCycle.alignmentWeek = spRepayTimeSlots.selectedItemPosition
                    }
                    paymentCycle.alignmentMonth = spRepayYearMonth.selectedItemPosition
                }
            }

            RxBus.publish(RxEvent.SetLoanDetails(LoanAccount.State.CREATED,
                    etShortName.text.toString().trim { it <= ' ' }, "identifer",
                    etPrincipalAmount.text.toString().trim { it <= ' ' }.toDouble(), PaymentCycle(),
                    TermRange("tempUnit", etTerm.text.toString().trim { it <= ' ' }.toDouble())
            ))

            onNavigationBarListner?.setLoanDetails(LoanAccount.State.CREATED,
                    etShortName.text.toString().trim { it <= ' ' }, "identifer",
                    etPrincipalAmount.text.toString().trim { it <= ' ' }.toDouble(), PaymentCycle(),
                    TermRange(product.termRange?.temporalUnit,etTerm.text.toString().trim{it <= ' '}.toDouble()),
                    spProducts.selectedItem.toString()
            )
            return null
        }

    }


    override fun onSelected() {

    }

    override fun onError(error: VerificationError) {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(ConstantKeys.PRODUCTS, products as ArrayList)
        outState.putStringArrayList(ConstantKeys.TERM_UNIT_TYPE, termUnitType as ArrayList)
        super.onSaveInstanceState(outState)
    }

    override fun showProducts(products: List<String>) {
        if (layoutError.visibility == View.VISIBLE) {
            errorHandler.hideSweetErrorLayoutUI(ncvLoanDetails, layoutError)
        }
        this.products?.addAll(products)
        productsAdapter.notifyDataSetChanged()
    }

    override fun setComponentsValidations(product: Product) {
        this.product = product
        etPrincipalAmount.setText(product.balanceRange?.minimum.toString())
        termUnitType?.clear()
        termUnitType?.addAll(loanDetailsPresenter.getCurrentTermUnitType(
                repayUnitType!!, product.termRange?.temporalUnit!!))
        termUnitTypeAdapter.notifyDataSetChanged()
        spTermUnitType.isEnabled = false
        etTerm.setText("1")
        etRepay.setText("1")
    }


    override fun showEmptyProducts() {
        errorHandler.showSweetEmptyUI(getString(R.string.products)
                , R.drawable.ic_sentiment_dissatisfied_black_24dp, ncvLoanDetails, layoutError)

    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }

    override fun showError(message: String) {
        if (!Network.isConnected(context!!)) {
            errorHandler.showSweetNoInternetUI(ncvLoanDetails, layoutError)
        } else {
            errorHandler.showSweetCustomErrorUI(message, R.drawable.ic_sentiment_dissatisfied_black_24dp,
                    ncvLoanDetails, layoutError)
        }
    }

    //TODO:add verification layer
    override fun validateShortName(): Boolean {
        return ValidateIdentifierUtil.isValid(activity!!,
                etShortName.text.toString().trim { it <= ' ' }, tilShortName)
    }

    override fun validateTerm(): Boolean {
        try {
            val minimum = 1.0
            val maximum = product.termRange?.maximum
            val value = java.lang.Double.parseDouble(etTerm.text.toString())

            if (etTerm.text.toString() == "") {
                ValidationUtil.showTextInputLayoutError(tilTerm, getString(R.string.required))
                return false
            } else if (minimum > value) {
                ValidationUtil.showTextInputLayoutError(tilTerm,
                        getString(R.string.value_must_greater_or_equal_to,
                                Utils.getPrecision(minimum)))
                return false
            } else if (value > maximum!!) {
                ValidationUtil.showTextInputLayoutError(tilTerm,
                        getString(R.string.value_must_less_than_or_equal_to,
                                Utils.getPrecision(maximum)))
                return false
            }
        } catch (e: NumberFormatException) {
            ValidationUtil.showTextInputLayoutError(tilTerm,
                    getString(R.string.required))
            return false
        }

        ValidationUtil.hideTextInputLayoutError(tilTerm)
        return true
    }

    override fun validatePrincipalAmount(): Boolean {
        try {
            val minimum = product.balanceRange?.minimum
            val maximum = product.balanceRange?.maximum
            val value = etPrincipalAmount.text.toString().toDouble()

            if (etPrincipalAmount.text.toString() == "") {
                ValidationUtil.isEmpty(activity!!,
                        etPrincipalAmount.text.toString().trim { it <= ' ' }, tilPrincipalAmount)
                return false
            } else if (minimum!! > value) {
                ValidationUtil.showTextInputLayoutError(tilPrincipalAmount,
                        getString(R.string.value_must_greater_or_equal_to,
                                Utils.getPrecision(minimum)))
                return false
            } else if (value > maximum!!) {
                ValidationUtil.showTextInputLayoutError(tilPrincipalAmount,
                        getString(R.string.value_must_less_than_or_equal_to,
                                Utils.getPrecision(maximum)))
                return false
            }
        } catch (e: NumberFormatException) {
            ValidationUtil.showTextInputLayoutError(tilPrincipalAmount,
                    getString(R.string.required))
            return false
        }

        ValidationUtil.hideTextInputLayoutError(tilPrincipalAmount)
        return true
    }

    override fun validateRepay(): Boolean {
        return ValidationUtil.isEmpty(activity!!,
                etRepay.text.toString().trim { it <= ' ' }, tilRepay)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (etPrincipalAmount.text.hashCode() == s?.hashCode()) {
            validatePrincipalAmount()
        } else if (etTerm.text.hashCode() == s?.hashCode()) {
            validateTerm()
        } else if (etRepay.text.hashCode() == s?.hashCode()) {
            validateRepay()
        } else if (etShortName.text.hashCode() == s?.hashCode()) {
            validateShortName()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
      if(context is OnNavigationBarListener.LoanDetailsData){onNavigationBarListner = context}
        else throw  RuntimeException("$context must implement OnNavigationBarListener.LoanDetails")
    }
}