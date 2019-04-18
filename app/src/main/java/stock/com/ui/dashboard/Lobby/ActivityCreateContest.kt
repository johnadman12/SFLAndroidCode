package stock.com.ui.dashboard.Lobby

import android.os.Bundle
import kotlinx.android.synthetic.main.create_contest_activity.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivityCreateContest : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_contest_activity)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    private fun initView() {
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        endDate.setOnClickListener {

        }
    }
}