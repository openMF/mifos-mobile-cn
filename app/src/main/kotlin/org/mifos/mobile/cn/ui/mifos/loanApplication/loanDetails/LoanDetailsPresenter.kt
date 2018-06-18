package org.mifos.mobile.cn.ui.mifos.loanApplication.loanDetails

import android.content.Context
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import javax.inject.Inject

class LoanDetailsPresenter @Inject constructor(@ApplicationContext context: Context)
    : BasePresenter<LoanDetailsContract.View>(context), LoanDetailsContract.Presenter {

    private var products: List<Product>

    init {
        products = ArrayList()
    }

    override fun fetchProducts() {
        checkViewAttached()
        getMvpView.showProgress()
        Observable.fromCallable(Callable<List<Product>> {
            FakeRemoteDataSource.getProductsJson()
        }).subscribe({
            products = it
            getMvpView.hideProgress()
            if (it.isEmpty()) {
                getMvpView.showEmptyProducts()
            }
            getMvpView.showProducts(filterProducts(it))
        })
    }

    override fun filterProducts(products: List<Product>): List<String> {
        return Observable.fromIterable(products)
                .map(Function<Product, String> { product -> product.name })
                .toList()
                .blockingGet()
    }


    override fun setProductPositionAndValidateViews(position: Int?) {
        getMvpView.setComponentsValidations(products[position!!])
    }

    override fun getCurrentTermUnitType(unitTypes: List<String>, unitType: String): List<String> {
        return Observable.fromIterable(unitTypes).filter { s -> s == unitType.toLowerCase() }
                .toList()
                .blockingGet()
    }

    override fun attachView(mvpView: LoanDetailsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
    }

}