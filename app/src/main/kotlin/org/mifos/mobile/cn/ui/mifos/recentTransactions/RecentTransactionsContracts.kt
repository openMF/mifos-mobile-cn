package org.mifos.mobile.cn.ui.mifos.recentTransactions

import org.intellij.lang.annotations.Identifier
import org.mifos.mobile.cn.data.models.customer.AccountEntriesPage
import org.mifos.mobile.cn.data.models.customer.AccountEntry
import org.mifos.mobile.cn.ui.base.MvpView

interface RecentTransactionsContracts {

    interface View: MvpView{

        fun showUserInterface()

        fun showAccountEntries(entries: List<AccountEntry>)

        fun showEmptyEntries()

        fun showRecyclerView(status: Boolean)

        fun showProgressbar()

        fun hideProgressbar()

        fun searchedTransaction(transactions: List<AccountEntry>)

    }

    interface Presenter{
        fun getEntriesPage()

        fun searchTransaction(transactionList: List<AccountEntry>,type: String)
    }
}