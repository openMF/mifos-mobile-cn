package org.mifos.mobile.cn.ui.utils

import android.graphics.Color
import com.google.android.material.snackbar.Snackbar
import android.util.Log
import android.view.View
import android.widget.TextView
import org.mifos.mobile.cn.MifosApplication

object Toaster {

    val LOG_TAG = Toaster::class.java.simpleName

    val INDEFINITE = Snackbar.LENGTH_INDEFINITE
    val LONG = Snackbar.LENGTH_LONG
    val SHORT = Snackbar.LENGTH_SHORT

    private var hideSnackbad: Snackbar? = null

    @JvmOverloads
    fun show(view: View, text: String, duration: Int = Snackbar.LENGTH_LONG) {
        val snackbar = Snackbar.make(view, text, duration)
        val sbView = snackbar.view
        val textView = sbView.findViewById<View>(com.google.android.material.R.id
                .snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 12f
        textView.maxLines = 5
        snackbar.setAction("OK") { snackbar.dismiss() }
        snackbar.show()
        hideSnackbad =  snackbar
    }

    fun showProgressMessage(view: View, text: String, duration: Int) {
        val snackbar = Snackbar.make(view, text, duration)
        val sbView = snackbar.view
        val textView = sbView.findViewById<View>(com.google.android.material.R.id
                .snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        textView.textSize = 12f
        snackbar.show()
        hideSnackbad = snackbar
    }

    fun hideSnackbar() {
        try {
            hideSnackbad!!.dismiss()
        } catch (e: NullPointerException) {
            Log.d(LOG_TAG, e.toString())
        }
    }

    fun show(view: View, res: Int, duration: Int) {
        show(view, MifosApplication.getContext().resources.getString(res), duration)
    }

    fun show(view: View, res: Int) {
        show(view, MifosApplication.getContext().resources.getString(res))
    }
}