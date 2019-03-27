package stock.com.ui.signup.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.Reset.ActivityResetPassword
import stock.com.ui.pojo.BasePojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ValidationUtil
import stock.com.utils.networkUtils.NetworkUtils

class ForgotPasswordActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener {
           onBackPressed()
        }
        btn_submit_.setOnClickListener {
            checkValidation()
        }
    }

    private fun checkValidation() {
        if (et_Email_.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_email))
        else if (!ValidationUtil.isEmailValid(et_Email_.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_email))
        else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                forgotPass()
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun forgotPass() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.forgot_pass(et_Email_.text.toString().trim())
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        startActivity(
                            Intent(this@ForgotPasswordActivity, ActivityResetPassword::class.java)
                        )
                        finish()
                    }
                    displayToast(response.body()!!.message)
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }


}