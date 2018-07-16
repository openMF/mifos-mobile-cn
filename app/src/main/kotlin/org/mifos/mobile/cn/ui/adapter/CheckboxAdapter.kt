package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.injection.ApplicationContext
import javax.inject.Inject
import kotlinx.android.synthetic.main.row_checkbox.view.*

/**
 * @author Manish Kumar
 * @since 13/July/2018
 */

class CheckboxAdapter @Inject
constructor(@ApplicationContext var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var statusList: List<CheckboxStatus>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.row_checkbox, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val statusModel = statusList[position]

        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
        val colors = intArrayOf(statusModel.color, statusModel.color)

        (holder as ViewHolder).cbStatusSelect?.isChecked = statusModel.isChecked
        holder.cbStatusSelect?.supportButtonTintList = ColorStateList(states, colors)
        holder.tvStatus?.text = statusModel.status

    }

    override fun getItemCount(): Int {

        return statusList.size
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView?.setOnClickListener({
                cbStatusSelect!!.isChecked = !(cbStatusSelect!!.isChecked)
            })
            itemView?.cb_status_select?.setOnCheckedChangeListener { buttonView,
                                                                     isChecked ->
                statusList[adapterPosition].isChecked = cbStatusSelect!!.isChecked
            }
        }

        var cbStatusSelect = itemView?.cb_status_select
        var tvStatus = itemView?.tv_status

    }
}
