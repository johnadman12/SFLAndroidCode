package stock.com.ui.dashboard.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_friends.*
import kotlinx.android.synthetic.main.activity_other_user_profile.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.FriendsList
import stock.com.ui.pojo.OtherProfile
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityOtherUserProfile : BaseActivity() {
    var friendId: String = ""
    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_user_profile)
        StockConstant.ACTIVITIES.add(this)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        if (intent != null)
            friendId = intent.getStringExtra(StockConstant.FRIENDID)

        if (friendId != null)
            getFriendsList()

        llADD.setOnClickListener {
            if (flag)
                addTofriendList(friendId, "1")
            else
                addTofriendList(friendId, "0")

        }
    }


    fun getFriendsList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<OtherProfile> =
            apiService.getOthersProfile(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(), friendId
            )
        call.enqueue(object : Callback<OtherProfile> {

            override fun onResponse(call: Call<OtherProfile>, response: Response<OtherProfile>) {
                d.dismiss()
                if (refreshD != null)
                    refreshD.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setData(response.body()!!.user)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<OtherProfile>, t: Throwable) {
                if (refreshD != null)
                    refreshD.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

    private fun setData(user: ArrayList<OtherProfile.User>?) {
        tv_pro.setText(user!!.get(0).levelType)
        text_user.setText(user.get(0).username)
        tv_bio.setText("Biography : " + user.get(0).biography)
        Glide.with(this).load(user.get(0).profileImage).into(profile_image)

        if (user.get(0).contestInvite.equals("Add")) {
            flag = true

        } else if (user.get(0).contestInvite.equals("remove")) {
            flag = false
            llADD.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.red_fill_button))
            add.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_close))
        } else {
            flag = false
            llADD.isEnabled = false
            llADD.visibility = View.GONE
            llPending.visibility = View.VISIBLE
        }

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
                        AppDelegate.showAlert(this@ActivityOtherUserProfile, response.body()!!.message)
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