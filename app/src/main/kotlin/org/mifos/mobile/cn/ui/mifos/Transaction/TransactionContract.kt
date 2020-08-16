package org.mifos.mobile.cn.ui.mifos.Transaction

import org.mifos.mobile.cn.data.models.entity.Transaction
import org.mifos.mobile.cn.data.models.entity.TransactionInfo
import org.mifos.mobile.cn.ui.base.MvpView

interface TransactionContract {
    interface View : MvpView {
        //TODO:edit this for access tokens and other user data
        fun showTransactionSuccessfully(transactionInfo: TransactionInfo)

        fun showTransactionUnSuccessfully(message: String?)

        fun showError(errorMessage: String)

        fun showProgress()

        fun hideProgress()
    }
    interface Presenter {

        fun transaction(transaction: Transaction)
    }
}