package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.injection.ApplicationContext
import javax.inject.Inject

class IdentificationScanAdapter @Inject constructor(@ApplicationContext private  var context: Context): RecyclerView.Adapter<IdentificationScanAdapter.ViewHolder>() {
    lateinit var  onItemClickListener: OnItemClickListener
companion object {
    lateinit var  scanCards : List<ScanCard>
}
    init {
        scanCards = ArrayList<ScanCard>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_identification_scan_card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scanCard : ScanCard = scanCards[position]
        holder.tvIdentifier.text = scanCard.identifier
        holder.tvDescription.text = scanCard.description

    }
    override fun getItemCount(): Int {
        return  scanCards.size
    }
    fun setScanCards(scanCards: List<ScanCard>){
       Companion.scanCards = scanCards
        notifyDataSetChanged()
    }

    fun setonItemClickListener(itemClickListener:OnItemClickListener){
        onItemClickListener = itemClickListener
    }

    fun getItem(position: Int): ScanCard {
        return scanCards[position]
    }



    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v),View.OnClickListener{

        var tvIdentifier: TextView = itemView.findViewById(R.id.tv_identifier)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_description_scan_card)
        var llIdentificationCard: LinearLayout = itemView.findViewById(R.id.ll_identifier_card)

        init {
            llIdentificationCard.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            when(v.id){
                R.id.ll_identifier_card ->
                    onItemClickListener.onItemClick(v,position)
            }

        }


    }
    interface OnItemClickListener{
        fun onItemClick(view: View, position:Int)


    }

}