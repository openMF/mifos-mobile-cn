package org.mifos.mobile.cn.data.models.accounts.loan

import com.google.gson.annotations.SerializedName

data class PaymentCycle (
        @SerializedName("temporalUnit") var temporalUnit: String? = null,
        @SerializedName("period") var period: Int? = null,
        @SerializedName("alignmentDay") var alignmentDay: Int? = null,
        @SerializedName("alignmentWeek") var alignmentWeek: Int? = null,
        @SerializedName("alignmentMonth") var alignmentMonth: Int? = null
)
