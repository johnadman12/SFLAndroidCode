package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class ActivityViewTeam : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var stockTeamAdapter: StockTeamAdapter? = null;
    private var stockSelectedItems: ArrayList<Int>? = null
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.relFieldView -> {
                startActivity(Intent(this@ActivityViewTeam, TeamPreviewActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_team)
        list = ArrayList()
        if (intent != null)
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    private fun initView() {
        stockSelectedItems = ArrayList();
        img_btn_back.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        stockTeamAdapter = StockTeamAdapter(
            this, list as ArrayList, "yes",
            object : StockTeamAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: Int) {
                    stockSelectedItems?.remove(item);
                }

                override fun onItemCheck(item: Int) {
                    stockSelectedItems?.add(item);
                }
            });
        setStockAdapter()
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_team!!.layoutManager = llm
        rv_team.visibility = View.VISIBLE
        rv_team!!.adapter = stockTeamAdapter;
    }
}