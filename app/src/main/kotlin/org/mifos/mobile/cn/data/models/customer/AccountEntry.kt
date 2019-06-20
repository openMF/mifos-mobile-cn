package org.mifos.mobile.cn.data.models.customer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AccountEntry (
    @SerializedName("type") val type: String,
    @SerializedName("transactionDate") val transactionDate: String? = null,
    @SerializedName("message") val message: String?=null,
    @SerializedName("amount") val amount : Double?=null,
    @SerializedName("balance") val balance: Double?=null
    ): Parcelable