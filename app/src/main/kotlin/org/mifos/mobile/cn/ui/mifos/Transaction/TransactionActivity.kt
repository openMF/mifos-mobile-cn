package org.mifos.mobile.cn.ui.mifos.Transaction

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_transaction.*
import org.json.JSONObject
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.entity.*
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.Toaster
import javax.inject.Inject

class TransactionActivity : MifosBaseActivity(), TransactionContract.View, View.OnClickListener {

    @Inject
    internal lateinit var transactionPresenter: TransactionPresenter
    var jsonob: JSONObject? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)
        showBackButton()
        setToolbarTitle("Send Money")
        activityComponent.inject(this)
        transactionPresenter.attachView(this)
        send.setOnClickListener(this)
        if(intent.getStringExtra("JSON")!=null) {
            jsonob = JSONObject(intent.getStringExtra("JSON"))
            MSISDNtext.setText(jsonob?.getString("mobile"))
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.send -> transaction()
        }

    }

    fun transaction() {

        val amount = amounttext.text.toString().trim()
        val msisdn = MSISDNtext.text.toString().trim()
        if (TextUtils.isEmpty(amount)) {
            amounttext.error = "Amount required"
            return
        }
        if (TextUtils.isEmpty(msisdn)) {
            MSISDNtext.error = "MSISDN required"
            return
        }
        val transaction = Transaction(TransactingEntity
        (PartyIdInfo(IdentifierType.MSISDN, "27710101999")),
                TransactingEntity(PartyIdInfo(IdentifierType.MSISDN, msisdn))
                , Amount("TZS", amount))
        transactionPresenter.transaction(transaction)
        Toaster.show(findViewById(android.R.id.content), "Sending Money", Toaster.SHORT)
    }

    override fun showTransactionSuccessfully(transactionInfo: TransactionInfo) {
        Log.d("Hello", transactionInfo.transactionId)
        Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
    }

    override fun showError(errorMessage: String) {
        Toaster.show(findViewById(android.R.id.content), errorMessage, Toaster.SHORT)
    }

    override fun showProgress() {
        showProgressbar()
    }

    override fun hideProgress() {
        hideProgressbar()
    }

    override fun showTransactionUnSuccessfully(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}