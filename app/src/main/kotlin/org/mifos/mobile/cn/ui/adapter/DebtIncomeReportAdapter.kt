package org.mifos.mobile.cn.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_debt_income_report.view.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor
import java.util.ArrayList
import javax.inject.Inject

class DebtIncomeReportAdapter @Inject constructor() : RecyclerView.Adapter<DebtIncomeReportAdapter.ViewHolder>() {

    private var creditWorthinessFactors: List<CreditWorthinessFactor> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtIncomeReportAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_debt_income_report,parent,false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return creditWorthinessFactors.size
    }

    fun setCreditWorthinessFactors(creditWorthinessFactors: List<CreditWorthinessFactor>) {
        this.creditWorthinessFactors = creditWorthinessFactors
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val creditWorthinessFactor = creditWorthinessFactors[position]
        holder.tvAmount.text =creditWorthinessFactor.amount.toString()
        holder.tvDescription.text = creditWorthinessFactor.description
    }


    class ViewHolder constructor(v:View): RecyclerView.ViewHolder(v){
        var tvAmount = v.tv_amount
        var tvDescription = v.tv_description
    }

}