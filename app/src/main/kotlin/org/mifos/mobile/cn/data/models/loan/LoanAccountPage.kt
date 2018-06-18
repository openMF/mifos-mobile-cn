package org.mifos.mobile.cn.data.models.loan

import com.google.gson.annotations.SerializedName

data class LoanAccountPage(
        @SerializedName("elements") var loanAccounts: List<LoanAccount>? = null,
        @SerializedName("totalPages") var totalPages: Int? = null,
        @SerializedName("totalElements") var totalElements: Long? = null
)