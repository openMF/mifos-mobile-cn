package org.mifos.mobile.cn.ui.mifos.loanApplication.loanDetails

import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.ui.base.MvpView

interface LoanDetailsContract {

    interface View : MvpView {

        fun showUserInterface()

        fun showProducts(products: List<String>)

        fun setComponentsValidations(product: Product)

        fun showEmptyProducts()

        fun showProgress()

        fun hideProgress()

        fun showError(message: String)

        fun validateShortName(): Boolean

        fun validateTerm(): Boolean

        fun validatePrincipalAmount(): Boolean

        fun validateRepay(): Boolean
    }

    interface Presenter {

        fun fetchProducts()

        fun filterProducts(products: List<Product>): List<String>

        fun setProductPositionAndValidateViews(position: Int?)

        fun getCurrentTermUnitType(unitTypes: List<String>, unitType: String): List<String>
    }
}