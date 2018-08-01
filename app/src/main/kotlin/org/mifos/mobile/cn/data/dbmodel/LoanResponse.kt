package org.mifos.mobile.cn.data.dbmodel

import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import org.mifos.mobile.cn.local.MifosCnDatabase

/**
 * @author Manish Kumar
 * @since 31/July/2018
 */

@Parcelize
@Table(name = "LoanResponse", database = MifosCnDatabase::class)
data class LoanResponse(
        @Column var identifier: String? = null,
        @Column var productIdentifier: String? = null,
        @Column var parameters: String? = null,
        @Column var currentState: String? = null,
        @Column var createdOn: String? = null,
        @Column var createdBy: String? = null,
        @Column var lastModifiedOn: String? = null,
        @Column var lastModifiedBy: String? = null

) : Parcelable, BaseModel() {
    @PrimaryKey(autoincrement = true)
    @IgnoredOnParcel
    var id: Long = 0
}