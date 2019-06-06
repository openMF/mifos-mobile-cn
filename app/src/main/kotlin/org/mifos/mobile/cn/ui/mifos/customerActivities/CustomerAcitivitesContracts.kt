package org.mifos.mobile.cn.ui.mifos.customerActivities

import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.ui.base.MvpView

interface CustomerAcitivitesContracts {
    interface View : MvpView {

        fun showUserInterface()

        fun showCustomerCommands(commands: List<Command>)

        fun showEmptyCommands(message: String)

        fun showRecyclerView(status: Boolean)

        fun showProgressbar()

        fun hideProgressbar()
    }
    interface Presenter{
        fun fetchCustomerCommands(customerIdentifier: String)
    }
}
