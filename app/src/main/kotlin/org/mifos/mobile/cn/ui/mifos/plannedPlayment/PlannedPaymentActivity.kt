package org.mifos.mobile.cn.ui.mifos.plannedPlayment

import android.os.Bundle
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys

class PlannedPaymentActivity: MifosBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_container)
        supportActionBar!!.hide()
        val productIdentifier = intent.extras.getString(
                ConstantKeys.PRODUCT_IDENTIFIER)
        val caseIdentifier = intent.extras.getString(ConstantKeys.CASE_IDENTIFIER)
        replaceFragment(PlannedPaymentFragment.newInstance(productIdentifier, caseIdentifier),
                false, R.id.container)

        showBackButton()
    }
}