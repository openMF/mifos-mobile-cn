package org.mifos.mobile.cn.ui.mifos.identificationdetails

import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.ui.base.MvpView

interface IdentificationDetailsContract {
    interface View : MvpView{
        fun fetchIdentificationScanCard()

         fun showUserInterface()

        fun initializeRecyclerView()

        fun showScanCards(scanCards: List<ScanCard>)

        fun showRecyclerView(status: Boolean)

        fun showScansStatus(message: String)


        fun hideProgressDialog()

        fun showError(message: String)
    }
    interface Presenter{
        fun fetchIdentificationScanCards(customerIdentifier: String, identificationNumber: String)

    }
}