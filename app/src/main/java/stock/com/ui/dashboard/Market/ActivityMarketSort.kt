package stock.com.ui.dashboard.Market

import android.os.Bundle
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivityMarketSort : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_sort)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
    }

}