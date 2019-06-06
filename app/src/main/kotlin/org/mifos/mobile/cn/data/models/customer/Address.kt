package org.mifos.mobile.cn.data.models.customer

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.raizlabs.android.dbflow.annotation.ConflictAction
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import kotlinx.android.parcel.Parcelize
import org.mifos.mobile.cn.local.MifosCnDatabase

@Parcelize
@Table(name = "Address", database = MifosCnDatabase::class, allFields = true, insertConflict =
ConflictAction.REPLACE)
data class Address(
    @PrimaryKey
    @SerializedName("street") var street: String? = null,
    @PrimaryKey
    @SerializedName("city") var city: String? = null,
    @PrimaryKey
    @SerializedName("region") var region: String? = null,
    @SerializedName("postalCode") var postalCode: String? = null,
    @SerializedName("countryCode") var countryCode: String? = null,
    @SerializedName("country") var country: String? = null
) : BaseModel(), Parcelable