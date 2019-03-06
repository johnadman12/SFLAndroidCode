package stock.com.ui.notification.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.content_notifications.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.notification.adapter.NotificationAdapter

class NotificationActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
//        when (view!!.id) {
//            R.id.btn_CreateTeam -> {
//            startActivity(Intent(this, ChooseTeamActivity::class.java))
//        }
//            R.id.txt_Signup -> {
//                startActivity(Intent(this, SignUpActivity::class.java))
//            }
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.notification)
        setMenu(false,false,false,false, false, false, false)
        setAdapter()
//        btn_CreateTeam.setOnClickListener(this)
//        txt_Signup.setOnClickListener(this)
    }


    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Notifications!!.layoutManager = llm
        rv_Notifications!!.adapter = NotificationAdapter(this)
    }
}