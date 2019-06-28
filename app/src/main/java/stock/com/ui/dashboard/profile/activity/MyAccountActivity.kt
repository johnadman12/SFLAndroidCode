package stock.com.ui.dashboard.profile.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_my_account.*
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class MyAccountActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.cv_transactions -> {
//            startActivity(Intent(this, ChooseTeamActivity::class.java))
            }
            R.id.cv_payments -> {
//                startActivity(Intent(this, SignUpActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.my_account)
//        setMenu(false, false, false, false, false, false,  false)
        cv_transactions.setOnClickListener(this)
        cv_payments.setOnClickListener(this)
    }


}