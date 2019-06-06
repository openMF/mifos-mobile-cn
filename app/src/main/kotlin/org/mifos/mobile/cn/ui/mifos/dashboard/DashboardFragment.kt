package org.mifos.mobile.cn.ui.mifos.dashboard

import android.content.Intent


import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.customerAccounts.CustomerAccountFragment
import org.mifos.mobile.cn.ui.mifos.customerDetails.CustomerDetailsActivity
import org.mifos.mobile.cn.ui.mifos.loanApplication.loanActivity.LoanApplicationActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys


class DashboardFragment : MifosBaseFragment(), View.OnClickListener {

    private lateinit var rootView: View
    private lateinit var customer: Customer
    companion object {

        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        setToolbarTitle(getString(R.string.home))
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_apply_for_loan.setOnClickListener(this)
        ll_accounts.setOnClickListener(this)
        ll_account_overview.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.ll_apply_for_loan -> {
                applyForLoan()
            }
            R.id.ll_accounts -> {
                openAccount()
            }
            R.id.ll_account_overview -> {
                showCustomerDetails()
            }
        }
    }

    private fun applyForLoan() {
        val intent = Intent(activity, LoanApplicationActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER, "customer_identifier")
        startActivity(intent)
    }

    private fun openAccount() {
        (activity as MifosBaseActivity)
                .replaceFragment(CustomerAccountFragment.newInstance(AccountType.LOAN),
                true, R.id.container)
    }
    private fun showCustomerDetails(){
        val intent = Intent(activity,CustomerDetailsActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER,"customer_identifier")
       startActivity(intent)


    }
}