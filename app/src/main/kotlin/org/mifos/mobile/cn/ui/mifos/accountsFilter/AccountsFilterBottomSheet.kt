package org.mifos.mobile.cn.ui.mifos.accountsFilter

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.bottom_sheet_filter_accounts.view.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.adapter.CheckboxAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.RxEvent
import javax.inject.Inject

/**
 * @author Manish Kumar
 * @since 20/July/2018
 */


class AccountsFilterBottomSheet : BottomSheetDialogFragment() {

    private lateinit var rootView: View
    private lateinit var behavior: BottomSheetBehavior<*>
    lateinit var accountType: AccountType

    @Inject
    lateinit var checkboxAdapter: CheckboxAdapter

    var filterList: List<CheckboxStatus>? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        (activity as MifosBaseActivity).activityComponent.inject(this)
        rootView = View.inflate(context, R.layout.bottom_sheet_filter_accounts, null)

        checkboxAdapter.statusList = filterList!!

        rootView.btn_filter.setOnClickListener {
            //publish the current filter list to set in the fragment
            RxBus.publish(RxEvent.GetCurrentFilterList(checkboxAdapter.statusList))
            //publish the current filter list for filtering
            RxBus.publish(RxEvent.SetStatusModelList(checkboxAdapter.statusList))
            dismiss()
        }

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rootView.rv_accounts_filter.layoutManager = layoutManager
        rootView.rv_accounts_filter.adapter = checkboxAdapter


        if (accountType == AccountType.LOAN) {
            rootView.tv_header_filter.text = getString(R.string.filter_loan)
        } else if(accountType == AccountType.DEPOSIT) {
            rootView.tv_header_filter.text = getString(R.string.filter_deposit)
        }
            dialog.setContentView(rootView)

        behavior = BottomSheetBehavior.from(rootView.parent as View)

        return dialog
    }


    override fun onStart() {
        super.onStart()
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }

}