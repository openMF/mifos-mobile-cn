package org.mifos.mobile.cn.ui.mifos

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.widget.SearchView
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_client_accounts.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.adapter.ViewPagerAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsContract
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsFragment
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsPresenter
import org.mifos.mobile.cn.ui.mifos.accountsFilter.AccountsFilterBottomSheet
import org.mifos.mobile.cn.ui.mifos.customerAccounts.CustomerAccountFragment
import org.mifos.mobile.cn.ui.mifos.loanApplication.loanActivity.LoanApplicationActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.StatusUtils
import javax.inject.Inject

class Account : MifosBaseFragment() , AccountsContract.View {
    private lateinit var accountType: AccountType


    @Inject
    internal lateinit var accountsPresenter: AccountsPresenter

    companion object {
        fun newInstance(accountType: AccountType): CustomerAccountFragment {
            val fragment = CustomerAccountFragment()
            val args = Bundle()
            args.putSerializable(ConstantKeys.ACCOUNT_TYPE, accountType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (arguments != null) {
            accountType = arguments!!.getSerializable(ConstantKeys.ACCOUNT_TYPE) as AccountType
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview: View = inflater.inflate(R.layout.fragment_client_accounts,
                container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        accountsPresenter.attachView(this)
        return rootview

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPagerAndTabLayout()
        setToolbarTitle(getString(R.string.accounts))
        accountsPresenter.loadLoanAccounts()
        accountsPresenter.loadDepositAccounts()
    }

    /**
     * Returns tag of Fragment present at `position`
     * @param position position of Fragment
     * @return Tag of Fragment
     */
    private fun getFragmentTag(position: Int): String {
        return "android:switcher:" + R.id.viewpager + ":" + position
    }


    private fun setUpViewPagerAndTabLayout() {
        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.addFragment(AccountsFragment.newInstance(ConstantKeys.DEPOSIT_ACCOUNTS),
                getString(R.string.deposit))
        viewPagerAdapter.addFragment(AccountsFragment.newInstance(ConstantKeys.LOAN_ACCOUNTS),
                getString(R.string.loan))
        viewpager.adapter = viewPagerAdapter
        viewpager.offscreenPageLimit = 2
        when (accountType) {
            AccountType.DEPOSIT -> viewpager.currentItem = 0
            AccountType.LOAN -> viewpager.currentItem = 1
        }

        deposit_toggle_btn.setOnClickListener {
            viewpager.currentItem = 0
        }
        loan_toggle_btn.setOnClickListener {
            viewpager.currentItem = 1
        }

        tabs.setupWithViewPager(viewpager)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                activity?.invalidateOptionsMenu()
                (activity as MifosBaseActivity).hideKeyBoard(view!!)
            }

            override fun onPageSelected(position: Int) {}

            override fun onPageScrollStateChanged(state: Int) {}
        })


    }

    /**
     * It provides with `loanAccounts` fetched from server which is then passed to fragment
     * implementing [AccountsContract.View] i.e. [AccountsFragment] which further displays them
     * in a recyclerView
     * @param loanAccounts [List] of [LoanAccount]
     */
    override fun showLoanAccounts(loanAccounts: List<LoanAccount>) {

        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showLoanAccounts(loanAccounts)
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .hideProgress()
    }

    /**
     * It provides with `depositAccounts` fetched from server which is then passed to fragment
     * implementing [AccountsContract.View] i.e. [AccountsFragment] which further displays them
     * in a recyclerView
     * @param depositAccounts [List] of [DepositAccount]
     */

    override fun showDepositAccounts(depositAccounts: List<DepositAccount>) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showDepositAccounts(depositAccounts)
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .hideProgress()
    }


