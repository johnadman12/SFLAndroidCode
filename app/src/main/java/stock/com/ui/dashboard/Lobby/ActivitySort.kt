package stock.com.ui.dashboard.Lobby

import android.os.Bundle
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivitySort : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort)
        initViews()
    }

    private fun initViews() {
        img_btn_close.visibility = VISIBLE
        img_btn_close.setOnClickListener {
            finish()
        }
        img_btn_back.setOnClickListener {
            finish()
        }

    }
}