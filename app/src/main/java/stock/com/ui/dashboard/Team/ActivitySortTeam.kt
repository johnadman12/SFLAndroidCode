package stock.com.ui.dashboard.Team

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sort_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivitySortTeam : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_create_team)
        initViews()

        checkBoxPrize?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxAlpha.isChecked = false;
            }
        }
        checkBoxVolume?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
            }
        }
        checkBoxAlpha?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
            }
        }
    }

    private fun initViews() {
        img_btn_close.visibility = View.VISIBLE

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
                temp = "Volume"
            }
            if (checkBoxAlpha.isChecked) {
                temp = "Alpha"
            }


            var intent = Intent();
            intent.putExtra("flag", temp)
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}