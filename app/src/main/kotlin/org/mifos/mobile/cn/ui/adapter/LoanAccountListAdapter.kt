package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.mifos.mobile.cn.ui.utils.StatusUtils
import java.util.ArrayList
import javax.inject.Inject
import kotlinx.android.synthetic.main.item_customer_loans.view.*
import org.mifos.mobile.cn.ui.base.OnItemClickListener

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

class LoanAccountListAdapter @Inject
constructor(@ApplicationContext var context: Context) : RecyclerView.Adapter<LoanAccountListAdapter.ViewHolder>() {
    private var customerLoanAccounts: List<LoanAccount>
    private lateinit var onItemClickListener: OnItemClickListener

    init {
        customerLoanAccounts = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer_loans, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val loanAccount = customerLoanAccounts[position]

        holder.tvLoanIdentifier!!.text = loanAccount.identifier

        val modifiedOn = context.getString(R.string.last_modified_on) + context.getString(
                R.string.colon) + DateUtils.getDate(loanAccount.createdOn!!,
                DateUtils.INPUT_DATE_FORMAT, DateUtils.OUTPUT_DATE_FORMAT)
        holder.tvModifiedOn!!.text = modifiedOn

        val modifiedBy = context.getString(R.string.last_modified_by) + context.getString(
                R.string.colon) + loanAccount.lastModifiedBy
        holder.tvModifiedBy!!.text = modifiedBy

        StatusUtils.setLoanAccountStatus(loanAccount.currentState!!,
                holder.ivStatusIndicator!!, context)

        holder.tvAccountBalance!!.text = "$ " +
                loanAccount.getLoanParameters().maximumBalance.toString()
    }

    override fun getItemCount(): Int {
        return customerLoanAccounts.size
    }

    fun setCustomerLoanAccounts(customerLoanAccounts: List<LoanAccount>) {
        this.customerLoanAccounts = customerLoanAccounts
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        onItemClickListener = itemClickListener
    }


    inner class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!), View.OnClickListener {
        override fun onClick(v: View?) {
            if (v != null) {
                onItemClickListener.onItemClick(v, adapterPosition)
            }
        }

        val llLoanAccount = view?.ll_loan_accounts

        init {
            llLoanAccount?.setOnClickListener(this)
        }

        val tvLoanIdentifier = view?.tv_loan_identifier

        val ivStatusIndicator = view?.iv_status_indicator

        val tvAccountBalance = view?.tv_account_balance

        val tvModifiedBy = view?.tv_modified_by

        val tvModifiedOn = view?.tv_modified_on


    }

}