package stock.com.ui.invite.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_invitecode.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class InviteCodeActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_join -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invitecode)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.invite_code)
//        setMenu(false, false, false, false, false, false, false)
        btn_join.setOnClickListener(this)
    }

}