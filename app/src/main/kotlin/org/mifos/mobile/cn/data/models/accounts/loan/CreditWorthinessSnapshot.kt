package org.mifos.mobile.cn.data.models.accounts.loan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class CreditWorthinessSnapshot(
        @SerializedName("forCustomer") var forCustomer: String? = null,
        @SerializedName("incomeSources") var incomeSources: List<CreditWorthinessFactor> =
                ArrayList(),
        @SerializedName("assets") var assets: List<CreditWorthinessFactor> = ArrayList(),
        @SerializedName("debts") var debts: List<CreditWorthinessFactor> = ArrayList()
) : Parcelable