    override fun showError(message: String) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showError(getString(R.string.loan))
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showError(getString(R.string.deposit))
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyAccounts(feature: String) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showEmptyAccounts(getString(R.string.loan))
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showEmptyAccounts(getString(R.string.deposit))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_account, menu)
        if (viewpager.currentItem == 1) {
            loan_toggle_focus_btn.visibility = View.VISIBLE
            deposit_toggle_focus_btn.visibility = View.GONE
            iv_apply_for_loan.visibility = View.VISIBLE
            applyForLoan()
            menu?.findItem(R.id.menu_filter_loan)?.isVisible = true
            menu?.findItem(R.id.menu_filter_deposit)?.isVisible = false
            menu?.findItem(R.id.menu_search_loan)?.isVisible = true
            menu?.findItem(R.id.menu_search_deposit)?.isVisible = false
            initSearch(menu!!, AccountType.LOAN)
        } else if (viewpager.currentItem == 0) {
            deposit_toggle_focus_btn.visibility = View.VISIBLE
            loan_toggle_focus_btn.visibility = View.GONE
            iv_apply_for_loan.visibility = View.GONE
            menu?.findItem(R.id.menu_filter_loan)?.isVisible = false
            menu?.findItem(R.id.menu_filter_deposit)?.isVisible = true
            menu?.findItem(R.id.menu_search_deposit)?.isVisible = true
            menu?.findItem(R.id.menu_search_loan)?.isVisible = false
            initSearch(menu!!, AccountType.DEPOSIT)
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    private fun applyForLoan() {
        iv_apply_for_loan.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, LoanApplicationActivity::class.java)
            intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER, "customer_identifier")
            startActivity(intent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_filter_loan -> showFilterDialog(AccountType.LOAN)
            R.id.menu_filter_deposit -> showFilterDialog(AccountType.DEPOSIT)
        }
        return true
    }

    private fun showFilterDialog(accountType: AccountType) {
        val accountFilterBottomSheet = AccountsFilterBottomSheet()
        when (accountType) {
            AccountType.LOAN -> {
                if ((childFragmentManager.findFragmentByTag(getFragmentTag(1))
                                as AccountsFragment).currentFilterList == null) {
                    accountFilterBottomSheet.filterList = StatusUtils.getLoanAccountsStatusList(context!!)
                } else {
                    accountFilterBottomSheet.filterList = (childFragmentManager
                            .findFragmentByTag(getFragmentTag(1))
                            as AccountsFragment).currentFilterList
                }
            }

            AccountType.DEPOSIT -> {
                if ((childFragmentManager.findFragmentByTag(getFragmentTag(0))
                                as AccountsFragment).currentFilterList == null) {
                    accountFilterBottomSheet.filterList = StatusUtils.getDepositAccountsStatusList(context!!)
                } else {
                    accountFilterBottomSheet.filterList = (childFragmentManager
                            .findFragmentByTag(getFragmentTag(0))
                            as AccountsFragment).currentFilterList
                }
            }

        }

        accountFilterBottomSheet.accountType = accountType
        accountFilterBottomSheet.show(childFragmentManager, getString(R.string.filter_accounts))
    }

    /**
     * Initializes the search option in [Menu] depending upon `account`
     * @param menu Interface for managing the items in a menu.
     * @param account An enum of [AccountType]
     */
    private fun initSearch(menu: Menu, account: AccountType) {
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var search: SearchView? = null

        if (account == AccountType.LOAN) {
            search = menu.findItem(R.id.menu_search_loan).actionView as SearchView
        } else if (account == AccountType.DEPOSIT) {
            search = menu.findItem(R.id.menu_search_deposit).actionView as SearchView
        }

        search!!.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (account == AccountType.LOAN) {
                    (childFragmentManager.findFragmentByTag(
                            getFragmentTag(1)) as AccountsFragment).searchLoanAccount(newText)
                } else if (account == AccountType.DEPOSIT) {
                    (childFragmentManager.findFragmentByTag(
                            getFragmentTag(0)) as AccountsFragment).searchDepositAccount(newText)
                }

                return false
            }
        })
    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }


    override fun onResume() {
        super.onResume()
        (activity as MifosBaseActivity).hideToolbarElevation()
    }

    override fun onPause() {
        super.onPause()
        (activity as MifosBaseActivity).setToolbarElevation()
    }

}