package org.mifos.mobile.cn.ui.mifos.privacypolicy

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import org.mifos.mobile.cn.R
import org.mifos.mobile.cn.ui.base.MifosBaseActivity

@SuppressLint("Registered")
class PrivacyPolicyActivity: MifosBaseActivity() {
    private var showOrHideWebViewInitialUse = "show"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        setToolbarTitle(getString(R.string.privacy_policy))
        showBackButton()

        // Force links and redirects to open in the WebView instead of in a browser
        webView.webViewClient = WebViewClient()

        // Enable Javascript
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // REMOTE RESOURCE
        webView.settings.domStorageEnabled = true
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.loadUrl(getString(R.string.privacy_policy_host_url))
        webView.webViewClient = MyWebViewClient()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    internal inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (Uri.parse(url).host!!
                            .endsWith(getString(R.string.privacy_policy_host))) {
                return false
            }
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            showOrHideWebViewInitialUse = "hide"
            progress_bar.visibility = View.GONE
            view.visibility = View.VISIBLE
            super.onPageFinished(view, url)
        }
    }
}