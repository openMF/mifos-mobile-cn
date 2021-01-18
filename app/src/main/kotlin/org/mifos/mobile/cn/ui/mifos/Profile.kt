package org.mifos.mobile.cn.ui.mifos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.fragment_client_accounts.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.menu_layout.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.beneficiaries.BeneficiariesActivity


class Profile : MifosBaseFragment(){
    var mypopupWindow: PopupWindow? = null
    companion object {
        fun newInstance(): Profile = Profile()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        getToolbar().setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notify.setOnClickListener {
            val intent = Intent(activity, Notification::class.java)
            startActivity(intent)
        }
        setPopUpWindow(view)
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.image_menu -> {
                    mypopupWindow?.showAsDropDown(view,0,0);
                }
            }
        }

        image_menu.setOnClickListener(clickListener)
    }

    private fun setPopUpWindow(view: View) {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_layout, null)
        mypopupWindow = PopupWindow(view, 350, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        val beneficiary = view.findViewById(R.id.text_beneficiary) as RelativeLayout
        beneficiary.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, BeneficiariesActivity::class.java)
            startActivity(intent)
        })
    }

}