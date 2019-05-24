package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_price_break.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivityPriceBreak : BaseActivity() {


    private var sport: String? = null;

    private var count: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_break)

        if (intent != null) {
            sport = intent.getStringExtra("sport");
        }

        tv_winner.setText(sport + " " + "Winners")

        var list = ArrayList<String>();

        if (sport!!.toInt() >= 2 && sport!!.toInt() < 7) {
            count = 5;
            list.add("5")
            list.add("4")
            list.add("3")
            list.add("2")
            list.add("1")
        } else if (sport!!.toInt() >= 7 && sport!!.toInt() < 14) {
            count = 3;

            list.add("7")
            list.add("6")
            list.add("5")
        } else if (sport!!.toInt() >= 15 && sport!!.toInt() <= 24) {
            count = 3;
            list.add("15")
            list.add("10")
            list.add("7")
        } else if (sport!!.toInt() >= 25 && sport!!.toInt() <= 49) {
            count = 3;
            list.add("25")
            list.add("15")
            list.add("10")
        } else if (sport!!.toInt() >= 50 && sport!!.toInt() <= 100) {
            count = 3;
            list.add("50")
            list.add("25")
            list.add("15")
        }





        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        ll_winners.setOnClickListener {
            val bottomSheetFragment = BottonSheetPriceBreakup(count,list)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())
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
