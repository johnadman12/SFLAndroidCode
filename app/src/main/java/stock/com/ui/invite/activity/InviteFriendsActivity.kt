package stock.com.ui.invite.activity

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_invite_friends.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class InviteFriendsActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_join -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_friends)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.invite_friends)
        setMenu(false, false, false, false, false, false, false)
        txt_Invite.setOnClickListener(this)
        txt_label.setText("kick off your friends " + getString(R.string.app_name) + " Journey!")
    }

}