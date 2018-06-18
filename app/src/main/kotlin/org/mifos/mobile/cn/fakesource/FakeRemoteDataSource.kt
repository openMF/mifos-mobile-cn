package org.mifos.mobile.cn.fakesource

import com.google.gson.reflect.TypeToken
import org.mifos.mobile.cn.data.models.product.Product

/**
 * FakeRemoteDataSource is reading the local json files into the java object using gson.
 * Created by Rajan Maurya on 25/6/17.
 */
class FakeRemoteDataSource {

    companion object {

        private val testDataFactory = TestDataFactory()

        /*fun getTestJson(): TestJson {
            return testDataFactory.convertJsonToDataObject(object : TypeToken<TestJson>() {
            }, FakeJsonName.TEST_JSON)
        }*/

        fun getProductsJson(): List<Product> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<Product>>() {
            }, FakeJsonName.PRODUCTS)
        }
    }
}
