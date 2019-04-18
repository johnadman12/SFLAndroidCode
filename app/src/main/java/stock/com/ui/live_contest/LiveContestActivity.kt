package stock.com.ui.live_contest

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_live_contest.*
import kotlinx.android.synthetic.main.dialog_select.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.live_contest.adapter.LiveContestAdapter

class LiveContestActivity : BaseActivity() {

    private var liveAdapter: LiveContestAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_contest)




        img_btn_back.setOnClickListener {
            onBackPressed();
        }


        img_btn_close.setOnClickListener {
            finish();
        }




        liveAdapter = LiveContestAdapter(applicationContext);
        val llm = LinearLayoutManager(applicationContext);
        llm.orientation = LinearLayoutManager.VERTICAL;
        recyclerView_Live!!.layoutManager = llm;
        recyclerView_Live!!.adapter = liveAdapter;


    }
}
