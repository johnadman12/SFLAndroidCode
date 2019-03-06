package stock.com.ui.contest.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_create_contest.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class CreateContestActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_CreateContest -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contest)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.create_contest)
        setMenu(false, false,false, false, false, false, false)
        btn_CreateContest.setOnClickListener(this)
    }

}