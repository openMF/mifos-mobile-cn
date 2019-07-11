package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.injection.ApplicationContext
import java.util.ArrayList
import javax.inject.Inject
import kotlinx.android.synthetic.main.item_customer_deposit_accounts.view.*
import org.mifos.mobile.cn.ui.utils.StatusUtils

/**
 * @author Manish Kumar
 * @since 10/July/2018
 */

class DepositAccountListAdapter @Inject
constructor(@ApplicationContext val context: Context) :
        RecyclerView.Adapter<DepositAccountListAdapter.ViewHolder>() {
    private var customerDepositAccounts: List<DepositAccount>


    init {
        customerDepositAccounts = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer_deposit_accounts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customerDepositAccount = customerDepositAccounts[position]

        holder.tvCustomerAccountIdentifier!!.text = customerDepositAccount.accountIdentifier
        holder.tvDepositProduct!!.text = customerDepositAccount.productIdentifier
        holder.tvAccountBalance!!.text = customerDepositAccount.balance.toString()

        StatusUtils.setDepositAccountStatus(customerDepositAccount.state!!,holder.ivStatusIndicator!!,
                context)
    }

    override fun getItemCount(): Int {
        return customerDepositAccounts.size
    }

    fun setCustomerDepositAccounts(customerDepositAccounts: List<DepositAccount>) {
        this.customerDepositAccounts = customerDepositAccounts
        notifyDataSetChanged()
    }


    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {

        val ivStatusIndicator = view?.iv_status_indicator

        val tvCustomerAccountIdentifier = view?.tv_customer_account_identifier

        val tvDepositProduct = view?.tv_deposit_product

        val tvAccountBalance = view?.tv_account_balance

        val llDepositAccounts = view?.ll_deposit_accounts

    }
}