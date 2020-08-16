package org.mifos.mobile.cn.data.models.entity

import com.google.gson.annotations.SerializedName

data class PartyIdentifiers(
        @SerializedName("identifiers")
        val identifierList: List<Identifier>? = null
)