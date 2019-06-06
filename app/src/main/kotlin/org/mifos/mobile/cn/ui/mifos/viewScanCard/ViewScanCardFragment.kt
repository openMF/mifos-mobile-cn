package org.mifos.mobile.cn.ui.mifos.viewScanCard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_scan_card.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.ImageLoaderUtils
import javax.annotation.Nullable

class ViewScanCardFragment : MifosBaseFragment() {

    lateinit var rootView: View

    private lateinit var customerIdentifier: String
    private lateinit var identifierNumber: String
    private lateinit var scanIdentifier: String
companion object {


    fun newInstance(customerIdentifier: String, identificationNumber: String, scanIdentifier: String): ViewScanCardFragment {
        val fragment = ViewScanCardFragment()
        val args = Bundle()
        args.putString(ConstantKeys.CUSTOMER_IDENTIFIER, customerIdentifier)
        args.putString(ConstantKeys.IDENTIFICATION_NUMBER, identificationNumber)
        args.putString(ConstantKeys.IDENTIFICATION_SCAN_CARD, scanIdentifier)
        fragment.arguments = args
        return fragment
    }
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            customerIdentifier = arguments!!.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
            identifierNumber = arguments!!.getString(ConstantKeys.IDENTIFICATION_NUMBER)
            scanIdentifier = arguments!!.getString(ConstantKeys.IDENTIFICATION_SCAN_CARD)
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_view_scan_card, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_scan_card.visibility = View.VISIBLE
        val imageLoaderUtils = ImageLoaderUtils(this.context!!)
        imageLoaderUtils.loadImage(imageLoaderUtils.buildIdentificationScanCardImageUrl(
                customerIdentifier, identifierNumber, scanIdentifier), iv_scan_card,
                R.drawable.ic_autorenew_black_24dp)

    }
}