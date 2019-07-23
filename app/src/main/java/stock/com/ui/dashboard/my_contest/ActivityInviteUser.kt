package stock.com.ui.dashboard.my_contest

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_invite_user.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.InviteData
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class ActivityInviteUser : BaseActivity(), View.OnClickListener {
    var contestId: String = ""
    var listUser: ArrayList<InviteData.UserDatum>? = null
    var listFriends: ArrayList<InviteData.FriendDatum>? = null
    var page: Int = 0
    var limit: Int = 50
    var flagRefresh: Boolean = false
    var pageScreen: Int = 0

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_Alluser -> {
                pageScreen = 0
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.white));
                et_search_news.setText("")
                getFriendsList()
            }
            R.id.tv_friends -> {
                pageScreen = 1
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
                et_search_news.setText("")
                getFriendsList()
            }
            R.id.img_btn_back -> {
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_user)
        tv_friends.setOnClickListener(this)
        tv_Alluser.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        if (intent != null)
            contestId = intent.getStringExtra(StockConstant.CONTESTID)
        listFriends = ArrayList()
        listUser = ArrayList()
        changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
        changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.textColorLightBlack));
        changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.colorbutton));
        changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.white));
        getFriendsList()
        tv_Alluser.performClick()
        imgcross.setOnClickListener {
            et_search_news.setText("")
            AppDelegate.hideKeyBoard(this@ActivityInviteUser)
        }
        pageScreen = 0
        srl_layout.setOnRefreshListener {
            flagRefresh = true
            getFriendsList()
        }

        et_search_news.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 2) {
                    getUserSearchList(s.toString())
                    AppDelegate.hideKeyBoard(this@ActivityInviteUser)
                }
            }
        })

    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }

    @SuppressLint("WrongConstant")
    fun setInviteAdapter(userData: ArrayList<InviteData.UserDatum>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyle_invite_list!!.layoutManager = llm
        recyle_invite_list!!.adapter = InviteUserAdapter(this@ActivityInviteUser, userData, this)
    }

    @SuppressLint("WrongConstant")
    fun setInviteFriendsAdapter(friends: ArrayList<InviteData.FriendDatum>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyle_invite_list!!.layoutManager = llm
        recyle_invite_list!!.adapter = InviteMyFriendsAdapter(this@ActivityInviteUser, friends, this)
    }


    fun getFriendsList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<InviteData> =
            apiService.getAllUserToInvite(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                contestId, page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<InviteData> {

            override fun onResponse(call: Call<InviteData>, response: Response<InviteData>) {
                d.dismiss()
                srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        listUser!!.clear()
                        listFriends!!.clear()
                        listUser = response.body()!!.userData
                        listFriends = response.body()!!.friendData
                        if (pageScreen == 0)
                            setInviteAdapter(response.body()!!.userData)
                        else
                            setInviteFriendsAdapter(response.body()!!.friendData)
                        if (flagRefresh) {
                            limit = limit + 50
                            if (pageScreen == 0)
                                setInviteAdapter(response.body()!!.userData)
                            else
                                setInviteFriendsAdapter(response.body()!!.friendData)
                        }
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<InviteData>, t: Throwable) {
                srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }


    fun getUserSearchList(search: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<InviteData> =
            apiService.getAllUserSearch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                contestId, page.toString(), limit.toString(), search
            )
        call.enqueue(object : Callback<InviteData> {
            override fun onResponse(call: Call<InviteData>, response: Response<InviteData>) {
                d.dismiss()
                srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        listUser!!.clear()
                        listFriends!!.clear()
                        listUser = response.body()!!.userData
                        listFriends = response.body()!!.friendData
                        if (pageScreen == 0)
                            setInviteAdapter(response.body()!!.userData)
                        else
                            setInviteFriendsAdapter(response.body()!!.friendData)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<InviteData>, t: Throwable) {
                srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }


    fun sendInvitation(friend_id: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.sendInvitation(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                friend_id,
                contestId, getFromPrefsString(StockConstant.USERNAME).toString()
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                /* if (refreshD != null)
                     refreshD.finishRefreshing()*/
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        AppDelegate.showAlert(this@ActivityInviteUser, response.body()!!.message)
                        getFriendsList()
                        setInviteAdapter(listUser!!)
                        setInviteFriendsAdapter(listFriends!!)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                /* if (refreshD != null)
                     refreshD.finishRefreshing()*/
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }


}