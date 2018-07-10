package org.mifos.mobile.cn.data.models.accounts.loan

import com.google.gson.annotations.SerializedName

data class TermRange(
        @SerializedName("temporalUnit") var temporalUnit: String?,
        @SerializedName("maximum") var maximum: Double?
)
