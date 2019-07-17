package org.mifos.mobile.cn.fakesource

import androidx.lifecycle.LiveData
import com.google.gson.reflect.TypeToken
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.models.customer.identification.Identification
import org.mifos.mobile.cn.data.models.customer.identification.ScanCard
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.data.models.customer.AccountEntriesPage
import org.mifos.mobile.cn.data.models.customer.AccountEntry
import org.mifos.mobile.cn.data.models.payment.PlannedPaymentPage
import org.mifos.mobile.cn.data.models.product.Product
import org.mifos.mobile.cn.data.models.product.ProductPage
import java.lang.reflect.Type

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

        fun getDepositAccountsJson(): List<DepositAccount> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<DepositAccount>>() {},
                    FakeJsonName.DEPOSIT_ACCOUNTS)
        }

        fun getCustomerJson(): Customer {
            return testDataFactory.getListTypePojo(object : TypeToken<Customer>() {},
                    FakeJsonName.CUSTOMER)
        }

        fun getCustomerCommandJson(): List<Command> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<Command>>() {},
                    FakeJsonName.CUSTOMER_COMMANDS)
        }

        fun getIdentificationsJson(): List<Identification> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<Identification>>() {

            }, FakeJsonName.IDENTIFICATIONS)
        }

        fun getScanCards(): List<ScanCard> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<ScanCard>>() {

            }, FakeJsonName.SCAN_CARDS)
        }
        fun getRecentTransactions(): List<AccountEntry> {
            return testDataFactory.getListTypePojo(object : TypeToken<List<AccountEntry>>(){

            },FakeJsonName.ENTRIES)
        }

        fun getProductPage(): LiveData<ProductPage> {
            return testDataFactory.getObjectTypePojo(ProductPage::class.java,
                    FakeJsonName.PRODUCT_PAGE)
        }

        fun getloanAccount(): LiveData<LoanAccount> {
            return testDataFactory.getObjectTypePojo(LoanAccount::class.java,
                    FakeJsonName.LOAN_ACCOUNT)
        }

        fun getPlannedPaymentPage(): LiveData<PlannedPaymentPage> {
            return testDataFactory.getObjectTypePojo(PlannedPaymentPage::class.java,
                    FakeJsonName.PLANNED_PAYMENT_PAGE)

        }

    }
}
