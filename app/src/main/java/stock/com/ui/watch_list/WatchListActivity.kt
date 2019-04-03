package stock.com.ui.watch_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_watch_list.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.R

class WatchListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        setWatchListAdapter();

        img_btn_back.setOnClickListener {
            onBackPressed();
        }

    }

    private fun setWatchListAdapter() {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_watch_list!!.layoutManager = llm
        recyclerView_watch_list.visibility = View.VISIBLE
        recyclerView_watch_list!!.adapter = WatchListAdapter(applicationContext!!/*, listItem*/);
    }
}
