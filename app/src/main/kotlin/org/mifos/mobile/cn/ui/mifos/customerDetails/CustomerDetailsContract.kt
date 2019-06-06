package org.mifos.mobile.cn.ui.mifos.customerDetails


import org.mifos.mobile.cn.data.models.customer.ContactDetail
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.ui.base.MvpView

interface CustomerDetailsContract {

    interface View: MvpView{

        fun showUserInterface()

        fun showCustomerDetails(customer : Customer)

        fun showContactDetails(contactDetail : ContactDetail)

        fun showToolbarTitleSubtitle(title: String, subtitle: String)

        fun loadCustomerPortrait()

        fun showProgressbar()

        fun hideProgressbar()

       fun getCustomerStatus(): Customer.State

        fun showError(errorMessage : String)

    }
    interface Presenter{
        fun loadCustomerDetails(identifier: String)


    }
}