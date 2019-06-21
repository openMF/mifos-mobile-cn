package org.mifos.mobile.cn.data.models.product

import com.google.gson.annotations.SerializedName

data class ProductPage (
        @SerializedName("elements") val elements: List<Product>? = null,
        @SerializedName("totalPages") val totalPages: Int? = null,
        @SerializedName("totalElements") val totalElements: Long? = null
)