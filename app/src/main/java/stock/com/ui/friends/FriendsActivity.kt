package stock.com.ui.friends

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.profile.PendingRequestActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.FriendsList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class FriendsActivity : BaseActivity() {
    var list: ArrayList<FriendsList.UserDatum>? = null
    var friendsAdapter: FriendAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList()
        tv_title.visibility = View.VISIBLE;

        tv_title.setText(getFromPrefsString(StockConstant.USERNAME));

        refreshD.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
                getFriendsList()
            }
        })

        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        getFriendsList()
        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                friendsAdapter!!.getFilter().filter(s);
            }
        })

        rl_pending.setOnClickListener {
            startActivity(Intent(this@FriendsActivity, PendingRequestActivity::class.java))
        }
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_friend!!.layoutManager = llm
        recyclerView_friend.visibility = View.VISIBLE
        friendsAdapter = FriendAdapter(applicationContext, list!!, this@FriendsActivity)
        recyclerView_friend!!.adapter = friendsAdapter
    }


    fun getFriendsList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<FriendsList> =
            apiService.getFriendsList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<FriendsList> {

            override fun onResponse(call: Call<FriendsList>, response: Response<FriendsList>) {
                d.dismiss()
                if (refreshD != null)
                    refreshD.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        tv_request_count.setText(response.body()!!.pending_requests)
                        list = response.body()!!.userData
                        setAdapter()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<FriendsList>, t: Throwable) {
                if (refreshD != null)
                    refreshD.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

    fun addTofriendList(friendId: String, status: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addToFriends(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(), friendId, status
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        AppDelegate.showAlert(this@FriendsActivity, response.body()!!.message)
                        getFriendsList()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }
}
