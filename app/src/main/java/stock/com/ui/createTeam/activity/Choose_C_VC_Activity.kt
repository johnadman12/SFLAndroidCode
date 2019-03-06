package stock.com.ui.createTeam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_choose_c_vc.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.createTeam.adapter.ChooseC_VC_Adapter
import stock.com.interfaces.OnClickCVC
import stock.com.model.PlayerData


class Choose_C_VC_Activity : BaseActivity(), View.OnClickListener, OnClickCVC {
    override fun onClick(tag: String, position: Int) {
        if (tag == "c") {
            for (i in playerList.indices) {
                playerList[i].isCaptain = false
            }
            if (playerList[position].isViceCaptain)
                playerList[position].isViceCaptain = false
            playerList[position].isCaptain = true
            adapter!!.notifyDataSetChanged()
        } else if (tag == "vc") {
            for (i in playerList.indices) {
                playerList[i].isViceCaptain = false
            }
            if (playerList[position].isCaptain)
                playerList[position].isCaptain = false
            playerList[position].isViceCaptain = true
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_CreateTeam -> {
                startActivity(Intent(this, TeamPreviewActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_c_vc)
        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("Choose Stock")
        setMenu(false, false, true, false, false,
            false, true)
        addData()
        btn_CreateTeam.setOnClickListener(this)
//        txt_Signup.setOnClickListener(this)
    }

    var playerList: MutableList<PlayerData> = ArrayList()
    fun addData() {
        for (i in 0..10) {
            var playerData = PlayerData()
            playerData.id = i.toString()
            playerData.isCaptain = false
            playerData.isViceCaptain = false
            playerList.add(playerData)
        }
        setAdapter()
    }

    var adapter: ChooseC_VC_Adapter? = null
    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        adapter = ChooseC_VC_Adapter(this, this, playerList)
        rv_Players!!.adapter = adapter
    }


}