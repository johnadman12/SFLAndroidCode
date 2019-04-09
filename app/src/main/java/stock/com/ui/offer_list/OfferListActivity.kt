package stock.com.ui.offer_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_offer_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.home.adapter.MatchLiveAdapter

class OfferListActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_list)

        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_offer!!.layoutManager = llm
        rv_offer!!.adapter = OfferListAdapter(applicationContext!!)
    }
}
