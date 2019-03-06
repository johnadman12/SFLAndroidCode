package stock.com.ui.joinedContest.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_completed_player_stats.*
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.joinedContest.adapter.PlayerStatsAdapter


class PlayerStatsActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_completed_player_stats)
        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.player_points)
        setMenu(false, false, false, false, false, false, false)
        setAdapter()
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players!!.adapter = PlayerStatsAdapter(this)
    }


}