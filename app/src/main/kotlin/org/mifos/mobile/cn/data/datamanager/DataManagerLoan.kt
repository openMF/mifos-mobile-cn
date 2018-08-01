package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Observable
import org.mifos.mobile.cn.data.databasehelper.DataBaseHelperLoan
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Manish Kumar
 * @since 31/July/2018
 */

@Singleton
class DataManagerLoan @Inject constructor(var databBaseHelperLoan: DataBaseHelperLoan) {

    fun saveLoanResponse(loanAccount: LoanAccount) :Observable<LoanAccount> {
        return  databBaseHelperLoan.saveLoanResponse(loanAccount)
    }

}