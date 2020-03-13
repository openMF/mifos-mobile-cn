package org.mifos.mobile.cn.ui.mifos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_accounts.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.adapter.DepositAccountListAdapter
import org.mifos.mobile.cn.ui.adapter.LoanAccountListAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.base.OnItemClickListener
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsContract
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsFragment
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsPresenter
import org.mifos.mobile.cn.ui.mifos.customerDepositDetails.CustomerDepositDetailsFragment
import org.mifos.mobile.cn.ui.mifos.customerLoanDetails.CustomerLoanDetailsFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import javax.inject.Inject

class Account : MifosBaseFragment() , View.OnClickListener, OnItemClickListener, AccountsContract.View {
    private lateinit var accountType: String
    private lateinit var loanAccounts: List<LoanAccount>
    private lateinit var depositAccounts: List<DepositAccount>
    var currentFilterList: List<CheckboxStatus>? = null

    @Inject
    internal lateinit var accountsPresenter: AccountsPresenter

    @Inject
    lateinit var loanAccountsListAdapter: LoanAccountListAdapter

    @Inject
    lateinit var depositAccountListAdapter: DepositAccountListAdapter
    companion object {
        fun newInstance(accountType: String): Account {
            val fragment = Account()
            val args = Bundle()
            args.putString(ConstantKeys.ACCOUNT_TYPE, accountType)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        getToolbar().setVisibility(View.GONE);
        val rootview: View = inflater.inflate(R.layout.fragment_account,
                container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        accountsPresenter.attachView(this)
        return rootview
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        loanAccounts = ArrayList()
        depositAccounts = ArrayList()
        if (arguments != null) {
            accountType = arguments!!.getString(ConstantKeys.ACCOUNT_TYPE)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        deposit_r.layoutManager = layoutManager
        deposit_r.setHasFixedSize(true)
        deposit_r.addItemDecoration(DividerItemDecoration(activity,
                layoutManager.orientation))
        val layoutManager1 = LinearLayoutManager(activity)
        layoutManager1.orientation = LinearLayoutManager.VERTICAL
        loan_r.layoutManager = layoutManager1
        loan_r.setHasFixedSize(true)
        loan_r.addItemDecoration(DividerItemDecoration(activity,
                layoutManager.orientation))
        btn_deposit.setOnClickListener(this)
        btn_loan.setOnClickListener(this)
        accountsPresenter.loadLoanAccounts()
        accountsPresenter.loadDepositAccounts()
        when (accountType) {
            ConstantKeys.LOAN_ACCOUNTS -> {
                loan_r.adapter = loanAccountsListAdapter
                loanAccountsListAdapter.setOnItemClickListener(this)
            }
            ConstantKeys.DEPOSIT_ACCOUNTS -> {
                deposit_r.adapter = depositAccountListAdapter
                depositAccountListAdapter.setOnItemClickListener(this)
            }
        }
    }

    override fun onClick(view: View) {
        when(view.id)
        {
            R.id.btn_deposit -> {
                deposit()
            }
            R.id.btn_loan -> {
                loan()
            }
        }

    }

    private fun loan() {
        btn_loan.isSelected = true
        btn_loan.isFocusable = true
        btn_loan.setChipBackgroundColorResource(R.color.white)
        btn_deposit.isSelected = false
        btn_deposit.setChipBackgroundColorResource(R.color.light_grey)
        loan_r.visibility = View.VISIBLE
        loan_r.setHasFixedSize(true)
        deposit_r.visibility = View.GONE
        loan_r.adapter = loanAccountsListAdapter
        loanAccountsListAdapter.setOnItemClickListener(this)
        accountType="loanAccounts"
    }

    private fun deposit() {
        btn_deposit.isSelected = true
        btn_deposit.isFocusable = true
        btn_deposit.setChipBackgroundColorResource(R.color.white)
        btn_loan.isSelected = false
        btn_loan.setChipBackgroundColorResource(R.color.light_grey)
        deposit_r.visibility = View.VISIBLE
        deposit_r.setHasFixedSize(true)
        loan_r.visibility = View.GONE
        accountType="deposit_accounts"
    }

    override fun onItemClick(childView: View, position: Int) {
        when (accountType) {
            ConstantKeys.LOAN_ACCOUNTS -> {
                (activity as MifosBaseActivity).replaceFragment(
                        CustomerLoanDetailsFragment.newInstance(
                                loanAccounts[position].productIdentifier!!,
                                loanAccounts[position].identifier!!), true, R.id.bottom_navigation_fragment_container)
            }
            ConstantKeys.DEPOSIT_ACCOUNTS -> {
                (activity as MifosBaseActivity).replaceFragment(
                        CustomerDepositDetailsFragment.newInstance(
                                depositAccounts[position].accountIdentifier!!),true,R.id.bottom_navigation_fragment_container
                )
            }
        }
    }

    override fun onItemLongPress(childView: View, position: Int) {
        TODO("Not yet implemented")
    }

    override fun showLoanAccounts(loanAccounts: List<LoanAccount>) {
        this.loanAccounts = loanAccounts
        if (loanAccounts.isNotEmpty()) {
            loanAccountsListAdapter.setCustomerLoanAccounts(loanAccounts)
        } else {
            showEmptyAccounts(getString(R.string.loan))
        }
    }
    override fun showDepositAccounts(depositAccounts: List<DepositAccount>) {
        this.depositAccounts = depositAccounts
        if (depositAccounts.isNotEmpty()) {
            depositAccountListAdapter.setCustomerDepositAccounts(depositAccounts)
        } else {
            showEmptyAccounts(getString(R.string.deposit))
        }

    }

    override fun showEmptyAccounts(feature: String) {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showProgress() {

    }

    override fun hideProgress() {
        hideProgressBar()
    }
}