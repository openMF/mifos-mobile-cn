package org.mifos.mobile.cn.ui.mifos.recentTransactions

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_accounts.*
import kotlinx.android.synthetic.main.fragment_recent_transactions.*
import kotlinx.android.synthetic.main.layout_exception_handler.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.customer.AccountEntriesPage
import org.mifos.mobile.cn.data.models.customer.AccountEntry
import org.mifos.mobile.cn.ui.adapter.RecentTransactionsAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import javax.inject.Inject

class RecentTransactionsFragment : MifosBaseFragment(),RecentTransactionsContracts.View,
SwipeRefreshLayout.OnRefreshListener{


    @Inject
    lateinit var recentTrancactionsAdapter: RecentTransactionsAdapter

    @Inject
    lateinit var recentTransactionsPresenter: RecentTransactionsPresenter

    lateinit var recentTransactionList: List<AccountEntry>
    companion object {
        fun newInstance(): RecentTransactionsFragment = RecentTransactionsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recentTransactionList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recent_transactions,container,false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        recentTransactionsPresenter.attachView(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUserInterface()

        btn_try_again.setOnClickListener{
            layout_error.visibility = View.VISIBLE
            recentTransactionsPresenter.getEntriesPage()
        }
        recentTransactionsPresenter.getEntriesPage()
    }

    override fun showUserInterface() {
        val  layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        rvRecentTransactions.layoutManager = layoutManager
        rvRecentTransactions.setHasFixedSize(true)
        rvRecentTransactions.adapter = recentTrancactionsAdapter

        swipeContainer.setColorSchemeColors(*activity!!
                .resources.getIntArray(R.array.swipeRefreshColors))
        swipeContainer.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        recentTransactionsPresenter.getEntriesPage()
    }
    override fun showAccountEntries(entries: List<AccountEntry>) {
        this.recentTransactionList =entries
        recentTrancactionsAdapter.setTransactionList(entries)
    }

    override fun showEmptyEntries() {
        showRecyclerView(false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_transactions_search,menu)
        setUpSearchInterface(menu)
    }
    private fun setUpSearchInterface(menu: Menu?){
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        val searchView = menu?.findItem(R.id.transactions_search)?.actionView as? SearchView

        searchView?.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recentTransactionsPresenter.searchTransaction(recentTransactionList,query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(TextUtils.isEmpty(newText)){
                    showRecyclerView(true)
                    recentTrancactionsAdapter.setTransactionList(recentTransactionList)

                }
                recentTransactionsPresenter.searchTransaction(recentTransactionList,newText!!)
                return false
            }
    })
}

    override fun searchedTransaction(transactions: List<AccountEntry>) {
        showRecyclerView(true)
        recentTrancactionsAdapter.setTransactionList(transactions)
    }

    override fun showRecyclerView(status: Boolean) {
        if(status){
            rvRecentTransactions.visibility = View.VISIBLE
            layoutError.visibility = View.GONE
        }
        else{
            rvRecentTransactions.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
        }
    }

    override fun showProgressbar() {
        swipeContainer.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipeContainer.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recentTransactionsPresenter.detachView()
    }
}