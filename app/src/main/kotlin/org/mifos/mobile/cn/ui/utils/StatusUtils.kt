package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.widget.ImageView
import org.mifos.mobile.cn.data.models.customer.Command
import org.mifos.mobile.cn.data.models.customer.Customer
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.CheckboxStatus
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount
import java.util.ArrayList

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

object StatusUtils {
    fun setCustomerStatus(state: Customer.State, imageView: AppCompatImageView,
                          context: Context) {
        when (state) {
            Customer.State.ACTIVE -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.deposit_green))
            Customer.State.CLOSED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.black))
            Customer.State.LOCKED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.red_dark))
            Customer.State.PENDING -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.light_yellow))
        }
    }

    fun setCustomerStatusIcon(state: Customer.State, imageView: ImageView,
                              context: Context?) {
        when (state) {
            Customer.State.ACTIVE -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context!!,
                        R.drawable.ic_check_circle_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            Customer.State.CLOSED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context!!,
                        R.drawable.ic_close_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            Customer.State.LOCKED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context!!,
                        R.drawable.ic_lock_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            Customer.State.PENDING -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context!!,
                        R.drawable.ic_hourglass_empty_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.blue))
            }
        }
    }


    fun setLoanAccountStatus(state: LoanAccount.State, imageView: AppCompatImageView,
                             context: Context) {
        when (state) {
            LoanAccount.State.CREATED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.blue))
            LoanAccount.State.PENDING -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.blue))

            LoanAccount.State.APPROVED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.deposit_green))

            LoanAccount.State.ACTIVE -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.deposit_green))

            LoanAccount.State.CLOSED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.red_dark))
        }
    }

    fun setDepositAccountStatus(state: DepositAccount.State, imageView: AppCompatImageView,
                                context: Context) {

        when (state) {
            DepositAccount.State.CREATED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.blue))
            DepositAccount.State.PENDING -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.blue))

            DepositAccount.State.APPROVED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.deposit_green))

            DepositAccount.State.ACTIVE -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.deposit_green))

            DepositAccount.State.CLOSED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.red_dark))

            DepositAccount.State.LOCKED -> imageView.setColorFilter(
                    ContextCompat.getColor(context, R.color.red_dark)
            )
        }

    }

    fun setLoanAccountStatusIcon(state: LoanAccount.State, imageView: ImageView,
                                 context: Context) {
        when (state) {
            LoanAccount.State.ACTIVE -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_check_circle_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            LoanAccount.State.CLOSED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_close_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            LoanAccount.State.APPROVED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_done_all_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            LoanAccount.State.PENDING -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_hourglass_empty_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.blue))
            }
            LoanAccount.State.CREATED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_hourglass_empty_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.blue))
            }
        }
    }

    fun setDepositAccountStatusIcon(state: DepositAccount.State, imageView: ImageView,
                                    context: Context) {
        when (state) {
            DepositAccount.State.ACTIVE -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_check_circle_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            DepositAccount.State.CLOSED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_close_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            DepositAccount.State.APPROVED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_done_all_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            DepositAccount.State.PENDING -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_hourglass_empty_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.blue))
            }
            DepositAccount.State.CREATED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_hourglass_empty_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.blue))
            }
            DepositAccount.State.LOCKED -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_lock_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
        }
    }

    fun getLoanAccountsStatusList(context: Context): List<CheckboxStatus> {
        val arrayList = ArrayList<CheckboxStatus>()
        arrayList.add(CheckboxStatus(context.getString(R.string.created),
                ContextCompat.getColor(context, R.color.blue
                )))
        arrayList.add(CheckboxStatus(context.getString(R.string.pending),
                ContextCompat.getColor(context, R.color.blue)))
        arrayList.add(CheckboxStatus(context.getString(R.string.approved),
                ContextCompat.getColor(context, R.color.deposit_green)))
        arrayList.add(CheckboxStatus(context.getString(R.string.active),
                ContextCompat.getColor(context, R.color.deposit_green)))
        arrayList.add(CheckboxStatus(context.getString(R.string.closed),
                ContextCompat.getColor(context, R.color.red_dark)))

        return arrayList

    }

    fun getDepositAccountsStatusList(context: Context): List<CheckboxStatus> {
        val arrayList = ArrayList<CheckboxStatus>()
        arrayList.add(CheckboxStatus(context.getString(R.string.created),
                ContextCompat.getColor(context, R.color.blue
                )))
        arrayList.add(CheckboxStatus(context.getString(R.string.pending),
                ContextCompat.getColor(context, R.color.blue)))
        arrayList.add(CheckboxStatus(context.getString(R.string.approved),
                ContextCompat.getColor(context, R.color.deposit_green)))
        arrayList.add(CheckboxStatus(context.getString(R.string.active),
                ContextCompat.getColor(context, R.color.deposit_green)))
        arrayList.add(CheckboxStatus(context.getString(R.string.closed),
                ContextCompat.getColor(context, R.color.red_dark)))
        arrayList.add(CheckboxStatus(context.getString(R.string.locked),
                ContextCompat.getColor(context, R.color.red_dark)))

        return arrayList

    }

    fun setCustomerActivitiesStatusIcon(action: Command.Action, imageView: ImageView,
                                        context: Context) {
        when (action) {
            Command.Action.ACTIVATE -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_check_circle_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            Command.Action.CLOSE -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_close_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            Command.Action.LOCK -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_lock_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.red_dark))
            }
            Command.Action.UNLOCK -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_lock_open_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
            Command.Action.REOPEN -> {
                imageView.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.ic_lock_open_black_24dp))
                imageView.setColorFilter(
                        ContextCompat.getColor(context, R.color.status))
            }
        }
    }

}

