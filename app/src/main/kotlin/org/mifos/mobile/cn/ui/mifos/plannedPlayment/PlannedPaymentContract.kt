package org.mifos.mobile.cn.ui.mifos.plannedPlayment

import org.mifos.mobile.cn.data.models.payment.PlannedPayment
import org.mifos.mobile.cn.ui.base.MvpView

interface PlannedPaymentContract {
    interface View : MvpView {

        fun fetchPlannedPayment()

        fun showUserInterface()

        fun showPlannedPayment(plannedPayments: List<PlannedPayment>)

        fun showMorePlannedPayments(plannedPayments: List<PlannedPayment>)

        fun showEmptyPayments(message: String)

        fun showRecyclerView(visible: Boolean)

        fun showProgressbar()

        fun hideProgressbar()

        fun showMessage(message: String)
    }

    interface Presenter {

        fun fetchPlannedPayment(productIdentifier: String, caseIdentifier: String,
                                pageIndex: Int?, initialDisbursalDate: String, loadmore: Boolean?)

        fun fetchPlannedPayment(productIdentifier: String, caseIdentifier: String,
                                pageIndex: Int?, initialDisbursalDate: String)

        fun showPlannedPayment(customers: List<PlannedPayment>)
    }
}