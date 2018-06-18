package org.mifos.mobile.cn.ui.utils

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import org.mifos.mobile.cn.R

/**
 * @author Rajan Maurya
 * On 26/05/18.
 */
class ValidationUtil {

    companion object {

        fun validateTextFields(editText: EditText, textInputLayout: TextInputLayout,
                               message: String): Boolean {
            if (TextUtils.isEmpty(editText.text.toString().trim())) {
                showTextInputLayoutError(textInputLayout, message)
                return true
            }
            hideTextInputLayoutError(textInputLayout)
            return false
        }

        fun validateSpinners(rootView: View, spinner: Spinner, message: String): Boolean {
            if (spinner.selectedItemPosition == 0) {
                Toaster.show(rootView, message)
                return true
            }
            return false
        }

        fun showTextInputLayoutError(textInputLayout: TextInputLayout, errorMessage: String) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = errorMessage
        }

        fun hideTextInputLayoutError(textInputLayout: TextInputLayout) {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
        }

        fun isEmpty(context: Context, string: String, inputLayout: TextInputLayout): Boolean {
            if (TextUtils.isEmpty(string)) {
                ValidateIdentifierUtil.showTextInputLayoutError(inputLayout,
                        context.getString(R.string.required))
                return false
            }
            hideTextInputLayoutError(inputLayout)
            return true
        }
    }
}
