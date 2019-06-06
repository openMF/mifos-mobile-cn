package org.mifos.mobile.cn.ui.mifos.viewScanCard

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_view_scan_card.*
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.adapter.ViewPagerAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Utils


class ViewScanCardActivity: MifosBaseActivity(),ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {



    private lateinit var scanCards: List<ScanCard>
    private lateinit var identificationNumber : String
    private lateinit var customerIdentifier: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_scan_card)

        val scanCardString = intent.getStringExtra(ConstantKeys.IDENTIFICATION_SCAN_CARD)
        identificationNumber = intent.getStringExtra(ConstantKeys.IDENTIFICATION_NUMBER)
        customerIdentifier = intent.getStringExtra(ConstantKeys.CUSTOMER_IDENTIFIER)
        val position = intent.getIntExtra(ConstantKeys.POSITION, 0)

        scanCards = Utils.getStringToPoJo(object : TypeToken<List<ScanCard>>() {

        }, scanCardString)

        showBackButton()

        vp_view_scan_card.addOnAdapterChangeListener(this)
        setupViewPager(vp_view_scan_card)
        vp_view_scan_card.setCurrentItem(position,true)
    }
    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        for (scanCard: ScanCard in scanCards) {
            adapter.addFragment(ViewScanCardFragment.newInstance(customerIdentifier,
                    identificationNumber, scanCard.identifier), scanCard.identifier)
        }
        viewPager.adapter = adapter
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        setToolbarTitle(scanCards[position].identifier)
    }

    override fun onPageSelected(position: Int) {
    }
    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: PagerAdapter?, newAdapter: PagerAdapter?) {
    }
}