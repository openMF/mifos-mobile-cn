package org.mifos.mobile.cn.local

import com.raizlabs.android.dbflow.annotation.Database

/**
 * @author Manish Kumar
 * @since 31/July/2018
 */

@Database(name = MifosCnDatabase.NAME, version = MifosCnDatabase.VERSION, foreignKeyConstraintsEnforced = true)
class MifosCnDatabase {
    companion object {
        // database name will be Mifos.db
        const val NAME = "MifosCn"
        //Always Increase the Version Number
        const val VERSION = 1
    }
}