package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.OnItemClickListener
import org.mifos.mobile.cn.ui.utils.DateUtils
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class IdentificationsAdapter @Inject constructor(@ApplicationContext private  var context: Context) : RecyclerView.Adapter<IdentificationsAdapter.ViewHolder>() {

    lateinit var onItemClickLister: OnItemClickListener
    init {
        identifications = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_identification,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val identification: Identification = Companion.identifications[position]
        holder.tvIdentificationType.text = identification.type
        holder.tvIdentificationIssue.text = context.getString(R.string.identification_issuer,
                identification.issuer)
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, identification.expirationDate!!.year!!)
        calendar.set(Calendar.MONTH, identification.expirationDate!!.month!! - 1)
        calendar.set(Calendar.DAY_OF_MONTH, identification.expirationDate!!.day!!)
        holder.tvExpirationDate.text = DateUtils.convertServerDate(calendar)
    }

    override fun getItemCount(): Int {
        return identifications.size
    }
    fun getItem(position: Int): Identification {
        return identifications[position]
    }

    fun setIdentifications(identifications: List<Identification>){
        Companion.identifications = identifications
        notifyDataSetChanged()
    }


    fun setOnItemClickListener(itemClickListener: OnItemClickListener){
        onItemClickLister = itemClickListener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v),View.OnClickListener,View.OnLongClickListener{


        var tvIdentificationType: TextView = itemView.findViewById(R.id.tv_identification_type)
        var tvIdentificationIssue: TextView = itemView.findViewById(R.id.tv_identification_issuer)
        var tvExpirationDate: TextView = itemView.findViewById(R.id.tv_expiration_date)
        var cvCustomer:LinearLayout = itemView.findViewById(R.id.ll_identifier_card)

        init {
            cvCustomer.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            onItemClickLister.onItemClick(v,adapterPosition)
        }
        override fun onLongClick(v: View):Boolean{
            onItemClickLister.onItemLongPress(v,adapterPosition)
            return true
        }


    }

    companion object {
        lateinit var identifications:List<Identification>
    }
}