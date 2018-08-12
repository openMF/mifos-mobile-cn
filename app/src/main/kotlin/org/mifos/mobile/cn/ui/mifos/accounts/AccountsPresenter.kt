package org.mifos.mobile.cn.ui.mifos.accounts

import android.content.Context
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import org.mifos.mobile.cn.fakesource.FakeRemoteDataSource
import org.mifos.mobile.cn.injection.ApplicationContext
import org.mifos.mobile.cn.ui.base.BasePresenter

import javax.inject.Inject

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

class AccountsPresenter @Inject constructor(@ApplicationContext context: Context) :
        BasePresenter<AccountsContract.View>(context), AccountsContract.Presenter {

    override fun attachView(mvpView: AccountsContract.View) {
        super.attachView(mvpView)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun loadDepositAccounts() {
        checkViewAttached()
        getMvpView.showProgress()
        Observable.fromCallable({ FakeRemoteDataSource.getDepositAccountsJson() }).subscribe({
            if (it.isEmpty()) {
                getMvpView.showEmptyAccounts(context.getString(R.string.deposit))
            } else {
                getMvpView.showDepositAccounts(it)
            }
        })

    }

    override fun loadLoanAccounts() {
        checkViewAttached()
        getMvpView.showProgress()
        Observable.fromCallable({ FakeRemoteDataSource.getLoanAccountsJson() }).subscribe({
            getMvpView.hideProgress()
            if (it.isEmpty()) {
                getMvpView.showEmptyAccounts(context.getString(R.string.loan))
            } else {
                getMvpView.showLoanAccounts(it)
            }
        })
    }

    /**
     * Filters [List] of [CheckboxStatus]
     * @param statusModelList [List] of [CheckboxStatus]
     * @return Returns [List] of [CheckboxStatus] which have
     * `checkboxStatus.isChecked()` as true.
     */
    fun getCheckedStatus(statusModelList: List<CheckboxStatus>): List<CheckboxStatus> {
        return Observable.fromIterable<CheckboxStatus>(statusModelList)
                .filter { checkboxStatus -> checkboxStatus.isChecked }.toList().blockingGet()
    }

    /**
     * Filters [List] of [LoanAccount] according to [CheckboxStatus]
     * @param accounts [List] of filtered [LoanAccount]
     * @param status Used for filtering the [List]
     * @return Returns [List] of filtered [LoanAccount] according to the
     * `status` provided.
     */
    fun getFilteredLoanAccount(accounts: List<LoanAccount>,
                               status: CheckboxStatus): List<LoanAccount> {
        return Observable.fromIterable<LoanAccount>(accounts)
                .filter(Predicate<LoanAccount> { account ->
                    if (status.status?.compareTo(context.getString(R.string.pending)) == 0
                            && account.currentState == LoanAccount.State.PENDING) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.created)) == 0
                            && account.currentState == LoanAccount.State.CREATED) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.approved)) == 0
                            && account.currentState == LoanAccount.State.APPROVED) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.active)) == 0
                            && account.currentState == LoanAccount.State.ACTIVE) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.closed)) == 0
                            && account.currentState == LoanAccount.State.CLOSED) {
                        return@Predicate true
                    }
                    false
                }).toList().blockingGet()
    }

    /**
     * Filters [List] of [LoanAccount] according to [CheckboxStatus]
     * @param accounts [List] of filtered [DepositAccount]
     * @param status Used for filtering the [List]
     * @return Returns [List] of filtered [DepositAccount] according to the
     * `status` provided.
     */
    fun getFilteredDepositAccount(accounts: List<DepositAccount>,
                                  status: CheckboxStatus): List<DepositAccount> {
        return Observable.fromIterable<DepositAccount>(accounts)
                .filter(Predicate<DepositAccount> { account ->
                    if (status.status?.compareTo(context.getString(R.string.pending)) == 0
                            && account.state == DepositAccount.State.PENDING) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.created)) == 0
                            && account.state == DepositAccount.State.CREATED) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.approved)) == 0
                            && account.state == DepositAccount.State.APPROVED) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.active)) == 0
                            && account.state == DepositAccount.State.ACTIVE) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.closed)) == 0
                            && account.state == DepositAccount.State.CLOSED) {
                        return@Predicate true
                    } else if (status.status?.compareTo(context.getString(R.string.locked)) == 0
                            && account.state == DepositAccount.State.LOCKED) {
                        return@Predicate true
                    }
                    false
                }).toList().blockingGet()
    }

    /**
     * Filters [List] of [LoanAccount]
     * @param accounts [List] of [LoanAccount]
     * @param input [String] which is used for filtering
     * @return Returns [List] of filtered [LoanAccount] according to the `input`
     * provided.
     */
    fun searchInLoanList(accounts: List<LoanAccount>,
                         input: String): List<LoanAccount> {
        return Observable.fromIterable(accounts)
                .filter { loanAccount -> loanAccount.identifier?.toLowerCase()!!
                        .contains(input.toLowerCase()) }.toList().blockingGet()
    }

    /**
     * Filters [List] of [LoanAccount]
     * @param accounts [List] of [LoanAccount]
     * @param input [String] which is used for filtering
     * @return Returns [List] of filtered [LoanAccount] according to the `input`
     * provided.
     */
    fun searchInDepositList(accounts: List<DepositAccount>,
                         input: String): List<DepositAccount> {
        return Observable.fromIterable(accounts)
                .filter { depositAccount -> depositAccount.accountIdentifier?.toLowerCase()!!
                        .contains(input.toLowerCase()) }.toList().blockingGet()
    }
}