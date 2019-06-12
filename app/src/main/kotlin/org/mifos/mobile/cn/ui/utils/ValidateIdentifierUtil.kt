package org.mifos.mobile.cn.ui.utils

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import android.text.TextUtils
import org.mifos.mobile.cn.R
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object ValidateIdentifierUtil {

    fun isValid(context: Context, string: String, textInputLayout: TextInputLayout): Boolean {
        if (TextUtils.isEmpty(string)) {
            showTextInputLayoutError(textInputLayout, context.getString(R.string.unique_required))
            return false
        }
        return validate(context, string, textInputLayout)
    }

    private fun validate(context: Context, string: String,
                         textInputLayout: TextInputLayout): Boolean {
        if (string.length < 3) {
            showTextInputLayoutError(textInputLayout,
                    context.getString(R.string.must_be_at_least_three_characters, 3))
            return false
        }

        if (string.length > 32) {
            showTextInputLayoutError(textInputLayout,
                    context.getString(R.string.only_thirty_two_character_allowed))
            return false
        }

        try {
            if (encode(string) == string) {
                showTextInputLayoutError(textInputLayout, null)
                return true
            } else {
                showTextInputLayoutError(textInputLayout, context.getString(
                        R.string.only_alphabetic_decimal_digits_characters_allowed))
                return false //If we can't encode with UTF-8, then there are no valid names.
            }
        } catch (e: UnsupportedEncodingException) {
            showTextInputLayoutError(textInputLayout,
                    context.getString(R.string.only_alphabetic_decimal_digits_characters_allowed))
            return false //If we can't encode with UTF-8, then there are no valid names.
        }

    }

    @Throws(UnsupportedEncodingException::class)
    private fun encode(identifier: String): String {
        return URLEncoder.encode(identifier, "UTF-8")
    }

    fun showTextInputLayoutError(textInputLayout: TextInputLayout,
                                 errorMessage: String?) {
        textInputLayout.isErrorEnabled = true
        textInputLayout.error = errorMessage
    }
}
