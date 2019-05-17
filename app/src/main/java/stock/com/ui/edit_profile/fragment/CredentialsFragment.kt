package stock.com.ui.edit_profile.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.content_signup.*
import kotlinx.android.synthetic.main.fragment_contact_info.*
import kotlinx.android.synthetic.main.fragment_crediantials.*
import kotlinx.android.synthetic.main.fragment_crediantials.view.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.FilterPojo
import stock.com.ui.pojo.UserPojo
import stock.com.ui.signup.activity.ConfirmationActivity
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils

class CredentialsFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_crediantials, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.tv_achange_pass.setOnClickListener {
            if(et_old_pass.text.toString().equals("")){
                displayToast(resources.getString(R.string.error_old_pass),"warning");
            }else if(et_new_pass.text.toString().equals("")){
                displayToast(resources.getString(R.string.error_new_pass),"warning");
            }else if(et_conf_pass.text.toString().equals("")){
                displayToast(resources.getString(R.string.error_con_pass), "warning");
            }else{
                if(!et_conf_pass.text.toString().equals(et_new_pass.text.toString())){
                    displayToast(resources.getString(R.string.error_pass_match), "warning")
                }else{
                    if(NetworkUtils.isConnected()){
                        changePassword();
                    }else{
                        displayToast(resources.getString(R.string.error_network_connection),"error")
                    }
                }
            }

          /*  startActivity(Intent(context, ConfirmationActivity::class.java)
                    .putExtra(StockConstant.USEREMAIL, et_email.text.toString())
                    .putExtra(StockConstant.USERNAME, et_user_name.text.toString())
                    .putExtra(StockConstant.USERPHONE, et_mob.text.toString())
                    .putExtra(StockConstant.USERID, getFromPrefsString(StockConstant.USERID).toString())
                    .putExtra(StockConstant.FLAG,  "true")
            )*/


        }

        view.tv_cancel.setOnClickListener{
              activity!!.finish();
        }




    }

    fun changePassword() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.changePassword(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),getFromPrefsString(StockConstant.USERID).toString(),et_old_pass.text.toString().trim(),et_new_pass.text.toString().trim())
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        //startActivity(Intent(this@ActivityResetPassword, DashBoardActivity::class.java))
                        displayToast(response.body()!!.message, "warning")
                        appLogout();
                    }else if(response.body()!!.status == "2"){
                        appLogout();
                    }else{
                        displayToast(response.body()!!.message, "warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }



}