package stock.com.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_searchbar.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivityNewsSearch : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchbar)
        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        setHomeAdapter()
    }

    @SuppressLint("WrongConstant")
    private fun setHomeAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_home!!.layoutManager = llm
        recycle_home.visibility = View.VISIBLE
        recycle_home!!.adapter = HomeNewsAdapter(this@ActivityNewsSearch);
    }


}