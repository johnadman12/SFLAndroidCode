package stock.com.ui.dashboard.Market

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_market_sort.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivityMarketSort : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_sort)
        StockConstant.ACTIVITIES.add(this)
        initViews()
        checkBoxPrize?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked=false
            }
        }
        checkBoxVolume?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked=false
            }
        }
        checkBoxAlpha?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked=false
            }
        }
        checkBoxHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxDayHTL.isChecked=false
            }
        }
        checkBoxDayHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked=false
            }
        }
    }

    private fun initViews() {
        img_btn_close.setOnClickListener {
            finish();
        }

        img_btn_back.setOnClickListener {
            finish();
        }

        btn_sortApply.setOnClickListener {
            var temp: String = "";
            if (checkBoxPrize.isChecked) {
                temp = "price"
            }
            if (checkBoxVolume.isChecked) {
                temp = "dayChange"
            }
            if (checkBoxAlpha.isChecked) {
                temp = "Alpha"
            }
            if (checkBoxHTL.isChecked) {
                temp = "HighToLow"
            }
            if (checkBoxDayHTL.isChecked) {
                temp = "DayHighToLow"
            }
            var intent = Intent();
            intent.putExtra("flag", temp)
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

}