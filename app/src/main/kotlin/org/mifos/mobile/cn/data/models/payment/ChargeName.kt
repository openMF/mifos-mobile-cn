package org.mifos.mobile.cn.data.models.payment

import com.google.gson.annotations.SerializedName

data class ChargeName (
        @SerializedName("identifier") var identifier: String? = null,
        @SerializedName("name") var name: String? = null
)