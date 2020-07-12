package org.mifos.mobile.cn.ui.mifos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.content_fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.Transaction.TransactionActivity
import org.mifos.mobile.cn.ui.mifos.beneficiaries.BeneficiariesActivity
import org.mifos.mobile.cn.ui.mifos.recentTransactions.RecentTransactionsFragment


class Home : MifosBaseFragment(), View.OnClickListener {

    private lateinit var rootView: View
    private lateinit var sheetBehavior: BottomSheetBehavior<*>
    var mypopupWindow: PopupWindow? = null

    companion object {
        fun newInstance(): Home = Home()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
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
        setPopUpWindow(view)
        receive.setOnClickListener(this)
        sendit.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {
            R.id.receive ->{
                qrcode()
            }
            R.id.sendit ->{
                mypopupWindow?.showAsDropDown(view,0,-5);
            }
        }
    }

    private fun sendmoneyhover() {
        val intent = Intent(activity, Send::class.java)
        mypopupWindow?.dismiss()
        startActivity(intent)
    }

    private fun qrcode() {
        val intent = Intent(activity, QRGenerator::class.java)
        startActivity(intent)
    }
    private fun sendmoney() {
        val intent = Intent(activity, TransactionActivity::class.java)
        mypopupWindow?.dismiss()
        startActivity(intent)
    }
    private fun setPopUpWindow(view: View) {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_send, null)
        mypopupWindow = PopupWindow(view, 500, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        val payment = view.findViewById(R.id.text_payment) as RelativeLayout
        payment.setOnClickListener(View.OnClickListener {
            sendmoney()
        })
        val hover = view.findViewById(R.id.text_hover) as RelativeLayout
        hover.setOnClickListener(View.OnClickListener {
            sendmoneyhover()
        })
    }
}