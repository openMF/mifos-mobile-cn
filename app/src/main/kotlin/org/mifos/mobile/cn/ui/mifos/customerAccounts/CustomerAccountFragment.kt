package org.mifos.mobile.cn.ui.mifos.customerAccounts

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import javax.inject.Inject


class CustomerAccountFragment : MifosBaseFragment(), AccountsContract.View {


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
        viewPagerAdapter.addFragment(AccountsFragment.newInstance(ConstantKeys.LOAN_ACCOUNTS),
                getString(R.string.loan))
        viewPagerAdapter.addFragment(AccountsFragment.newInstance(ConstantKeys.DEPOSIT_ACCOUNTS),
                getString(R.string.deposit))
        viewpager.adapter = viewPagerAdapter
        viewpager.offscreenPageLimit = 2
        when (accountType) {
            AccountType.LOAN -> viewpager.currentItem = 0
            AccountType.DEPOSIT -> viewpager.currentItem = 1

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

        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showLoanAccounts(loanAccounts)
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .hideProgress()
    }

    /**
     * It provides with `depositAccounts` fetched from server which is then passed to fragment
     * implementing [AccountsContract.View] i.e. [AccountsFragment] which further displays them
     * in a recyclerView
     * @param depositAccounts [List] of [DepositAccount]
     */

    override fun showDepositAccounts(depositAccounts: List<DepositAccount>) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showDepositAccounts(depositAccounts)
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .hideProgress()
    }


    override fun showError(message: String) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showError(getString(R.string.loan))
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showError(getString(R.string.deposit))
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyAccounts(feature: String) {
        (childFragmentManager.findFragmentByTag(getFragmentTag(0)) as AccountsContract.View)
                .showEmptyAccounts(getString(R.string.loan))
        (childFragmentManager.findFragmentByTag(getFragmentTag(1)) as AccountsContract.View)
                .showEmptyAccounts(getString(R.string.deposit))
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