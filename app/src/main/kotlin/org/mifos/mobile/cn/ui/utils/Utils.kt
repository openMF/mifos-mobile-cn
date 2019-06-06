package org.mifos.mobile.cn.ui.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @author Rajan Maurya
 * On 28/05/18.
 */
class Utils {

    companion object {
        fun getPrecision(aDouble: Double?): String {
            return String.format(Locale.ENGLISH, "%.2f", aDouble)
        }

        fun <T> getStringToPoJo(listModel: TypeToken<T>, jsonName: String): T {
            return Gson().fromJson(jsonName, listModel.type)
        }
    }
}