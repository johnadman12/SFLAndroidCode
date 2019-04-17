package stock.com.ui.feedback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.wallet.adapter.MessageHistoryAdapter

class FeedBackActivity : BaseActivity() {


    private var watchListAdapter: MessageHistoryAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        setAdapter();

        img_btn_back.setOnClickListener {
            onBackPressed();
        }


    }


    private fun setAdapter() {
        watchListAdapter = MessageHistoryAdapter(applicationContext!!)
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_message_history!!.layoutManager = llm
        recyclerView_message_history!!.adapter = watchListAdapter;

    }


}
