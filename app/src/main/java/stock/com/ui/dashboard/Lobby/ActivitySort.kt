package stock.com.ui.dashboard.Lobby

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_sort.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivitySort : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        reset.visibility = VISIBLE
        var getFlag: String = ""
        initViews();

        if (intent != null)
            getFlag = intent.getStringExtra("flagStatus")


        if (getFlag != null)
            if (getFlag.equals("price", true)) {
                checkBoxEntry.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = false;
                checkBoxPrize.isChecked = true;
            } else if (getFlag.equals("Entry", true)) {
                checkBoxPrize.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = false;
                checkBoxEntry.isChecked = true;
            } else if (getFlag.equals("time", true)) {
                checkBoxPrize.isChecked = false;
                checkBoxTime.isChecked = true;
                checkBoxPosition.isChecked = false;
                checkBoxEntry.isChecked = false;

            } else if (getFlag.equals("position", true)) {
                checkBoxPrize.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = true;
                checkBoxEntry.isChecked = false;
            } else {
                checkBoxPrize.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = false;
                checkBoxEntry.isChecked = false;
            }

        reset.setOnClickListener {
            var intent = Intent();
            intent.putExtra("flag", "nodata")
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

        checkBoxPrize?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxEntry.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = false;
            }
        }
        checkBoxEntry?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxPrize.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPosition.isChecked = false;
            }
        }
        checkBoxTime?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxEntry.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxPosition.isChecked = false;
            }
        }

        checkBoxPosition?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxEntry.isChecked = false;
                checkBoxTime.isChecked = false;
                checkBoxPrize.isChecked = false;
            }
        }
    }

    private fun initViews() {

        img_btn_back.setOnClickListener {
            finish();
        }

        btn_sortApply.setOnClickListener {
            var temp: String = "";
            if (checkBoxPrize.isChecked) {
                temp = "price"
            }
            if (checkBoxEntry.isChecked) {
                temp = "Entry"
            }
            if (checkBoxTime.isChecked) {
                temp = "time"
            }
            if (checkBoxPosition.isChecked) {
                temp = "position"
            }

            var intent = Intent();
            intent.putExtra("flag", temp)
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    override fun onBackPressed() {
        super.onBackPressed();

    }
}