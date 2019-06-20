package org.mifos.mobile.cn.data.models.customer

import com.google.gson.annotations.SerializedName

data class AccountEntriesPage (
    @SerializedName("accountEntries") val entries:List<AccountEntry>?=null,
    @SerializedName("totalPages") val totalPages: Int? = null,
    @SerializedName("totalElements") val totalElements: Long? = null
    )