package org.mifos.mobile.cn.data.models.accounts.loan

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class LoanAccount(
        @SerializedName("identifier") var identifier: String? = null,
        @SerializedName("productIdentifier")  var productIdentifier: String? = null,
        @SerializedName("parameters") var parameters: String? = null,
        @SerializedName("accountAssignments") var accountAssignments: List<AccountAssignment> =
                ArrayList(),
        @SerializedName("currentState") var currentState: State? = null,
        @SerializedName("createdOn") var createdOn: String? = null,
        @SerializedName("createdBy") var createdBy: String? = null,
        @SerializedName("lastModifiedOn") var lastModifiedOn: String? = null,
        @SerializedName("lastModifiedBy") var lastModifiedBy: String? = null
) {

    private val loanParameters: LoanParameters? = null

    enum class State {

        @SerializedName("CREATED")
        CREATED,

        @SerializedName("PENDING")
        PENDING,

        @SerializedName("APPROVED")
        APPROVED,

        @SerializedName("ACTIVE")
        ACTIVE,

        @SerializedName("CLOSED")
        CLOSED
    }

    enum class RepayUnitType {

        @SerializedName("WEEKS")
        WEEKS,

        @SerializedName("MONTHS")
        MONTHS,

        @SerializedName("YEARS")
        YEARS
    }

    fun getLoanParameters(): LoanParameters {
        return Gson().fromJson(parameters, LoanParameters::class.java)
    }

}
