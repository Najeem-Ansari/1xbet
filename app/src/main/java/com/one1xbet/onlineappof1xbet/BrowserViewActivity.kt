package com.one1xbet.onlineappof1xbet

import android.app.Activity
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class BrowserViewActivity : Activity() {
    var webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browers_inapp)
        init()
        loadData()
    }

    private fun init() {
        webView = findViewById<View>(R.id.webView) as WebView?
    }

    private fun loadData() {
        setTitle("WebView")
        webView!!.setInitialScale(1)
        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.domStorageEnabled = true
        webView!!.webChromeClient = WebChromeClient()
        webView!!.settings.useWideViewPort = true
        //android 6.0
        webView!!.webViewClient = WebViewClient()
        webView!!.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webView!!.isScrollbarFadingEnabled = false
        webView!!.loadUrl(intent.getStringExtra("URL").toString())
    }

   override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (webView!!.canGoBack()) {
                        webView!!.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}
