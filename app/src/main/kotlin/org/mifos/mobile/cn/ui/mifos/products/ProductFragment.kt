package org.mifos.mobile.cn.ui.mifos.products

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.layout_exception_handler.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.data.models.product.ProductPage
import org.mifos.mobile.cn.ui.adapter.ProductAdapter
import org.mifos.mobile.cn.ui.base.MifosBaseActivity
import org.mifos.mobile.cn.ui.base.MifosBaseFragment
import java.util.ArrayList
import javax.inject.Inject

class ProductFragment:MifosBaseFragment(),ProductContracts.View,SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var productPresenter: ProductPresenter

    @Inject
    lateinit var productAdapter: ProductAdapter

    lateinit var productList: List<Product>

    companion object {
        fun newInstance() = ProductFragment().apply {
            val args = Bundle()
            arguments = args
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        productList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_product, container, false)
        (activity as MifosBaseActivity).activityComponent.inject(this)
        productPresenter.attachView(this)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showUserInterface()

        btn_try_again.setOnClickListener {
            layoutError.visibility = View.GONE
            productPresenter.getProductsPage()
        }

            productPresenter.getProductsPage()
    }
    override fun showUserInterface() {

        setToolbarTitle(getString(R.string.products))
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL
        rvProduct.layoutManager = layoutManager
        rvProduct.setHasFixedSize(true)
        rvProduct.adapter = productAdapter

        swipeContainer.setColorSchemeColors(*activity!!
                .resources.getIntArray(R.array.swipeRefreshColors))
        swipeContainer.setOnRefreshListener(this)

    }
    override fun onRefresh() {
        productPresenter.getProductsPage()
    }
    override fun showProduct(products: LiveData<ProductPage>) {
        showRecyclerView(true)
        this.productList = products.value?.elements!!
        productAdapter.setProductsList(products.value?.elements!!)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_product_search, menu)
        setUpSearchInterface(menu)
    }

    private fun setUpSearchInterface(menu: Menu?) {

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as? SearchManager
        val searchView = menu?.findItem(R.id.product_search)?.actionView as? SearchView

        searchView?.setSearchableInfo(searchManager?.getSearchableInfo(activity?.componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                productPresenter.searchProduct(productList, query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (TextUtils.isEmpty(newText)) {
                    showRecyclerView(true)
                    productAdapter.setProductsList(productList)

                } else {
                    productPresenter.searchProduct(productList, newText)
                }

                return false
            }
        })

    }
    override fun showEmptyProduct() {
        showRecyclerView(false)
    }
    override fun showRecyclerView(status: Boolean) {
        if (status) {
            rvProduct.visibility = View.VISIBLE
            layoutError.visibility = View.GONE
        } else {
            rvProduct.visibility = View.GONE
            layoutError.visibility = View.VISIBLE
        }
    }
    override fun showProgressbar() {
        swipeContainer.isRefreshing = true
    }
    override fun hideProgressbar() {
        swipeContainer.isRefreshing = false
    }
    override fun searchedProduct(products: List<Product>) {
        //showRecyclerView(true)
        productAdapter.setProductsList(products)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        productPresenter.detachView()
    }
}