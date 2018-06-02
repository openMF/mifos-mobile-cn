package org.mifos.mobile.cn.ui.mifos

import android.os.Bundle
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

class DashboardActivity : MifosBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbarTitle(getString(R.string.home))
    }
}
