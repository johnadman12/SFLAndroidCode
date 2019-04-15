package stock.com.ui.notification.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_notifications.*
import kotlinx.android.synthetic.main.content_notifications.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.notification.adapter.NotificationAdapter

class NotificationActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)
        img_btn_back.setOnClickListener(this)
        initViews()
    }

    private fun initViews() {
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