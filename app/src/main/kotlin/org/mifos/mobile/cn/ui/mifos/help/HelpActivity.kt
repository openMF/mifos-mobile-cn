package org.mifos.mobile.cn.ui.mifos.help

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

class HelpActivity : MifosBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_container)

        setToolbarTitle(getString(R.string.help))
        showBackButton()
         replaceFragment(HelpFragment.newInstance(),false,R.id.container)
    }
}