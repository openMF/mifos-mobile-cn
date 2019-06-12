package org.mifos.mobile.cn.ui.mifos.customerActivities

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.fragment_customer_activities.*
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.adapter.CustomerActivitiesAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import javax.annotation.Nullable
import javax.inject.Inject

class CustomerActivitiesFragment: MifosBaseFragment(),CustomerAcitivitesContracts.View, SwipeRefreshLayout.OnRefreshListener {

    lateinit var rootView: View
    lateinit var customerIdentifier: String

    @Inject
    lateinit var customerActivitiesPresenter: CustomerActivitiesPresenter

    @Inject
    lateinit var customerActivitiesAdapter: CustomerActivitiesAdapter

    companion object {
        fun newInstance(customerIdentifier: String):CustomerActivitiesFragment{
            val fragment:CustomerActivitiesFragment = CustomerActivitiesFragment()
            val args = Bundle()
            args.putString(ConstantKeys.CUSTOMER_IDENTIFIER, customerIdentifier)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        if (arguments != null){
            customerIdentifier = arguments!!.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
        }
        setToolbarTitle(getString(R.string.activities))

    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,@Nullable savedInstanceState: Bundle?): View? {
        customerActivitiesPresenter.attachView(this)
        return inflater.inflate(R.layout.fragment_customer_activities,container,false)
    }

    override fun onViewCreated(@NonNull view: View,@Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserInterface()
    }

    override fun onResume() {
        super.onResume()
        customerActivitiesPresenter.fetchCustomerCommands(customerIdentifier)
    }

    override fun onRefresh() {
        customerActivitiesPresenter.fetchCustomerCommands(customerIdentifier)
    }

    override fun showUserInterface() {
        val layoutmanager : LinearLayoutManager = LinearLayoutManager(activity)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        rv_customer_activities.layoutManager = layoutmanager
        rv_customer_activities.setHasFixedSize(true)
        rv_customer_activities.adapter = customerActivitiesAdapter
//        swipe_container.setColorSchemeColors(activity!!.resources.getIntArray(R.array.swipeRefreshColors))
        swipe_container.setOnRefreshListener(this)
    }

    override fun showCustomerCommands(commands: List<Command>) {
        showRecyclerView(true)
        customerActivitiesAdapter.setCommands(commands)
    }

    override fun showEmptyCommands(message: String) {
        showRecyclerView(false)
        Log.e(getString(R.string.activities),getString(R.string.activities))
    }

    override fun showRecyclerView(status: Boolean) {
        if (status){
            rv_customer_activities.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        } else{
            rv_customer_activities.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        }
    }

    override fun showProgressbar() {
        swipe_container.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipe_container.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        customerActivitiesPresenter.detachView()
    }

}