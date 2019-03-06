package stock.com.ui.signup.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class WebUrlActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_url)
        StockConstant.ACTIVITIES.add(this)
        toolbarTitleTv.setText("T&Cs and Privacy Policy")
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}