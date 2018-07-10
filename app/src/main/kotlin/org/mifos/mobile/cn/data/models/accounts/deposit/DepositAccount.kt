package org.mifos.mobile.cn.data.models.accounts.deposit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class DepositAccount(
        @SerializedName("customerIdentifier") var customerIdentifier: String? = null,
        @SerializedName("productIdentifier") var productIdentifier: String? = null,
        @SerializedName("accountIdentifier") var accountIdentifier: String? = null,
        @SerializedName("beneficiaries") var beneficiaries: List<String> = ArrayList(),
        @SerializedName("state") var state: State? = null,
        @SerializedName("balance") var balance: Double? = null
) : Parcelable {

    enum class State {

        @SerializedName("CREATED")
        CREATED,

        @SerializedName("PENDING")
        PENDING,

        @SerializedName("APPROVED")
        APPROVED,

        @SerializedName("ACTIVE")
        ACTIVE,

        @SerializedName("LOCKED")
        LOCKED,

        @SerializedName("CLOSED")
        CLOSED
    }

}
