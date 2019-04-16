package stock.com.ui.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class FriendsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        setAdapter();

        tv_title.visibility=View.VISIBLE;

        tv_title.setText(getFromPrefsString(StockConstant.USERNAME));

        img_btn_back.setOnClickListener {
            onBackPressed();
        }




    }
    private fun setAdapter(){
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_friend!!.layoutManager = llm
        recyclerView_friend.visibility = View.VISIBLE
        recyclerView_friend!!.adapter = FriendAdapter(applicationContext)
    }
}
