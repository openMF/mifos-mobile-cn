package org.mifos.mobile.cn.data.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartyIdInfo(
        @SerializedName("partyIdType")
        var partyIdType: IdentifierType? = null,
        @SerializedName("partyIdentifier")
        var partyIdentifier: String? = null) : Parcelable