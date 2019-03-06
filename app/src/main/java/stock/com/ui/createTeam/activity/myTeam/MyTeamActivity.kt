package stock.com.ui.createTeam.activity.myTeam

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_contest.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.createTeam.adapter.MyTeamAdapter

class MyTeamActivity : BaseActivity(), View.OnClickListener {
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
        setContentView(R.layout.activity_myteam)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.my_team)
        setMenu(true,false,false,false, false, false, false)
        setAdapter()
//        btn_CreateTeam.setOnClickListener(this)
//        txt_Signup.setOnClickListener(this)
    }


    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Contest!!.layoutManager = llm
        rv_Contest!!.adapter = MyTeamAdapter(this)
    }
}