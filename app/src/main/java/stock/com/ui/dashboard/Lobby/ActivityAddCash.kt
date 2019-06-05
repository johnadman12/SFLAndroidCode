package stock.com.ui.dashboard.Lobby

import android.os.Bundle
import kotlinx.android.synthetic.main.add_funds_activity.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivityAddCash : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_funds_activity)
        StockConstant.ACTIVITIES.add(this)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }


        tv1.setOnClickListener {
            et_amount.setText("")
            et_amount.setText(tv1.text)
        }

        tv2.setOnClickListener {
            et_amount.setText("")
            et_amount.setText(tv2.text.substring(0,1))
        }

        tv2.setOnClickListener {
            et_amount.setText("")
            et_amount.setText(tv3.text)
        }
    }

}