package org.mifos.mobile.cn.ui.mifos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.gson.Gson
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.qrgenerator.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

class QRGenerator: MifosBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrgenerator)
        setToolbarTitle(getString(R.string.home))
        getToolbar().setVisibility(View.GONE)
        generateQrCodeButton.setOnClickListener {
            if (checkEditText()) {
                hideKeyboard()
                val user = UserObject(name = fullNameEditText.text.toString(), mobile = mobileEditText.text.toString(),email = emailEditText.text.toString(),accountNumber = accountEditText.text.toString())
                val serializeString = Gson().toJson(user)
                setImageBitmap(serializeString)
            }
        }
    }

    private fun setImageBitmap(encryptedString: String?) {
        val bitmap = QRCodeHelper.newInstance(this)?.setContent(encryptedString)?.setErrorCorrectionLevel(ErrorCorrectionLevel.Q)?.setMargin(2)?.qRCOde
        qrCodeImageView.setImageBitmap(bitmap)
    }

    /**
     * Hides the soft input keyboard if it is shown to the screen.
     */

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun checkEditText(): Boolean {
        if (TextUtils.isEmpty(fullNameEditText.text.toString())) {
            Toast.makeText(this, "fullName field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        } else if (TextUtils.isEmpty(mobileEditText.text.toString())) {
            Toast.makeText(this, "Mobile number field cannot be empty!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}