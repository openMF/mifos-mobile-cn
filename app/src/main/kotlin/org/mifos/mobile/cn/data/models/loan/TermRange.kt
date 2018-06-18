package org.mifos.mobile.cn.data.models.loan

import com.google.gson.annotations.SerializedName

data class TermRange(
        @SerializedName("temporalUnit") var temporalUnit: String?,
        @SerializedName("maximum") var maximum: Double?
)
