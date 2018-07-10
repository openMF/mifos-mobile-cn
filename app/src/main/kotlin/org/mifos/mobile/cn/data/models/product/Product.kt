package org.mifos.mobile.cn.data.models.product

import com.google.gson.annotations.SerializedName
import org.mifos.mobile.cn.data.models.accounts.loan.AccountAssignment
import org.mifos.mobile.cn.data.models.accounts.loan.TermRange
import java.util.ArrayList


data class Product (
        @SerializedName("identifier")  var identifier: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("termRange") var termRange: TermRange? = null,
        @SerializedName("balanceRange") var balanceRange: BalanceRange? = null,
        @SerializedName("interestRange") var interestRange: InterestRange? = null,
        @SerializedName("interestBasis") var interestBasis: InterestBasis? = null,
        @SerializedName("patternPackage") var patternPackage: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("currencyCode") var currencyCode: String? = null,
        @SerializedName("minorCurrencyUnitDigits") var minorCurrencyUnitDigits: Int = 0,
        @SerializedName("accountAssignments") var accountAssignments: List<AccountAssignment> =
                ArrayList(),
        @SerializedName("parameters") var parameters: String? = null,
        @SerializedName("createdOn") var createdOn: String? = null,
        @SerializedName("createdBy") var createdBy: String? = null,
        @SerializedName("lastModifiedOn") var lastModifiedOn: String? = null,
        @SerializedName("lastModifiedBy") var lastModifiedBy: String? = null
)

