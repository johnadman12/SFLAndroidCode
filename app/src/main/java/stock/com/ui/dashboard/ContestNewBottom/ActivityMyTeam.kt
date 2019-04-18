package stock.com.ui.dashboard.ContestNewBottom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.content_myteam.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivityMyTeam : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        StockConstant.ACTIVITIES.add(this)
        initView()
        setMyAdapter()
    }

    private fun initView() {
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

    }


    @SuppressLint("WrongConstant")
    fun setMyAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvMYTeam!!.layoutManager = llm
        rvMYTeam?.itemAnimator = DefaultItemAnimator()
        rvMYTeam!!.adapter = MyTeamAdapter(this)
    }
}