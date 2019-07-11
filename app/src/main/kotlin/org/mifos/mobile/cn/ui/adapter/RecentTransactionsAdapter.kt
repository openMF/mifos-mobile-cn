package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recent_transactions.view.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.customer.AccountEntry
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.mifos.mobile.cn.ui.utils.StatusUtils
import javax.inject.Inject

class RecentTransactionsAdapter @Inject constructor(@ApplicationContext val context: Context)
    :RecyclerView.Adapter<RecentTransactionsAdapter.ViewHolder>(){

    private var transactions:List<AccountEntry> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_recent_transactions,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = transactions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]


        holder.tvType.text = transaction.type

        val transactionDate = "Transaction on" + ":" + DateUtils.getDate(transaction.transactionDate!!,
              DateUtils.INPUT_DATE_FORMAT, DateUtils.OUTPUT_DATE_FORMAT)

        holder.tvTransactionDate.text = transactionDate

        holder.tvAmount.text =  transaction.amount.toString()
        holder.tvBalance.text = transaction.balance.toString()
        holder.tvMessage.text = transaction.message
    }

    fun setTransactionList(transaction: List<AccountEntry>){
        this.transactions = transaction
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvType:TextView = itemView.tv_type
        val tvTransactionDate : TextView = itemView.tv_transaction_date
        val tvMessage:TextView = itemView.tv_message
        val tvAmount:TextView = itemView.tv_amount
        val tvBalance:TextView = itemView.tv_balance
    }
}