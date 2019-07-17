package stock.com.ui.dashboard.my_contest

import android.annotation.SuppressLint
import android.os.Bundle
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

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_Alluser -> {
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.white));
                setInviteAdapter(listUser!!)

            }
            R.id.tv_friends -> {
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
                setInviteFriendsAdapter(listFriends!!)
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
                contestId, "0", "50"
            )
        call.enqueue(object : Callback<InviteData> {

            override fun onResponse(call: Call<InviteData>, response: Response<InviteData>) {
                d.dismiss()
                /* if (refreshD != null)
                     refreshD.finishRefreshing()*/
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        listUser = response.body()!!.userData
                        listFriends = response.body()!!.friendData
                        setInviteAdapter(response.body()!!.userData)
//                        setInviteFriendsAdapter(response.body()!!.friendData)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<InviteData>, t: Throwable) {
                /* if (refreshD != null)
                     refreshD.finishRefreshing()*/
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