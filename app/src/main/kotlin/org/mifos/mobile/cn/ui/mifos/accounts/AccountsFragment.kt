package org.mifos.mobile.cn.ui.mifos.accounts

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.therajanmaurya.sweeterror.SweetUIErrorHandler
import kotlinx.android.synthetic.main.fragment_accounts.*
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
import kotlinx.android.synthetic.main.layout_sweet_exception_handler.*

class AccountsFragment : MifosBaseFragment(), AccountsContract.View, View.OnClickListener {

    private lateinit var accountType: String
    private lateinit var loanAccounts: List<LoanAccount>
    private lateinit var depositAccounts: List<DepositAccount>


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
        (activity as MifosBaseActivity).activityComponent.inject(this)
        loanAccounts = ArrayList()
        depositAccounts = ArrayList()
        if (arguments != null) {
            accountType = arguments!!.getString(ConstantKeys.ACCOUNT_TYPE)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_accounts, container, false)
        accountsPresenter.attachView(this)

        //error handler
        errorHandler = SweetUIErrorHandler(context, rootView)
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_accounts.layoutManager = layoutManager
        rv_accounts.setHasFixedSize(true)
        rv_accounts.addItemDecoration(DividerItemDecoration(activity,
                layoutManager.orientation))
        btn_try_again.setOnClickListener(this)

        when (accountType) {
            ConstantKeys.LOAN_ACCOUNTS -> rv_accounts.adapter = loanAccountsListAdapter
            ConstantKeys.DEPOSIT_ACCOUNTS -> rv_accounts.adapter = depositAccountListAdapter

        }

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_try_again -> retry()
        }
    }

    private fun retry() {
        errorHandler.hideSweetErrorLayoutUI(rv_accounts, layout_error)
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
                errorHandler.showSweetEmptyUI(feature, R.drawable.ic_payment_black_24dp, rv_accounts,
                        layout_error)
            }

            getString(R.string.deposit) ->
                errorHandler.showSweetEmptyUI(feature, R.drawable.ic_monetization_on_black_24dp,
                        rv_accounts, layout_error)
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
            errorHandler.showSweetNoInternetUI(rv_accounts, layout_error)
        } else {
            errorHandler.showSweetErrorUI(message, rv_accounts, layout_error)
        }

    }

    override fun showProgress() {
        showProgressBar()
    }

    override fun hideProgress() {
        hideProgressBar()
    }
}