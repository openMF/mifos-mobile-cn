package org.mifos.mobile.cn.ui.mifos.loanApplication

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.loan.CreditWorthinessFactor

import org.mifos.mobile.cn.ui.utils.RxBus
import org.mifos.mobile.cn.ui.utils.RxEvent
import org.mifos.mobile.cn.ui.utils.Toaster

class AddDebtIncomeBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {


    private var tvHeader: TextView? = null
    private var etAmount: EditText? = null
    private var etDescription: EditText? = null
    lateinit var btnAddDebtIncome: Button
    lateinit var btnCancel: Button

    private lateinit var rootView: View

    private var behavior: BottomSheetBehavior<*>? = null
    private var creditWorthinessSource: CreditWorthinessSource? = null
    private var creditWorthinessFactor: CreditWorthinessFactor? = null

    private var position: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        rootView = View.inflate(context, R.layout.bottom_sheet_add_debt_income, null)
        initialiseComponents(rootView)
        dialog.setContentView(rootView)
        behavior = BottomSheetBehavior.from(rootView.parent as View)

        when (creditWorthinessSource) {
            CreditWorthinessSource.DEBT -> {
                tvHeader?.text = getString(R.string.add_debt)
                btnAddDebtIncome.text = getString(R.string.add_debt)
            }
            CreditWorthinessSource.INCOME -> {
                tvHeader?.text = getString(R.string.add_income)
                btnAddDebtIncome.text = getString(R.string.add_income)
            }
            CreditWorthinessSource.EDIT_DEBT -> {
                tvHeader?.text = getString(R.string.edit_debt)
                btnAddDebtIncome.text = getString(R.string.edit_debt)
                etAmount?.setText(creditWorthinessFactor?.amount.toString())
                etDescription?.setText(creditWorthinessFactor?.description)
            }
            CreditWorthinessSource.EDIT_INCOME -> {
                tvHeader?.text = (getString(R.string.edit_income))
                btnAddDebtIncome.text = (getString(R.string.edit_income))
                etAmount?.setText(creditWorthinessFactor?.amount.toString())
                etDescription?.setText(creditWorthinessFactor?.description)
            }
        }

        return dialog
    }

    private fun initialiseComponents(view: View) {
        tvHeader = view.findViewById(R.id.tv_header)
        etAmount = view.findViewById(R.id.et_amount)
        etDescription = view.findViewById(R.id.et_description)
        btnAddDebtIncome = view.findViewById(R.id.btn_add_debt_income)
        btnAddDebtIncome.setOnClickListener(this)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener(this)


    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_cancel -> onCancel()
            R.id.btn_add_debt_income -> addDebtIncome()
        }
    }


    private fun onCancel() {
        dismiss()
    }

    private fun addDebtIncome() {
        if (TextUtils.isEmpty(etAmount?.text.toString().trim { it <= ' ' })) {
            Toaster.show(rootView, getString(R.string.amount_should_be_not_empty))
            return
        }
        if (TextUtils.isEmpty(etDescription?.text.toString().trim { it <= ' ' })) {
            Toaster.show(rootView, getString(R.string.description_should_not_be_empty))
            return
        }

        val creditWorthinessFactor = CreditWorthinessFactor()
        creditWorthinessFactor.amount = etAmount?.text.toString().trim { it <= ' ' }.toDouble()
        creditWorthinessFactor.description = etDescription?.text.toString().trim { it <= ' ' }

        when (creditWorthinessSource) {
            CreditWorthinessSource.DEBT -> RxBus
                    .publish(RxEvent.AddDebt(creditWorthinessFactor))
            CreditWorthinessSource.INCOME -> RxBus
                    .publish(RxEvent.AddIncome(creditWorthinessFactor))
            CreditWorthinessSource.EDIT_DEBT -> RxBus
                    .publish(RxEvent.EditDebt(creditWorthinessFactor, position))
            CreditWorthinessSource.EDIT_INCOME -> RxBus
                    .publish(RxEvent.EditIncome(creditWorthinessFactor, position))
        }
        dismiss()
    }

    fun setCreditWorthinessSource(creditWorthinessSource: CreditWorthinessSource) {
        this.creditWorthinessSource = creditWorthinessSource
    }

    fun setCreditWorthinessFactor(creditWorthinessFactor: CreditWorthinessFactor) {
        this.creditWorthinessFactor = creditWorthinessFactor
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    override fun onStart() {
        super.onStart()
        behavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}