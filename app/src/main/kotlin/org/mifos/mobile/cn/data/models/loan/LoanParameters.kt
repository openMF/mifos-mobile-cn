package org.mifos.mobile.cn.data.models.loan

import com.google.gson.annotations.SerializedName

data class LoanParameters(
        @SerializedName("customerIdentifier") var customerIdentifier: String? = null,
        @SerializedName("creditWorthinessSnapshots") var creditWorthinessSnapshots:
        List<CreditWorthinessSnapshot>? = null,
        @SerializedName("maximumBalance") var maximumBalance: Double? = null,
        @SerializedName("termRange") var termRange: TermRange? = null,
        @SerializedName("paymentCycle") var paymentCycle: PaymentCycle? = null
)