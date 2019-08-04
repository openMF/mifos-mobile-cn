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
import org.mifos.mobile.cn.ui.base.OnItemClickListener
import org.mifos.mobile.cn.ui.utils.StatusUtils

/**
 * @author Manish Kumar
 * @since 10/July/2018
 */

class DepositAccountListAdapter @Inject
constructor(@ApplicationContext val context: Context) :
        RecyclerView.Adapter<DepositAccountListAdapter.ViewHolder>() {
    private var customerDepositAccounts: List<DepositAccount>
    private lateinit var onItemClickListener: OnItemClickListener


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
    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        onItemClickListener = itemClickListener
    }


    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!), View.OnClickListener{
        override fun onClick(v: View?) {
            if (v != null) {
                onItemClickListener.onItemClick(v,adapterPosition)
            }
        }
        private val llDepositAccount= view?.ll_deposit_accounts
        init {
            llDepositAccount?.setOnClickListener(this)
        }

        val ivStatusIndicator = view?.iv_status_indicator

        val tvCustomerAccountIdentifier = view?.tv_customer_account_identifier

        val tvDepositProduct = view?.tv_deposit_product

        val tvAccountBalance = view?.tv_account_balance

    }
}