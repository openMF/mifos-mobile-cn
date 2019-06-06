package org.mifos.mobile.cn.ui.mifos.customerDetails

import android.os.Bundle
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.mifos.customerActivities.CustomerActivitiesFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys

class CustomerDetailsActivity: MifosBaseActivity() {
    var customerDetailsFragment= CustomerDetailsFragment


   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_container)
       supportActionBar!!.hide()

       val identifier : String = intent.extras.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
       replaceFragment(CustomerDetailsFragment.newInstance(identifier),false,R.id.container)
       showBackButton()

    }

}