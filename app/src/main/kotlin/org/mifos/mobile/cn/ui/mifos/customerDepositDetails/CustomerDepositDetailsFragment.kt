package org.mifos.mobile.cn.ui.mifos.customerDepositDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_customer_deposit_details.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.StatusUtils
import org.mifos.mobile.cn.ui.utils.Toaster
import javax.inject.Inject

class CustomerDepositDetailsFragment :
        MifosBaseFragment(), CustomerDepositDetailsContracts.View, View.OnClickListener {
    @Inject
    internal lateinit var customerDepositDetailsPresenter: CustomerDepositDetailsPresenter

    lateinit var rootView: View

    private lateinit var accountIdentifier: String
    private lateinit var depositAccount: DepositAccount

    companion object {
        fun newInstance(accountIdentifier: String): CustomerDepositDetailsFragment {
            val fragment = CustomerDepositDetailsFragment()
            val args = Bundle()
            args.putString(ConstantKeys.ACCOUNT_IDENTIFIER, accountIdentifier)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            accountIdentifier = arguments!!.getString(ConstantKeys.ACCOUNT_IDENTIFIER)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.fragment_customer_deposit_details, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        customerDepositDetailsPresenter.attachView(this)
        setToolbarTitle(getString(R.string.deposit_account))

        return rootView
    }

    override fun onResume() {
        super.onResume()
        cl_customer_deposit_details.visibility = View.GONE
        customerDepositDetailsPresenter.fetchDepositAccountDetails(accountIdentifier)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_try_again -> {
                cl_customer_deposit_details.visibility = View.GONE
                customerDepositDetailsPresenter.fetchDepositAccountDetails(accountIdentifier)
            }
        }
    }

    override fun showDepositDetails(customerDepositAccounts: DepositAccount) {
        this.depositAccount = customerDepositAccounts
        cl_customer_deposit_details.visibility = View.VISIBLE
        StatusUtils.setDepositAccountStatusIcon(depositAccount.state!!,
                iv_deposit_current_status, context!!)
        tv_deposit_current_status.text = depositAccount.state!!.name
        tv_account.text = depositAccount.accountIdentifier
        tv_balance.text = depositAccount.balance.toString()

        if (depositAccount.beneficiaries.isEmpty()) {
            tv_beneficiaries.setText(R.string.no_beneficiary)
        } else {
            tv_beneficiaries.text = depositAccount.beneficiaries.toString()
        }
    }

    override fun showProgressbar() {
        super.showProgressBar()
    }

    override fun hideProgressbar() {
        super.hideProgressBar()
    }

    override fun showError(message: String) {
        cl_customer_deposit_details.visibility = View.GONE
        Toaster.show(rootView, message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressbar()
        customerDepositDetailsPresenter.detachView()
    }
}