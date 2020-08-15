package org.mifos.mobile.cn.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import org.mifos.mobile.cn.ui.utils.ProgressBarHandler


open class MifosBaseFragment : Fragment() {

    private lateinit var callback: BaseActivityCallback
    private lateinit var progressBarHandler: ProgressBarHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBarHandler = ProgressBarHandler(activity!!)
    }

    fun getToolbar() : Toolbar {
        return callback.getToolbar()
    }

    fun showProgressDialog(message: String) {
        callback.showJusticeProgressDialog(message)
    }

    fun hideProgressDialog() {
        callback.hideJusticeProgressDialog()
    }

    fun hideKeyboard(view: View, context: Context) {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager
                .RESULT_UNCHANGED_SHOWN)
    }

    fun setToolbarTitle(title: String) {
        callback.setToolbarTitle(title)
        hideTabLayout()
    }

    fun parent(): MifosBaseActivity {
        return activity as MifosBaseActivity
    }

    fun showProgressBar() {
        progressBarHandler.show()
    }

    fun hideProgressBar() {
        progressBarHandler.hide()
    }

    fun showTabLayout() {
        callback.showTabLayout(true)
    }

    fun hideTabLayout() {
        callback.showTabLayout(false)
    }

     override fun onAttach(context: Context?) {
        super.onAttach(context)
        val activity = context as? Activity
        try {
            callback = activity as BaseActivityCallback
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() +
                    " must implement BaseActivityCallback methods")
        }
    }
}