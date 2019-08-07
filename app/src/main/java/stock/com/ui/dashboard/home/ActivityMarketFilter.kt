package stock.com.ui.dashboard.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_market_filter.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class ActivityMarketFilter : BaseActivity(), View.OnClickListener {
    private var activeCurrencyFilter: String? = "";

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.reset -> {
                setActiveCurrencyType("1")
                val resultIntent = Intent()
                resultIntent.putExtra("resetfiltermarket", "1")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
            R.id.btn_Apply -> {
                if (checkBoxActive.isChecked) {
                    setActiveCurrencyType("0")
                    val resultIntent = Intent()
                    resultIntent.putExtra("resetfiltermarket", "0")
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else
                    displayToast("please select to apply filter", "warning")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_filter)
        StockConstant.ACTIVITIES.add(this)
        activeCurrencyFilter = getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE);
        if (activeCurrencyFilter!!.equals("0"))
            checkBoxActive.isChecked = true
        initViews()
    }

    private fun initViews() {
        img_btn_back.setOnClickListener(this)
        reset.setOnClickListener(this)
        reset.visibility = VISIBLE
        btn_Apply.setOnClickListener(this)
    }
}