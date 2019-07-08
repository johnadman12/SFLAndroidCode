package stock.com.ui.dashboard.Market

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
        reset.visibility = View.VISIBLE
        var getFlag: String = ""

        if (intent != null)
            getFlag = intent.getStringExtra("flagStatus")

        if (getFlag.equals("price", true)) {
            checkBoxVolume.isChecked = false;
            checkBoxAlpha.isChecked = false;
            checkBoxHTL.isChecked = false;
            checkBoxDayHTL.isChecked = false
            checkBoxPrize.isChecked = true;

        } else if (getFlag.equals("dayChange", true)) {
            checkBoxVolume.isChecked = true;
            checkBoxAlpha.isChecked = false;
            checkBoxHTL.isChecked = false;
            checkBoxDayHTL.isChecked = false
            checkBoxPrize.isChecked = false;
        } else if (getFlag.equals("Alpha", true)) {
            checkBoxVolume.isChecked = false;
            checkBoxAlpha.isChecked = true;
            checkBoxHTL.isChecked = false;
            checkBoxDayHTL.isChecked = false
            checkBoxPrize.isChecked = false;

        } else if (getFlag.equals("HighToLow", true)) {
            checkBoxVolume.isChecked = false;
            checkBoxAlpha.isChecked = false;
            checkBoxHTL.isChecked = true;
            checkBoxDayHTL.isChecked = false
            checkBoxPrize.isChecked = false;
        } else if (getFlag.equals("DayHighToLow", true)) {
            checkBoxVolume.isChecked = false;
            checkBoxAlpha.isChecked = false;
            checkBoxHTL.isChecked = false;
            checkBoxDayHTL.isChecked = true
            checkBoxPrize.isChecked = false;
        } else {
            checkBoxVolume.isChecked = false;
            checkBoxAlpha.isChecked = false;
            checkBoxHTL.isChecked = false;
            checkBoxDayHTL.isChecked = false
            checkBoxPrize.isChecked = false;
        }

        initViews()


        checkBoxPrize?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false
            }
        }
        checkBoxVolume?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false
            }
        }
        checkBoxAlpha?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false
            }
        }
        checkBoxHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxDayHTL.isChecked = false
            }
        }
        checkBoxDayHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxHTL.isChecked = false
            }
        }

        reset.setOnClickListener {
            var intent = Intent();
            intent.putExtra("flag", "nodata")
            setResult(Activity.RESULT_OK, intent);
            finish();
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