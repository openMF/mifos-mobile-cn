package org.mifos.mobile.cn.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Authentication(
        @SerializedName("tokenType") var tokenType: String,
        @SerializedName("accessToken") var accessToken: String,
        @SerializedName("accessTokenExpiration") var accessTokenExpiration: String,
        @SerializedName("refreshTokenExpiration") var refreshTokenExpiration: String,
        @SerializedName("passwordExpiration") var passwordExpiration: String
) : Parcelable
