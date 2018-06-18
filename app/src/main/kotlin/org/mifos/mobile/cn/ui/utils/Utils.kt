package org.mifos.mobile.cn.ui.utils

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
    }
}