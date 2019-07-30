package org.mifos.mobile.cn.data.models.payment

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class PlannedPayment (
        @SerializedName("interestRate") var interestRate: Double? = null,
        @SerializedName("costComponents") var costComponents: List<CostComponent> = ArrayList(),
        @SerializedName("remainingPrincipal") var remainingPrincipal: Double? = null,
        @SerializedName("date") var date: String? = null
)