package stock.com.ui.createTeam.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_playerdetail.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.createTeam.adapter.PlayerDetailFantasyStatusAdapter


class PlayerDetailActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.player_detail)
        setMenu(false, false, false, false, false, false, false)
        setAdapter()
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_FantasyStatus!!.layoutManager = llm
        rv_FantasyStatus!!.adapter = PlayerDetailFantasyStatusAdapter(this)
    }


}