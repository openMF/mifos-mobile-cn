package org.mifos.mobile.cn.ui.mifos.plannedPlayment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_planned_payment.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.payment.PlannedPayment
import org.mifos.mobile.cn.ui.adapter.PlannedPaymentAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.mifos.mobile.cn.ui.utils.Toaster
import org.zakariya.stickyheaders.PagedLoadScrollListener
import org.zakariya.stickyheaders.StickyHeaderLayoutManager
import java.util.*
import javax.inject.Inject

class PlannedPaymentFragment:MifosBaseFragment(),PlannedPaymentContract.View,SwipeRefreshLayout.OnRefreshListener,
CalendarView.OnDateChangeListener,View.OnClickListener{

    @Inject
    lateinit var plannedPaymentAdapter: PlannedPaymentAdapter

    @Inject
    lateinit var plannedPaymentPresenter: PlannedPaymentPresenter

    lateinit var rootView: View

    private lateinit var productIdentifier: String
    private lateinit var caseIdentifier: String
    private var isCalenderVisible: Boolean =false
    private var initialDisbursalDate: String? = ""
    companion object {

    fun newInstance(productIdentifier: String,
                    caseIdentifier: String): PlannedPaymentFragment {
        val fragment = PlannedPaymentFragment()
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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_planned_payment, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        plannedPaymentPresenter.attachView(this)


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUserInterface()

        fetchPlannedPayment()

    }

    override fun fetchPlannedPayment() {
        rv_planned_payment.visibility = View.GONE
        plannedPaymentPresenter.fetchPlannedPayment(productIdentifier, caseIdentifier, 0,
                initialDisbursalDate!!, false)
    }

    override fun showUserInterface() {
        if (toolbar != null) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar!!
                    .setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.title = null
        }
        val layoutManager = StickyHeaderLayoutManager()
        rv_planned_payment.layoutManager = layoutManager
        rv_planned_payment.setHasFixedSize(true)
        rv_planned_payment.adapter = plannedPaymentAdapter
        swipe_container.setOnRefreshListener(this)
        rv_planned_payment.addOnScrollListener(object : PagedLoadScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, loadComplete: PagedLoadScrollListener.LoadCompleteNotifier) {
                plannedPaymentPresenter.fetchPlannedPayment(productIdentifier, caseIdentifier, page,
                        initialDisbursalDate!!, true)
            }
        })
        calender_view_payment.setOnDateChangeListener(this)
        tv_toolbar_date.text = DateUtils.currentDate
        ll_toolbar_date.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.ll_toolbar_date ->{ showCalendar()}
                R.id.btn_try_again -> {fetchPlannedPayment()}
                R.id.btn_load_planned_payment -> {loadPlannedPaymentAccordingToDate()}

            }
        }
        }
    private fun showCalendar(){
        if (!isCalenderVisible) {
            cv_planned_payment.visibility = View.VISIBLE
            isCalenderVisible = true
            tv_toolbar_date.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_arrow_drop_up_black_24dp, 0)
        } else {
            cv_planned_payment.visibility = View.GONE
            tv_toolbar_date.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_arrow_drop_down_black_24dp, 0)
            isCalenderVisible = false
        }
    }
    fun loadPlannedPaymentAccordingToDate(){
        isCalenderVisible = false
        cv_planned_payment.visibility = View.GONE
        plannedPaymentPresenter.fetchPlannedPayment(productIdentifier, caseIdentifier, 0,
                initialDisbursalDate!!, false)
    }


    override fun showPlannedPayment(plannedPayments: List<PlannedPayment>) {
        showRecyclerView(true)
        plannedPaymentAdapter.setPlannedPayment(plannedPayments)
    }

    override fun showMorePlannedPayments(plannedPayments: List<PlannedPayment>) {
        showRecyclerView(true)
        plannedPaymentAdapter.setMorePlannedPayment(plannedPayments)
    }

    override fun showEmptyPayments(message: String) {
        showRecyclerView(false)
    }

    override fun showRecyclerView(visible: Boolean) {
        if (visible) {
            rv_planned_payment.visibility = View.VISIBLE
        } else {
            rv_planned_payment.visibility = View.GONE
        }
    }

    override fun showProgressbar() {
        swipe_container.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipe_container.isRefreshing = false
    }

    override fun showMessage(message: String) {
        Toaster.show(rootView, message)
    }

    override fun onRefresh() {
        plannedPaymentPresenter.fetchPlannedPayment(productIdentifier, caseIdentifier, 0,
                initialDisbursalDate!!, false)    }

    override fun onSelectedDayChange(view: CalendarView, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        tv_toolbar_date.text = DateUtils.getDate(DateUtils.getDateInUTC(calendar),
                DateUtils.STANDARD_DATE_TIME_FORMAT, DateUtils.OUTPUT_DATE_FORMAT)
        initialDisbursalDate = DateUtils.getDateInUTC(calendar)
    }





}