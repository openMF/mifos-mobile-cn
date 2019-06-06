package org.mifos.mobile.cn.data.models.customer.identification

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScanCard(
    @SerializedName("description") var description: String,
    @SerializedName("identifier") var identifier: String
) : Parcelable