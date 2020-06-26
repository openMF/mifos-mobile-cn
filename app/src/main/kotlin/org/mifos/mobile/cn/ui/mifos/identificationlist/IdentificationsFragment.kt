package org.mifos.mobile.cn.ui.mifos.identificationlist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import android.text.TextUtils
import android.view.*
import kotlinx.android.synthetic.main.fragment_identification_list.*
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.adapter.IdentificationsAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import org.mifos.mobile.cn.ui.base.OnItemClickListener
import org.mifos.mobile.cn.ui.mifos.identificationdetails.IdentificationDetailsFragment
import org.mifos.mobile.cn.ui.utils.ConstantKeys
import org.mifos.mobile.cn.ui.utils.Toaster
import java.util.*
import javax.annotation.Nonnull
import javax.annotation.Nullable
import javax.inject.Inject

class IdentificationsFragment : MifosBaseFragment(),IdentificationsContract.Views,OnItemClickListener, SwipeRefreshLayout.OnRefreshListener{

    lateinit var rootView: View
    lateinit var identifications: List<Identification>
    lateinit var customerIdentifier: String

    @Inject
    lateinit var  identificationsPresenter: IdentificationsPresenter

    @Inject
    lateinit var identificationAdapter: IdentificationsAdapter

    companion object {
        fun newInstance(identifier : String):IdentificationsFragment{
        val fragment = IdentificationsFragment()
            val args = Bundle()
            args.putString(ConstantKeys.CUSTOMER_IDENTIFIER, identifier)
            fragment.arguments = args
            return fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        identifications = ArrayList()
        if (arguments != null){
            customerIdentifier = arguments!!.getString(ConstantKeys.CUSTOMER_IDENTIFIER)

        }
        setHasOptionsMenu(true)
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        rootView  = inflater.inflate(R.layout.fragment_identification_list,container,false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        identificationsPresenter.attachView(this)

        return rootView
    }

    override fun onViewCreated(@Nonnull view: View,@Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserInterface()
    }

    override fun showUserInterface() {
        setToolbarTitle(getString(R.string.identification_cards))
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rv_identifications.layoutManager = layoutManager
        rv_identifications.setHasFixedSize(true)
        identificationAdapter.setOnItemClickListener(this)
        identificationAdapter.setIdentifications(identifications)
        rv_identifications.adapter = identificationAdapter

        swipe_container_identification.setOnRefreshListener(this)
    }

    override fun showIdentification(identifications: List<Identification>) {
        showRecyclerView(true)
        this.identifications = identifications
        identificationAdapter.setIdentifications(identifications)
    }

    override fun showProgressbar() {
        swipe_container_identification.isRefreshing = true
    }

    override fun hideProgressbar() {
        swipe_container_identification.isRefreshing = false
    }

    override fun showRecyclerView(status: Boolean) {
        if (status){
            rv_identifications.visibility = View.VISIBLE
            layout_identification_error.visibility = View.GONE
        } else{
            rv_identifications.visibility = View.GONE
            layout_identification_error.visibility = View.VISIBLE
        }
    }

    override fun showEmptyIdentifications() {
        showRecyclerView(false)

    }

    override fun showMessage(message: String) {
        Toaster.show(rootView,message)
    }

    override fun searchedIdentifications(identification: List<Identification>) {
        identificationAdapter.setIdentifications(identification)
    }

    override fun onItemClick(childView: View, position: Int) {
         (activity as MifosBaseActivity).replaceFragment(
         IdentificationDetailsFragment.newInstance(customerIdentifier,
                 identificationAdapter.getItem(position)),true,R.id.container)
    }

    override fun onItemLongPress(childView: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.menu_identification_search,menu)
        setUpSearchInterface(menu)

    }

    private fun setUpSearchInterface(menu: Menu?) {
        val manager: SearchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView : SearchView = menu!!.findItem(R.id.identification_search).actionView as SearchView
        searchView.setSearchableInfo(manager.getSearchableInfo(activity!!.componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                identificationsPresenter.searchIdentifications(identifications, query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    identificationAdapter.setIdentifications(identifications)
                }

                return false
            }
        })


    }

    override fun onRefresh() {
        identificationsPresenter.fetchIdentifications(customerIdentifier)

    }

    override fun onResume() {
        super.onResume()
        identificationsPresenter.fetchIdentifications(customerIdentifier)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        identificationsPresenter.detachView()
    }
}