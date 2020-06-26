package org.mifos.mobile.cn.ui.mifos.identificationdetails

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_identification_details.*
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.adapter.IdentificationScanAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.viewScanCard.ViewScanCardActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.DateUtils
import java.util.*
import javax.annotation.Nullable
import javax.inject.Inject

class IdentificationDetailsFragment:MifosBaseFragment(),IdentificationDetailsContract.View, IdentificationScanAdapter.OnItemClickListener {

    @Inject
    lateinit var identificationDetailsPresenter: IdentificationDeatilsPresenter

    @Inject
    lateinit var identificationScanAdapter : IdentificationScanAdapter

    lateinit var rootView: View

    lateinit var customerIdentifier: String
    lateinit var identificationCard: Identification
    lateinit var scanCards: List<ScanCard>
    companion object {
        fun newInstance(customerIdentifier:String,identification: Identification): IdentificationDetailsFragment{
            val fragment = IdentificationDetailsFragment()
            val args = Bundle()
            args.putString(ConstantKeys.CUSTOMER_IDENTIFIER, customerIdentifier)
            args.putParcelable(ConstantKeys.IDENTIFICATION_CARD,identification)
            fragment.arguments = args
            return fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            customerIdentifier = arguments!!.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
            identificationCard = arguments!!.getParcelable(ConstantKeys.IDENTIFICATION_CARD)
        }
        setHasOptionsMenu(true)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?,@Nullable savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_identification_details,container,false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        identificationDetailsPresenter.attachView(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserInterface()
        initializeRecyclerView()

    }

    override fun onResume() {
        super.onResume()
        fetchIdentificationScanCard()
    }

    override fun fetchIdentificationScanCard() {
        tv_scans_status.visibility = View.VISIBLE
        rv_scans_uploaded.visibility = View.GONE
        layout_error.visibility = View.GONE
        identificationDetailsPresenter.fetchIdentificationScanCards(customerIdentifier, identificationCard.number!!)
    }

    override fun showUserInterface() {
        tv_number.text = identificationCard.number
        tv_issuer.text = identificationCard.issuer
        tv_type.text = identificationCard.type

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, identificationCard.expirationDate!!.year!!)
        calendar.set(Calendar.MONTH, identificationCard.expirationDate!!.month!! - 1)
        calendar.set(Calendar.DAY_OF_MONTH, identificationCard.expirationDate!!.day!!)
        tv_expiration_date.text = DateUtils.convertServerDate(calendar)
    }

    override fun initializeRecyclerView() {
        val layoutmanager = LinearLayoutManager(activity)
        layoutmanager.orientation = LinearLayoutManager.VERTICAL
        rv_scans_uploaded.layoutManager = layoutmanager
        rv_scans_uploaded.setHasFixedSize(true)
        identificationScanAdapter.setonItemClickListener(this)
        rv_scans_uploaded.adapter = identificationScanAdapter
            }

    override fun showScanCards(scanCards: List<ScanCard>) {
        this.scanCards = scanCards
        showRecyclerView(true)
        identificationScanAdapter.setScanCards(scanCards)
    }

    override fun showRecyclerView(status: Boolean) {
        if (status){
            rv_scans_uploaded.visibility = View.VISIBLE
            layout_error.visibility = View.GONE
        }
        else {
            rv_scans_uploaded.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
        }
        tv_scans_status.visibility = View.GONE
    }

    override fun showScansStatus(message: String) {
        showRecyclerView(false)
    }

    override fun showError(message: String) {
        showRecyclerView(false)
    }

    override fun onItemClick(view: View, position: Int) {
        val intent = Intent(activity, ViewScanCardActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER, customerIdentifier)
        intent.putExtra(ConstantKeys.IDENTIFICATION_NUMBER, identificationCard.number)
        intent.putExtra(ConstantKeys.IDENTIFICATION_SCAN_CARD, Gson().toJson(scanCards))
        intent.putExtra(ConstantKeys.POSITION, position)
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_identification_card,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        identificationDetailsPresenter.detachView()
    }

}