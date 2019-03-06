package stock.com.ui.joinedContest.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_joined_completed_contestlist.*
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.joinedContest.adapter.JoinedCompletedContestAdapter


class CompletedJoinedContestActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_ViewPlayerStats -> {
                startActivity(Intent(this, PlayerStatsActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_completed_contestlist)
        initViews()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.joined_Contest)
        setMenu(false, false, false, false, false, false, false)
        setAdapter()
        txt_ViewPlayerStats.setOnClickListener(this)
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Contest!!.layoutManager = llm
        rv_Contest!!.adapter = JoinedCompletedContestAdapter(this)
    }


}