package org.mifos.mobile.cn.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.utils.DateUtils
import org.mifos.mobile.cn.ui.utils.StatusUtils
import java.util.ArrayList
import javax.inject.Inject

class CustomerActivitiesAdapter @Inject constructor(@ApplicationContext private  var context: Context) : RecyclerView.Adapter<CustomerActivitiesAdapter.ViewHolder>() {

    private var commands:List<Command> = ArrayList()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view :View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_customer_activities,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commands.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var command : Command = commands[position]

        holder.tvActivity!!.text = command.action!!.name
        holder.tvCommandChangeDateTime!!.text = context.getString(R.string.activities_created_by_on,
                command.createdBy, DateUtils.getDate(command.createdOn!!,
                DateUtils.STANDARD_DATE_TIME_FORMAT,
                DateUtils.ACTIVITIES_DATE_FORMAT))
        StatusUtils.setCustomerActivitiesStatusIcon(command.action!!, holder.ivCommandStatus!!,context)

    }
    fun setCommands(commands: List<Command>){
        this.commands = commands
        notifyDataSetChanged()
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {


         var ivCommandStatus: ImageView? = itemView.findViewById(R.id.iv_command_status)


        var tvActivity: TextView? = itemView.findViewById(R.id.tv_activity)


        var tvCommandChangeDateTime: TextView? = itemView.findViewById(R.id.tv_status_change_date_time)


    }
}