package org.mifos.mobile.cn.ui.mifos.dashboard


import android.os.Bundle
import android.view.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.recentTransactions.RecentTransactionsFragment


class DashboardFragment : MifosBaseFragment(), View.OnClickListener {

    private lateinit var rootView: View
    private lateinit var customer: Customer
    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    companion object {

        fun newInstance(): DashboardFragment {
            val fragment = DashboardFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        setToolbarTitle(getString(R.string.home))
        val ft = childFragmentManager.beginTransaction()
        val rt = RecentTransactionsFragment()
        ft.replace(R.id.fl_recentTransactions, rt)
        ft.addToBackStack(null)
        ft.commit()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheetBehavior = BottomSheetBehavior.from(rt_bottom_sheet)
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })
    }

    override fun onClick(view: View) {

        when (view.id) {
        }
    }

}
