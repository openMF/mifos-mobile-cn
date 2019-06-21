package org.mifos.mobile.cn.ui.mifos.products

import androidx.lifecycle.LiveData
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.data.models.product.ProductPage
import org.mifos.mobile.cn.ui.base.MvpView

interface ProductContracts {
    interface View : MvpView {
        fun showUserInterface()

        fun showProduct(products: LiveData<ProductPage>)

        fun showEmptyProduct()

        fun showRecyclerView(status: Boolean)

        fun showProgressbar()

        fun hideProgressbar()

        fun searchedProduct(products: List<Product>)

    }

    interface Presenter {

        fun getProductsPage()


        fun searchProduct(products: List<Product>, identifier: String)
    }
}

