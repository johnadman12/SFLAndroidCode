package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_price_break.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.R

class ActivityPriceBreak : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_break)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        setAdapter()
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_listWinner!!.layoutManager = llm
        rv_listWinner!!.adapter = PriceBreakAdapter(this@ActivityPriceBreak)
    }
}
