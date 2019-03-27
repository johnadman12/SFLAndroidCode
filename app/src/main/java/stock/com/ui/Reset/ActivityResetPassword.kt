package stock.com.ui.Reset

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.utils.StockConstant

class ActivityResetPassword : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {
            startActivity(Intent(this@ActivityResetPassword, DashBoardActivity::class.java))
        }

    }
}