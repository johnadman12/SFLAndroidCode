package stock.com.ui.addfunds.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class AddFundsActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_funds_activity)
        initViews()

    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("Add Funds")
        setMenu(false, false, false, false, false, false, false)
    }
}