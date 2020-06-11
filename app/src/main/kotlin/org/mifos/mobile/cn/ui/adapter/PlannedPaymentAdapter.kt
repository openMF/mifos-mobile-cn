package org.mifos.mobile.cn.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.item_header_planned_payment.view.*
import kotlinx.android.synthetic.main.item_planned_payment.view.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.payment.CostComponent
import org.mifos.mobile.cn.data.models.payment.PlannedPayment
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.zakariya.stickyheaders.SectioningAdapter
import java.util.*
import javax.inject.Inject

class PlannedPaymentAdapter @Inject constructor(@ApplicationContext var context: Context) : SectioningAdapter() {

    private class Section {
        var remainingPrincipal: Double = 0.0
        lateinit var date: String
        var costComponents = ArrayList<CostComponent>()
    }

    class ItemViewHolder constructor(itemView: View) : SectioningAdapter.ItemViewHolder(itemView) {
        var tvChangeName: TextView = itemView.tv_charge_name
        var tvChangeValue: TextView = itemView.tv_charge_value
    }


    class HeaderViewHolder constructor(itemView: View,var context: Context) : SectioningAdapter.HeaderViewHolder(itemView){

        var tvPaymentDate: TextView = itemView.tv_payment_date
        var tvRemainingPrincipal: TextView = itemView.tv_remaining_principal
        var ivCollapse: ImageView = itemView.iv_collapse

        fun updateSectionCollapseToggle(sectionIsCollapsed: Boolean,context: Context) {
            @DrawableRes val id = if (sectionIsCollapsed)
                R.drawable.ic_arrow_drop_down_black_24dp
            else
                R.drawable.ic_arrow_drop_up_black_24dp
            ivCollapse.setImageDrawable(ContextCompat.getDrawable(context,id))
        }

    }

    private val sections = ArrayList<Section>()

    fun setPlannedPayment(plannedPayments: List<PlannedPayment>) {
        sections.clear()
        for (plannedPayment in plannedPayments) {
            val section = Section()
            section.costComponents.addAll(plannedPayment.costComponents)
            section.remainingPrincipal = plannedPayment.remainingPrincipal!!
            section.date = plannedPayment.date!!
            sections.add(section)
        }
        notifyAllSectionsDataSetChanged()
    }

    fun setMorePlannedPayment(morePlannedPayment: List<PlannedPayment>) {
        for (plannedPayment in morePlannedPayment) {
            val section = Section()
            section.costComponents.addAll(plannedPayment.costComponents)
            section.remainingPrincipal = plannedPayment.remainingPrincipal!!
            section.date = plannedPayment.date!!
            sections.add(section)
        }
        notifyAllSectionsDataSetChanged()
    }

    override fun onCreateGhostHeaderViewHolder(parent: ViewGroup): SectioningAdapter.GhostHeaderViewHolder {
        val ghostView = View(parent.context)
        ghostView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        return SectioningAdapter.GhostHeaderViewHolder(ghostView)
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return sections[sectionIndex].costComponents.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, itemType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.item_planned_payment, parent, false)
        return ItemViewHolder(v)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup?, headerType: Int): HeaderViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val v = inflater.inflate(R.layout.item_header_planned_payment, parent, false)
        return HeaderViewHolder(v,context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int,
                                      itemIndex: Int, itemType: Int) {
        val s = sections[sectionIndex]
        val ivh = viewHolder as ItemViewHolder?
        val costComponent = s.costComponents[itemIndex]
        ivh!!.tvChangeName.text = Character.toUpperCase(costComponent.chargeIdentifier!![0]) + costComponent.chargeIdentifier!!.substring(1)
        ivh.tvChangeValue.text = costComponent.amount.toString()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder?,
                                        sectionIndex: Int, headerType: Int) {
        val section = sections[sectionIndex]
        val hvh = viewHolder as HeaderViewHolder?
        if (section.date != null) {
            hvh!!.tvPaymentDate.text = DateUtils.getDate(section.date, DateUtils.INPUT_DATE_FORMAT,
                    DateUtils.OUTPUT_DATE_FORMAT)
        } else {
            hvh!!.tvPaymentDate.text = context.getString(R.string.planned_payment)
        }
        hvh.tvRemainingPrincipal.text = (context.getString(R.string.remaining_principal)
                + context.getString(R.string.colon) + section.remainingPrincipal)

        hvh.updateSectionCollapseToggle(isSectionCollapsed(sectionIndex),context)
    }

}