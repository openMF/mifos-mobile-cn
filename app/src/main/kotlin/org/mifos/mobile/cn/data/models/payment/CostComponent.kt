package org.mifos.mobile.cn.data.models.payment

import com.google.gson.annotations.SerializedName

data class CostComponent (
        @SerializedName("chargeIdentifier") var chargeIdentifier: String? = null,
        @SerializedName("amount") var amount: Double? = null
)