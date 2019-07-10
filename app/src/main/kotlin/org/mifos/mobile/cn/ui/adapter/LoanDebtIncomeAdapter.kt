package org.mifos.mobile.cn.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor
import org.mifos.mobile.cn.ui.mifos.loanApplication.CreditWorthinessSource
import java.util.ArrayList
import kotlinx.android.synthetic.main.item_loan_debt_income.view.*
import javax.inject.Inject

class LoanDebtIncomeAdapter @Inject constructor()
    : RecyclerView.Adapter<LoanDebtIncomeAdapter.ViewHolder>(){

    private var creditWorthinessFactors: List<CreditWorthinessFactor>
    internal lateinit var onClickEditDeleteListener: OnClickEditDeleteListener
    internal lateinit var creditWorthinessSource: CreditWorthinessSource
    private var isReviewAdapter: Boolean = false

    init {
        creditWorthinessFactors = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_loan_debt_income, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creditWorthinessFactor: CreditWorthinessFactor = creditWorthinessFactors[position]
        holder.tvAmount?.text = creditWorthinessFactor.amount.toString()
        holder.tvDescription?.text = creditWorthinessFactor.description
        holder.ivEdit.setOnClickListener() {
                        onClickEditDeleteListener.onClickEdit(creditWorthinessSource, position)
        }
        holder.ivDelete.setOnClickListener() {
            onClickEditDeleteListener.onClickDelete(creditWorthinessSource, position)
        }
    }

    override fun getItemCount(): Int {
        return creditWorthinessFactors.size
    }

    fun isReview(isReviewAdapter: Boolean) {
        this.isReviewAdapter = isReviewAdapter
    }

    fun setOnClickEditDeleteListener(itemClickListener: OnClickEditDeleteListener) {
        this.onClickEditDeleteListener = itemClickListener
    }

    fun setCreditWorthinessSource(creditWorthinessSource: CreditWorthinessSource) {
        this.creditWorthinessSource = creditWorthinessSource
    }

    fun setCreditWorthinessFactors(creditWorthinessFactors: List<CreditWorthinessFactor>) {
        this.creditWorthinessFactors = creditWorthinessFactors
    }

     class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         val tvAmount = view.tv_amount
         val tvDescription = view.tv_description
         val ivEdit = view.iv_edit
         val ivDelete = view.iv_delete

    }

    interface OnClickEditDeleteListener {
        fun onClickEdit(creditWorthinessSource: CreditWorthinessSource?, position: Int)
        fun onClickDelete(creditWorthinessSource: CreditWorthinessSource?, position: Int)
    }
}