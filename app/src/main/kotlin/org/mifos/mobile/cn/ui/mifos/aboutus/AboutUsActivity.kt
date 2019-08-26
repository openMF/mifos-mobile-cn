package org.mifos.mobile.cn.ui.mifos.aboutus

import android.os.Bundle
import kotlinx.android.synthetic.main.globalcontainer.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

class AboutUsActivity: MifosBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_container)
        setToolbarTitle(getString(R.string.about_us))
        showBackButton()
        replaceFragment(AboutUsFragment.newInstance(),false,R.id.container)
    }
}