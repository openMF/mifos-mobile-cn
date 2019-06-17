package org.mifos.mobile.cn.ui.mifos.customerDetails

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import androidx.appcompat.app.AppCompatActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_customer_details.*
import org.mifos.mobile.cn.data.models.customer.Address
import org.mifos.mobile.cn.data.models.customer.ContactDetail
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.enums.AccountType
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.mifos.customerAccounts.CustomerAccountFragment
import org.mifos.mobile.cn.ui.mifos.customerActivities.CustomerActivitiesActivity
import org.mifos.mobile.cn.ui.mifos.customerProfile.CustomerProfileActivity
import org.mifos.mobile.cn.ui.mifos.identificationlist.IdentificationsActivity
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.ImageLoaderUtils
import org.mifos.mobile.cn.ui.utils.StatusUtils
import org.mifos.mobile.cn.ui.views.HeaderView

import javax.inject.Inject

class CustomerDetailsFragment : MifosBaseFragment(), AppBarLayout.OnOffsetChangedListener,CustomerDetailsContract.View, View.OnClickListener{



    @Inject
    lateinit var customerDetailsPresenter: CustomerDetailsPresenter

    private lateinit var rootView : View
    private lateinit var customerIdentification : String
    private  var isHideToolbarView: Boolean = false
    private lateinit var collapsingToolbarLayout : CollapsingToolbarLayout
    private lateinit var customer : Customer
    private lateinit var toolbarHeaderView : HeaderView
    private  lateinit var floatHeaderView : HeaderView

  companion object {
      fun newInstance(identifier: String): CustomerDetailsFragment {
          val fragment = CustomerDetailsFragment()
          val args = Bundle()
          args.putString(ConstantKeys.CUSTOMER_IDENTIFIER,identifier)
          fragment.arguments = args
          return fragment

      }
  }



    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setToolbarTitle(getString(R.string.account_overview))
        if(arguments != null){1
            customerIdentification = arguments!!.getString(ConstantKeys.CUSTOMER_IDENTIFIER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_customer_details,container,false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        customerDetailsPresenter.attachView(this)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarHeaderView = view.findViewById(R.id.toolbar_header_view)
        floatHeaderView = view.findViewById(R.id.float_header_view)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar)
        ll_deposit_accounts.setOnClickListener(this)
        ll_loan_accounts.setOnClickListener(this)
        ll_identification_cards.setOnClickListener(this)
        iv_customer_profile.setOnClickListener(this)
        ll_activities.setOnClickListener(this)
        showUserInterface()


    }
    override fun onClick(view: View) {
        when(view.id){
            R.id.ll_deposit_accounts -> {
                openDepositAccount()
            }
            R.id.ll_loan_accounts-> {
                openLoanAccount()
            }
            R.id.ll_activities -> {
                openCustomerActivities()
            }
            R.id.ll_identification_cards -> {
                openIdentificationCards()
            }
            R.id.iv_customer_profile -> {
                openCustomerProfile()
            }
        }
    }

    private fun openCustomerProfile() {
        val intent = Intent(activity, CustomerProfileActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER, customerIdentification)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
       cl_customer_details.visibility = View.GONE
        collapsingToolbarLayout.title= " "
        customerDetailsPresenter.loadCustomerDetails(customerIdentification)
    }



