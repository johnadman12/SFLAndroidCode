package stock.com.ui.Reset

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.SignupPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils

class ActivityResetPassword : BaseActivity() {
   var flag:String="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        StockConstant.ACTIVITIES.add(this)
        if (intent != null) {
            flag = intent.getStringExtra(StockConstant.FLAG);
        }

        if(flag.equals("true")){
            til_conf_pass.setHint(resources.getString(R.string.old_pass));
            til_new_pass.setHint(resources.getString(R.string.new_pass));
        }
        initViews()
    }
    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {
            if(flag.equals("true")){
                checkValidationChangePasssword();
            }else {
                checkValidation();

            }}
        bt_cancel.setOnClickListener {
            finish()
        }

    }


    private fun checkValidationChangePasssword() {
        if (et_new_pass.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_password))
        else if (et_conf_pass.text.toString().isEmpty()) {
                AppDelegate.showToast(this, getString(R.string.old_pass))
         } else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                    changePassword();
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkValidation() {
        if (et_new_pass.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_password))
        else if (et_conf_pass.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_confirm_password))
        else if (!et_conf_pass.text.toString().equals(et_new_pass.text.toString()))
            AppDelegate.showToast(this, "Password doesn't match")
        else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                resetPassword()
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun resetPassword() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.resetPassword(getFromPrefsString(StockConstant.USERID).toString(), et_conf_pass.text.toString().trim())
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(Intent(this@ActivityResetPassword, DashBoardActivity::class.java))
                    }
                    displayToast(response.body()!!.message)
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<SignupPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
    fun changePassword() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.changePassword(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),getFromPrefsString(StockConstant.USERID).toString(),et_new_pass.text.toString().trim(),et_conf_pass.text.toString().trim())
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        //startActivity(Intent(this@ActivityResetPassword, DashBoardActivity::class.java))
                        displayToast(response.body()!!.message)
                        appLogout();
                    }else if(response.body()!!.status == "2"){
                        appLogout();
                    }else{
                        displayToast(response.body()!!.message)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
}