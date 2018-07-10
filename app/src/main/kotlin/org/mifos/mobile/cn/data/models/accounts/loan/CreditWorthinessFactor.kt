package org.mifos.mobile.cn.data.models.accounts.loan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditWorthinessFactor(
        @SerializedName("description") var description: String? = null,
        @SerializedName("amount") var amount: Double? = null
) : Parcelable