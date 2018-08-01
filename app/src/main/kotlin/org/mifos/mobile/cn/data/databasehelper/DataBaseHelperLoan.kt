package org.mifos.mobile.cn.data.databasehelper

import io.reactivex.Observable
import org.mifos.mobile.cn.data.dbmodel.LoanResponse
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import javax.inject.Inject


/**
 * @author Manish Kumar
 * @since 31/July/2018
 */
class DataBaseHelperLoan @Inject constructor() {

    fun saveLoanResponse(loanAccount: LoanAccount): Observable<LoanAccount> {
        return Observable.defer {
            val loanResponse = LoanResponse()
            loanResponse.createdBy = loanAccount.createdBy
            loanResponse.createdOn = loanAccount.createdOn
            loanResponse.lastModifiedOn = loanAccount.lastModifiedOn
            loanResponse.lastModifiedBy = loanAccount.lastModifiedBy
            loanResponse.identifier = loanAccount.identifier
            loanResponse.parameters = loanAccount.parameters
            loanResponse.currentState = loanAccount.currentState?.name
            loanResponse.productIdentifier = loanAccount.productIdentifier
            loanResponse.save()
            return@defer Observable.just(LoanAccount())
        }
    }

}