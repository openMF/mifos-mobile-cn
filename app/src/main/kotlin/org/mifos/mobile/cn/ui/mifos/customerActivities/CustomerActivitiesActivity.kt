package org.mifos.mobile.cn.ui.mifos.customerActivities

import android.os.Bundle
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys

class CustomerActivitiesActivity: MifosBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_container)

        val identifier : String = intent.extras.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
        replaceFragment(CustomerActivitiesFragment.newInstance(identifier),false,R.id.container)
        showBackButton()
    }
}