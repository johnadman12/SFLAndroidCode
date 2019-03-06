package stock.com.ui.dashboard.more.activity


import android.os.Bundle
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class WebViewActivity : BaseActivity() {

    var slug: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        slug = intent.getStringExtra("PAGE_SLUG")
        initView()
    }


    override fun onBackPressed() {
        finish()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        toolbarTitleTv.setText(slug);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }


}
