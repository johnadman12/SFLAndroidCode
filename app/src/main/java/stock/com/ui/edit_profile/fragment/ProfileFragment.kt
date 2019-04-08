package stock.com.ui.edit_profile.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.UserPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ProfileFragment: BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViews()
        getProfile();
    }
    override fun onClick(v: View?) {
    }
    fun getProfile() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserPojo> = apiService.getProfile(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<UserPojo> {
            override fun onResponse(call: Call<UserPojo>, response: Response<UserPojo>) {
                d.dismiss()
                if (response.body() != null) {
                        if(response.body()!!.status.equals("1")){
                            setData(response.body()!!.user!!)
                        }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
    private fun setData(user: UserPojo.User){
        et_user_name.setText(user.username);
        et_user_name.isEnabled=false;
        et_biography.setText(user.biography);
    }
}