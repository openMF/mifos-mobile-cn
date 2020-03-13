package org.mifos.mobile.cn.ui.mifos

import android.os.Bundle
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

class Notification: MifosBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        showBackButton()
        setToolbarTitle("Notifications")
    }
}