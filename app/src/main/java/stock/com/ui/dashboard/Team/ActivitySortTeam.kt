package stock.com.ui.dashboard.Team

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_sort_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivitySortTeam : BaseActivity() {
    var getFlag: String = ""
    var checkFalg: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_create_team)
        StockConstant.ACTIVITIES.add(this)
        reset.visibility = View.VISIBLE

        if (intent != null)
            getFlag = intent.getStringExtra("flagStatus")

        if (getFlag.equals("price", true)) {
            makecheckbox(false, false, true, false, false, false)
            checkFalg=true;

        } else if (getFlag.equals("Alpha", true)) {
            makecheckbox(false, true, false, false, false, false)
            checkFalg=true;

        } else if (getFlag.equals("priceHTL", true)) {
            makecheckbox(false, false, false, false, false, true)
            checkFalg=true;

        } else if (getFlag.equals("dayLTH", true)) {
            makecheckbox(false, false, false, true, false, false)
            checkFalg=true;

        } else if (getFlag.equals("dayHTL", true)) {
            makecheckbox(false, false, false, false, true, false)
            checkFalg=true;

        } else if (getFlag.equals("Volume", true)) {
            makecheckbox(true, false, false, false, false, false)
            checkFalg=true;
        } else {
            makecheckbox(false, false, false, false, false, false)
            checkFalg=true;
        }

        initViews()

        checkBoxPrize?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxPrizeHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false;
                checkBoxDayLTH.isChecked = false;
                checkFalg=true;
            }
        }
        checkBoxVolume?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxPrizeHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false;
                checkBoxDayLTH.isChecked = false;
                checkFalg=true;
            }
        }
        checkBoxAlpha?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxPrizeHTL.isChecked = false;
                checkBoxDayHTL.isChecked = false;
                checkBoxDayLTH.isChecked = false;
                checkFalg=true;
            }
        }
        checkBoxPrizeHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxDayHTL.isChecked = false;
                checkBoxDayLTH.isChecked = false;
                checkFalg=true;
            }
        }
        checkBoxDayHTL?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxDayLTH.isChecked = false;
                checkBoxPrizeHTL.isChecked = false;
                checkFalg=true;
            }
        }
        checkBoxDayLTH?.setOnCheckedChangeListener { buttonView, isChecked ->
            //val msg = "You have " + (if (isChecked) "checked" else "unchecked") + " this Check it Checkbox."
            if (isChecked) {
                checkBoxVolume.isChecked = false;
                checkBoxPrize.isChecked = false;
                checkBoxAlpha.isChecked = false;
                checkBoxDayHTL.isChecked = false;
                checkBoxPrizeHTL.isChecked = false;
                checkFalg=true;
            }
        }


        reset.setOnClickListener {
            checkFalg=false;
            var intent = Intent();
            intent.putExtra("flag", "nodata")
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }


    fun makecheckbox(fV: Boolean, fA: Boolean, fP: Boolean, fDLTH: Boolean, fDHTL: Boolean, fPHTL: Boolean) {
        checkBoxVolume.isChecked = fV;
        checkBoxAlpha.isChecked = fA;
        checkBoxPrize.isChecked = fP;
        checkBoxDayLTH.isChecked = fDLTH;
        checkBoxDayHTL.isChecked = fDHTL;
        checkBoxPrizeHTL.isChecked = fPHTL;

    }


    private fun initViews() {

        img_btn_back.setOnClickListener {
            finish();
        }

        btn_sortApply.setOnClickListener {
            var temp: String = "";
            if (checkFalg) {
                displayToast("Please Select Sort Option.", "warning")
            } else {
                if (checkBoxPrize.isChecked) {
                    temp = "price"
                }
                if (checkBoxVolume.isChecked) {
                    temp = "Volume"
                }
                if (checkBoxAlpha.isChecked) {
                    temp = "Alpha"
                }
                if (checkBoxPrizeHTL.isChecked) {
                    temp = "priceHTL"
                }
                if (checkBoxDayLTH.isChecked) {
                    temp = "dayLTH"
                }
                if (checkBoxDayHTL.isChecked) {
                    temp = "dayHTL"
                }


                var intent = Intent();
                intent.putExtra("flag", temp)
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }
}