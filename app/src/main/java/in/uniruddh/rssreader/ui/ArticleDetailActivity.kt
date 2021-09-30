package `in`.uniruddh.rssreader.ui

import `in`.uniruddh.rssreader.databinding.ActivityArticleDetailBinding
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

/**
 * @Author: Aniruddh Bhilvare
 * @Date:  30/September/2021
 * @Email: uniruddh@gmail.com
 */
class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleUrl = intent.getStringExtra("ArticleUrl")
        if (articleUrl != null) {
            binding.webview.loadUrl(articleUrl)
            binding.webview.settings.javaScriptEnabled = false
            binding.webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return true
                }
            }
        }

    }
}