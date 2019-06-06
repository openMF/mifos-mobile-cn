package org.mifos.mobile.cn.data.local

import com.raizlabs.android.dbflow.sql.language.SQLite.select
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.data.models.customer.Customer_Table

import javax.inject.Inject


class DatabaseHelperCustomer @Inject constructor(){

    fun fetchCustomer(identifier: String): io.reactivex.Observable<Customer> {
        return io.reactivex.Observable.defer {
            var customer = select()
                    .from(Customer::class.java)
                    .where(Customer_Table.identifier.eq(identifier))
                    .querySingle()
            if (customer == null)
                customer = Customer()
            //else it will throw exception and will not go in the flatMap
            io.reactivex.Observable.just(customer!!)
        }

    }

}