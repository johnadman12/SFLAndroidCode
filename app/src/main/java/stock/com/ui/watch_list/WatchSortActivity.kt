package stock.com.ui.watch_list

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_sort_watch.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class WatchSortActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    var getFlag: String = ""
    var checkFlag:Boolean= false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_watch)
        reset.visibility = View.VISIBLE



        img_btn_back.setOnClickListener {
            onBackPressed()

        }
        reset.setOnClickListener {
            checkFlag=false;
            var intent = Intent();
            intent.putExtra("flag", "nodata")
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        if (intent != null)
            getFlag = intent.getStringExtra("flagStatus")
        if (getFlag.equals("high", true)) {
            makecheckbox(false, false, false, true, false)
            checkFlag = true;

        } else if (getFlag.equals("low", true)) {
            makecheckbox(false, false, true, false, false)
            checkFlag = true;

        } else if (getFlag.equals("daily", true)) {
            makecheckbox(false, false, false, false, true)
            checkFlag = true;

        } else if (getFlag.equals("dailyLTH", true)) {
            makecheckbox(false, true, false, false, false)
            checkFlag = true;

        } else {
            makecheckbox(true, false, false, false, false)
            checkFlag = false;
        }

        checkBoxDailyChanges.setOnCheckedChangeListener(this);
        cb_weekly.setOnCheckedChangeListener(this);
        cb_monthly.setOnCheckedChangeListener(this);
        cb_price_low.setOnCheckedChangeListener(this);
        cb_price_high.setOnCheckedChangeListener(this);


        var temp = "";

        btn_Apply.setOnClickListener {
            if ( checkFlag){
                if (cb_price_high.isChecked) {
                    temp = "high";
                }
                if (cb_price_low.isChecked) {
                    temp = "low";
                }

                if (checkBoxDailyChanges.isChecked) {
                    temp = "daily";
                }

                if (cb_monthly.isChecked) {
                    temp = "dailyLTH";
                }

                var intent = Intent();
                intent.putExtra("flag", temp)
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
            else {
                displayToast("Please Select Sort Option.", "warning");
            }
        }

    }

    fun makecheckbox(fW: Boolean, fM: Boolean, fPL: Boolean, fPH: Boolean, fD: Boolean) {
        cb_weekly.isChecked = fW;
        cb_monthly.isChecked = fM;
        cb_price_low.isChecked = fPL;
        cb_price_high.isChecked = fPH;
        checkBoxDailyChanges.isChecked = fD;
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.checkBoxDailyChanges -> {
                if (isChecked) {
                    cb_weekly.isChecked = false;
                    cb_monthly.isChecked = false;
                    cb_price_low.isChecked = false;
                    cb_price_high.isChecked = false;
                    checkFlag = true;
                }
                else{
                    checkFlag=false;
                }
            }
            R.id.cb_weekly -> {
                if (isChecked) {
                    checkBoxDailyChanges.isChecked = false;
                    cb_monthly.isChecked = false;
                    cb_price_low.isChecked = false;
                    cb_price_high.isChecked = false;
                    checkFlag = true;
                }else{
                    checkFlag=false;
                }
            }
            R.id.cb_monthly -> {
                if (isChecked) {
                    cb_weekly.isChecked = false;
                    checkBoxDailyChanges.isChecked = false;
                    cb_price_low.isChecked = false;
                    cb_price_high.isChecked = false;
                    checkFlag = true;
                }else{
                    checkFlag=false;
                }
            }
            R.id.cb_price_low -> {
                if (isChecked) {
                    cb_weekly.isChecked = false;
                    checkBoxDailyChanges.isChecked = false;
                    cb_monthly.isChecked = false;
                    cb_price_high.isChecked = false;
                    checkFlag = true;
                }else{
                    checkFlag=false;
                }
            }
            R.id.cb_price_high -> {
                if (isChecked) {
                    cb_weekly.isChecked = false;
                    checkBoxDailyChanges.isChecked = false;
                    cb_monthly.isChecked = false;
                    cb_price_low.isChecked = false;
                    checkFlag = true;
                }else{
                    checkFlag=false;
                }
            }


        }
    }

}
