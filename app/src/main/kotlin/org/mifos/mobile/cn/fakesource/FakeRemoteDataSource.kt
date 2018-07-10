package org.mifos.mobile.cn.fakesource

import com.google.gson.reflect.TypeToken
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
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

        fun getLoanAccountsJson(): List<LoanAccount> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<LoanAccount>>() {},
                    FakeJsonName.LOAN_ACCOUNTS)
        }

        fun getDepositAccountsJson():List<DepositAccount> {
            return testDataFactory.getListTypePojo(object :TypeToken<List<DepositAccount>>() {},
                    FakeJsonName.DEPOSIT_ACCOUNTS)
        }
    }
}
