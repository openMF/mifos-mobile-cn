package org.mifos.mobile.cn.ui.mifos.identificationlist

import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.ui.base.MvpView

interface IdentificationsContract {
    interface Views : MvpView{
        fun showUserInterface()

        fun showIdentification(identifications: List<Identification>)

        fun showProgressbar()

        fun hideProgressbar()

        fun showRecyclerView(status: Boolean)

        fun showEmptyIdentifications()

        fun showMessage(message: String)

        fun searchedIdentifications(identification: List<Identification>)

    }
    interface Presenter{
        fun fetchIdentifications(customerIdentifier: String)
        fun searchIdentifications(identificationList: List<Identification>, query: String)
    }
}