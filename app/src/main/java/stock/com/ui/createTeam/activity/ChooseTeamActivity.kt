package stock.com.ui.createTeam.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_choose_team.*
import stock.com.AppBase.BaseActivity
import stock.com.BuildConfig
import stock.com.ui.createTeam.adapter.PlayerListAdapter
import android.app.AlertDialog
import stock.com.R


class ChooseTeamActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_wk -> {
                playerTypeSelector(WK)
            }
            R.id.img_ar -> {
                playerTypeSelector(AR)
            }
            R.id.img_bat -> {
                playerTypeSelector(BAT)
            }
            R.id.img_bowler -> {
                playerTypeSelector(BOWLER)
            }
            R.id.btn_Next -> {
                nextdialog()
               // startActivity(Intent(this, Choose_C_VC_Activity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_team)
        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("$50K/NASDAQ")
        setMenu(false, false, false, false, false, true, false)
        setAdapter()
        img_wk.setOnClickListener(this)
        img_ar.setOnClickListener(this)
        img_bat.setOnClickListener(this)
        img_bowler.setOnClickListener(this)
        playerTypeSelector(WK)
        btn_Next.setOnClickListener(this)
        if (BuildConfig.APPLICATION_ID == "os.real11" || BuildConfig.APPLICATION_ID == "os.cashfantasy") {
            txt_substitute.visibility = VISIBLE
            txt_PickPlayer.gravity = Gravity.START
        }
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Player!!.layoutManager = llm
        rv_Player!!.adapter = PlayerListAdapter(this)
    }


    private var WK = 1
    private var BAT = 2
    private var AR = 3
    private var BOWLER = 4


    fun playerTypeSelector(value: Int) {
        txt_WK.isSelected = false
        txt_AR.isSelected = false
        txt_BAT.isSelected = false
        txt_BOWLER.isSelected = false


        img_wk.isSelected = false
        img_ar.isSelected = false
        img_bat.isSelected = false
        img_bowler.isSelected = false


        when (value) {
            WK -> {
                txt_WK.isSelected = true
                img_wk.isSelected = true
                txt_PickPlayer.setText(R.string.pick_1_wicket_keeper)
            }
            BAT -> {
                txt_BAT.isSelected = true
                img_bat.isSelected = true
                txt_PickPlayer.setText(R.string.pick_3_5_batsmen)
            }
            AR -> {
                txt_AR.isSelected = true
                img_ar.isSelected = true
                txt_PickPlayer.setText(R.string.pick_1_3_allrounder)
            }
            BOWLER -> {
                img_bowler.isSelected = true
                txt_BOWLER.isSelected = true
               txt_PickPlayer.setText(R.string.pick_3_5_bowler)
            }

        }

    }

    fun nextdialog() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.next_button_dailog)
        val no: Button = dialog.findViewById(R.id.cancle_btn)
        no.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    /*private fun nextdialog() {
        val inflater = layoutInflater
        val alertLayout = inflater.inflate(R.layout.next_button_dailog, null)
        val cancle_btn : Button = findViewById(R.id.cancle_btn)

        val alert = AlertDialog.Builder(this)
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout)
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false)
        val dialog = alert.create()
        cancle_btn.setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
        *//*val dialog = Dialog(this)
        dialog.setContentView(R.layout.next_button_dailog)
        dialog.setCancelable(false)


        dialog.show()*//*
    }*/
}