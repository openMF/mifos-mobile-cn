package org.mifos.mobile.cn.ui.mifos.aboutus

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.fragment_about_us.*
import org.mifos.mobile.cn.BuildConfig
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.privacypolicy.PrivacyPolicyActivity
import java.util.*

class AboutUsFragment : MifosBaseFragment(), View.OnClickListener {

    lateinit var rootview: View

    companion object {
        fun newInstance(): Fragment {
            val fragment: Fragment = AboutUsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootview = inflater.inflate(R.layout.fragment_about_us, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        setToolbarTitle(getString(R.string.about_us))
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_app_version.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        tv_copy_right.text = getString(R.string.copy_right_mifos, Calendar.getInstance().get(Calendar.YEAR).toString())
        tv_licenses.setOnClickListener(this)
        tv_privacy_policy.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.tv_licenses -> showOpenSourceLisences()
                R.id.tv_privacy_policy -> showPrivacyPolicy()
            }
        }
    }

    fun showOpenSourceLisences() {
        startActivity(Intent(activity, OssLicensesMenuActivity::class.java))
    }

    fun showPrivacyPolicy() {
        startActivity(Intent(activity, PrivacyPolicyActivity::class.java))
    }
}