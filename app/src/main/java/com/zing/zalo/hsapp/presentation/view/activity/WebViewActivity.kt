package com.zing.zalo.hsapp.presentation.view.activity

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding
    private lateinit var link: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLink()
        setupWebView()
    }

    private fun getLink() {
        link = intent.getStringExtra("url") ?: "https://www.google.com/"
        binding.link.setText(link)
    }

    private fun setupWebView() {
        binding.webView.run {
            loadUrl(link)
            webViewClient = WebViewClient()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_in1, R.anim.anim_out1)
    }
}