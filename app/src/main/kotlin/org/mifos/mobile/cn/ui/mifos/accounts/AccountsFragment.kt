package org.mifos.mobile.cn.ui.mifos.accounts

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.ui.adapter.DepositAccountListAdapter
import org.mifos.mobile.cn.ui.adapter.LoanAccountListAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Network
import javax.inject.Inject

class AccountsFragment : MifosBaseFragment(), AccountsContract.View, View.OnClickListener {

    private lateinit var accountType: String
    private lateinit var rvAccounts: RecyclerView
    private lateinit var loanAccounts: List<LoanAccount>
    private lateinit var depositAccounts: List<DepositAccount>
    private lateinit var layoutError: View
    private lateinit var btnTryAgain: Button

    @Inject
    lateinit var loanAccountsListAdapter: LoanAccountListAdapter

    @Inject
    lateinit var depositAccountListAdapter: DepositAccountListAdapter


    private lateinit var errorHandler: SweetUIErrorHandler

    val LOG_TAG = AccountsFragment::class.java.simpleName

    @Inject
    internal lateinit var accountsPresenter: AccountsPresenter

    companion object {

        fun newInstance(accountType: String): AccountsFragment {
            val fragment = AccountsFragment()
            val args = Bundle()
            args.putString(ConstantKeys.ACCOUNT_TYPE, accountType)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loanAccounts = ArrayList()
        depositAccounts = ArrayList()
        if (arguments != null) {
            accountType = arguments!!.getString(ConstantKeys.ACCOUNT_TYPE)
        }
        (activity as MifosBaseActivity).activityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_accounts, container, false)
        accountsPresenter.attachView(this)
        setUpViews(rootView)

        //error handler
        errorHandler = SweetUIErrorHandler(context, rootView)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvAccounts.layoutManager = layoutManager
        rvAccounts.setHasFixedSize(true)
        rvAccounts.addItemDecoration(DividerItemDecoration(activity,
                layoutManager.orientation))
        return rootView

    }

    private fun setUpViews(view: View) {
        rvAccounts = view.findViewById(R.id.rv_accounts)
        layoutError = view.findViewById(R.id.layout_error)
        btnTryAgain = view.findViewById(R.id.btn_try_again)
        btnTryAgain.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_try_again -> retry()
        }
    }

    private fun retry() {
        errorHandler.hideSweetErrorLayoutUI(rvAccounts, layoutError)
        when (accountType) {
            ConstantKeys.LOAN_ACCOUNTS -> accountsPresenter.loadLoanAccounts()
            ConstantKeys.DEPOSIT_ACCOUNTS -> accountsPresenter.loadDepositAccounts()
        }
    }

    /**
     * Shows [List] of [LoanAccount] fetched from server using
     * [LoanAccountListAdapter]
     * @param loanAccounts [List] of [LoanAccount]
     */
    override fun showLoanAccounts(loanAccounts: List<LoanAccount>) {
        this.loanAccounts = loanAccounts
        if (loanAccounts.isNotEmpty()) {
            loanAccountsListAdapter.setCustomerLoanAccounts(loanAccounts)
        } else {
            showEmptyAccounts(getString(R.string.loan))
        }
    }

    override fun showEmptyAccounts(feature: String) {
        when (feature) {
            getString(R.string.loan) -> {
                errorHandler.showSweetEmptyUI(feature, R.drawable.ic_payment_black_24dp, rvAccounts,
                        layoutError)
            }

            getString(R.string.deposit) ->
                errorHandler.showSweetEmptyUI(feature, R.drawable.ic_monetization_on_black_24dp,
                        rvAccounts, layoutError)
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

    override fun showError(message: String) {
        if (!Network.isConnected(context!!)) {
            errorHandler.showSweetNoInternetUI(rvAccounts, layoutError)
        } else {
            errorHandler.showSweetErrorUI(message, rvAccounts, layoutError)
        }

    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }
}