package org.mifos.mobile.cn.data.models.entity

import com.google.gson.annotations.SerializedName

data class RegistrationEntity(
        @SerializedName("accountId")
        val accountNumber: String?= null,
        @SerializedName("idType")
        val idType: IdentifierType?= null,
        @SerializedName("idValue")
        val idValue: String?= null)