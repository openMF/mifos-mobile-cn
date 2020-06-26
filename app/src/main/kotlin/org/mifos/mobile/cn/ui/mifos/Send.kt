package org.mifos.mobile.cn.ui.mifos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.hover.sdk.actions.HoverAction
import com.hover.sdk.api.Hover
import com.hover.sdk.api.HoverParameters
import com.hover.sdk.permissions.PermissionActivity
import kotlinx.android.synthetic.main.activity_send.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import java.util.ArrayList

class Send : MifosBaseActivity(),Hover.DownloadListener {
    private val TAG = "SendActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        showBackButton()
        setToolbarTitle(getString(R.string.send_money))
        Hover.initialize(applicationContext,this)
        send.setOnClickListener(){
            val intent = Intent(applicationContext, PermissionActivity::class.java)
            startActivityForResult(intent, 0)
            val i = HoverParameters.Builder(this@Send)
                    .request("Add action id") // Add your action ID here
                    .extra("phoneNumber", phonetext.text.toString()) // Uncomment and add your variables if any
                    .extra("amount", amounttext.text.toString())
                    .extra("description", descriptiontext.text.toString())
                    .buildIntent()
            startActivityForResult(i, 0)
        }
    }

    override fun onSuccess(actions: ArrayList<HoverAction>) {
        Log.d(TAG, "Successfully downloaded " + actions.size + " actions")
    }

    override fun onError(message: String?) {
        Log.e(TAG, "Error: $message")
    }
}