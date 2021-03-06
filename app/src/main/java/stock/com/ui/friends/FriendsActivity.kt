package stock.com.ui.friends

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
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
    var page: Int = 0
    var limit: Int = 50


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList()
        tv_title.visibility = View.VISIBLE;

        tv_title.setText(getFromPrefsString(StockConstant.USERNAME));

        srl_layout.setOnRefreshListener {
            page++
            getFriendsList(1)
        }

        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        setAdapter()
        getFriendsList(0)
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


    fun getFriendsList(firstTime: Int) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<FriendsList> =
            apiService.getFriendsList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(), page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<FriendsList> {

            override fun onResponse(call: Call<FriendsList>, response: Response<FriendsList>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        tv_request_count.setText(response.body()!!.pending_request.toString())
                        if (firstTime == 0) {
                            list!!.clear()
                            list!!.addAll(response.body()!!.userData!!)
                        } else {
                            list!!.addAll(response.body()!!.userData!!)

                        }
                      if (friendsAdapter!=null)
                          friendsAdapter!!.notifyDataSetChanged()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<FriendsList>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
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
                        getFriendsList(0)
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
