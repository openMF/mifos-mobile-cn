package org.mifos.mobile.cn.ui.mifos.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.fragment_help.view.*
import org.mifos.mobile.cn.BuildConfig
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.aboutus.AboutUsFragment
import org.mifos.mobile.cn.ui.mifos.privacypolicy.PrivacyPolicyActivity
import java.util.*

// TODO: Rename parameter arguments, choose names that match

class HelpFragment : MifosBaseFragment(){

    lateinit var rootview: View
       var url: String  = "https://gitter.im/openMF/mifos-mobile-cn"

    lateinit var button: Button

    companion object {
        fun newInstance(): Fragment {
            val fragment: Fragment = HelpFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootview = inflater.inflate(R.layout.fragment_help, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        setToolbarTitle(getString(R.string.help))
        rootview.want_help.setOnClickListener {
            openGitter()
        }
        return rootview
    }

    private fun openGitter() {
        startActivity(Intent(Intent.ACTION_VIEW ,Uri.parse(url)))
    }

}