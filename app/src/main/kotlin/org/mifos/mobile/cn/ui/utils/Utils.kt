package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.view.Menu
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * @author Rajan Maurya
 * On 28/05/18.
 */
class Utils {

    companion object {
        fun dpToPx(dp: Int): Int {
            return ((dp * Resources.getSystem().displayMetrics.density).toInt());
        }

        fun pxToDp(px: Int, context: Context): Int {
            return ((px / Resources.getSystem().displayMetrics.density).toInt());
        }
        fun getPrecision(aDouble: Double?): String {
            return String.format(Locale.ENGLISH, "%.2f", aDouble)
        }

        fun <T> getStringToPoJo(listModel: TypeToken<T>, jsonName: String): T {
            return Gson().fromJson(jsonName, listModel.type)
        }

        fun setToolbarIconColor(context: Context, menu: Menu, color: Int) {
            for (i in 0 until menu.size()) {
                val drawable = menu.getItem(i).icon
                if (drawable != null) {
                    drawable.mutate()
                    drawable.setColorFilter(
                            ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
                }
            }
        }
    }
}