    override fun showCustomerDetails(customer: Customer) {
        this.customer = customer
        cl_customer_details.visibility = View.VISIBLE
        layout_error.visibility = View.GONE

        loadCustomerPortrait()

        tv_current_status.text = customer.currentState!!.name
        StatusUtils.setCustomerStatusIcon(customer.currentState!!,iv_current_status,context)


        val address : Address = customer.address!!
        val addressBuilder = StringBuilder()
        addressBuilder
                .append(address.street).append(", ")
                .append(address.city).append(", ")
        if (address.postalCode != null) {
            addressBuilder.append(address.postalCode)
            addressBuilder.append(", ")
        }
        addressBuilder.append(address.country)
        tv_address.text = addressBuilder

        if (customer.contactDetails!!.isEmpty()) {
            tv_no_contact_details_available.visibility = View.VISIBLE
            tv_email.visibility = View.GONE
            tv_phone_no.visibility = View.GONE
            tv_mobile_no.visibility = View.GONE
        } else {
            for (contactDetail in customer.contactDetails!!) {
                showContactDetails(contactDetail)
            }
        }

        tv_birthday.text = customer.dateOfBirth!!.year.toString() + "-" +
                customer.dateOfBirth!!.month + "-" + customer.dateOfBirth!!.day

        val title = customer.givenName + " " + customer.surname
        val subtitle: String
        if (customer.assignedEmployee == null) {
            subtitle = getString(R.string.assigned_employee) + " " +
                    getString(R.string.not_assigned)
        } else {
            subtitle = getString(R.string.assigned_employee) + " " + customer.assignedEmployee
        }
        showToolbarTitleSubtitle(title, subtitle)
    }
    override fun showUserInterface() {
        if (toolbar_customer != null) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar_customer)
            (activity as AppCompatActivity).supportActionBar!!
                    .setDisplayHomeAsUpEnabled(true)
        }

        collapsingToolbarLayout.title= " "
        app_bar_layout.addOnOffsetChangedListener(this)
    }
    override fun showToolbarTitleSubtitle(title: String, subtitle: String) {
        toolbarHeaderView.bindTo(title,subtitle)
       floatHeaderView.bindTo(title,subtitle)

    }


    override fun showContactDetails(contactDetail: ContactDetail) {
        when (contactDetail.type) {
            ContactDetail.Type.EMAIL -> {
                tv_email.text = contactDetail.value
                rl_email.visibility = View.VISIBLE
            }
            ContactDetail.Type.MOBILE -> {
                tv_mobile_no.text = contactDetail.value
                rl_mobile_no.visibility = View.VISIBLE
            }
            ContactDetail.Type.PHONE -> {
                tv_phone_no.text = contactDetail.value
                rl_phone_no.visibility = View.VISIBLE
            }
        }
    }



    override fun loadCustomerPortrait() {

        val imageLoaderUtils = ImageLoaderUtils(this.context!!)
       imageLoaderUtils.loadImage(imageLoaderUtils.buildCustomerPortraitImageUrl(customer.identifier),iv_customer_profile,R.drawable.mifos_logo_new)

    }

    override fun showProgressbar() {
     showProgressBar()
    }

    override fun hideProgressbar() {
      hideProgressBar()
    }




    override fun getCustomerStatus(): Customer.State {
        return customer.currentState!!
    }


    override fun showError(errorMessage: String) {
        cl_customer_details.visibility = View.GONE
        layout_error.visibility = View.VISIBLE

    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgressDialog()
        customerDetailsPresenter.detachView()
    }
    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = appBarLayout!!.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()
        if (percentage == 1f && isHideToolbarView) {
            toolbar_header_view.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbar_header_view.visibility = View.GONE
            isHideToolbarView = !isHideToolbarView
        }

    }
    private fun openDepositAccount() {
        (activity as MifosBaseActivity)
                .replaceFragment(CustomerAccountFragment.newInstance(AccountType.DEPOSIT),
                        true, R.id.container)
    }
    private fun openLoanAccount() {
        (activity as MifosBaseActivity)
                .replaceFragment(CustomerAccountFragment.newInstance(AccountType.LOAN),
                        true, R.id.container)
    }
    private fun openCustomerActivities() {
        val intent = Intent(activity,CustomerActivitiesActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER,"customer_identifier")
        startActivity(intent)
    }
    private fun openIdentificationCards() {
        val intent = Intent(activity,IdentificationsActivity::class.java)
        intent.putExtra(ConstantKeys.CUSTOMER_IDENTIFIER,"customer_identifier")
        startActivity(intent)
    }




}