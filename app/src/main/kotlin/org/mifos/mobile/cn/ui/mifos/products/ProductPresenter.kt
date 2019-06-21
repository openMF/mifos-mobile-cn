package org.mifos.mobile.cn.ui.mifos.products

import android.content.Context
import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.data.models.product.ProductPage
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter
import java.util.concurrent.Callable
import io.reactivex.functions.Predicate
import org.mifos.mobile.cn.ui.mifos.customerDetails.CustomerDetailsContract
import javax.inject.Inject

class ProductPresenter @Inject constructor(@ApplicationContext context: Context): BasePresenter<ProductContracts.View>(context),ProductContracts.Presenter {
    private lateinit var products: ProductPage


    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    override fun getProductsPage() {
        checkViewAttached()
        getMvpView.showProgressbar()
        Observable.fromCallable(Callable<LiveData<ProductPage>> {
            FakeRemoteDataSource.getProductPage()
        }).subscribe({
            getMvpView.hideProgressbar()
            getMvpView.showProduct(it)
        })
    }



    override fun searchProduct(products: List<Product>, query: String) {
        checkViewAttached()
        getMvpView.searchedProduct(Observable.fromIterable(products)
                .filter(object : Predicate<Product>{
                    override fun test(t: Product): Boolean {
                        return t.identifier?.toLowerCase()?.contains(query.toLowerCase())
                                .toString().toBoolean()
                    }
                }).toList().blockingGet())

    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.clear()
    }

}
