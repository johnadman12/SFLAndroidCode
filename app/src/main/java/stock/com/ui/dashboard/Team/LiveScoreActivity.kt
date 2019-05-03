package stock.com.ui.dashboard.Team

import android.os.Bundle
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class LiveScoreActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_score_activity)
        StockConstant.ACTIVITIES.add(this)

        img_btn_back.setOnClickListener {
            onBackPressed();
        }


    }
}