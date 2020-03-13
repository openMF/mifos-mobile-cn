package org.mifos.mobile.cn.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.accounts.AccountsFragment
import org.mifos.mobile.cn.ui.mifos.recentTransactions.RecentTransactionsFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys

class Test1 : MifosBaseFragment() {
    companion object {
        fun newInstance(): Test1 = Test1()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setToolbarTitle("Recent Transactions")
        getToolbar().setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}