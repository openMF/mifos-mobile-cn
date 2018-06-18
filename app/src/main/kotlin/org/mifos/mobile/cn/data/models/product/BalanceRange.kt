package org.mifos.mobile.cn.data.models.product

import com.google.gson.annotations.SerializedName

data class BalanceRange (
        @SerializedName("minimum") var minimum: Double? = null,
        @SerializedName("maximum") var maximum: Double? = null
)
