package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.widget.ImageView
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.data.models.accounts.deposit.DepositAccount
import org.mifos.mobile.cn.data.models.accounts.loan.LoanAccount

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

object StatusUtils {


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


}

