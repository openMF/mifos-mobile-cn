package org.mifos.mobile.cn.data.models.accounts.loan

import com.google.gson.annotations.SerializedName

data class AccountAssignment (
        @SerializedName("designator") var designator: String? = null,
        @SerializedName("accountIdentifier") var accountIdentifier: String? = null,
        @SerializedName("ledgerIdentifier") var ledgerIdentifier: String? = null
)