package stock.com.ui.signup.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password.*
import kotlinx.android.synthetic.main.app_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.SignupPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils

class PasswordActivity : BaseActivity() {

    var login_email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        StockConstant.ACTIVITIES.add(this)
        login_email = intent.getStringExtra("email")
        println("login email is   " + login_email)
        initViews()
    }

    private fun initViews() {
        toolbarTitleTv.setText("Password")
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        btn_submit.setOnClickListener {
            checkValidation()
        }

        forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }
    }

    private fun checkValidation() {
        if (et_Password_.text.toString().isEmpty())
            AppDelegate.showToast(this, "Please enter password")
        else if (et_Password_.text.toString().length < 6)
            AppDelegate.showToast(this, getString(R.string.short_password))
        else if (!(et_Password_.text.toString().matches(".*[A-Za-z]+.*[0-9]+.*".toRegex()) || et_Password_.text.toString().matches(
                ".*[0-9]+.*[A-Za-z]+.*".toRegex()
            ))
        )

            AppDelegate.showToast(this, getString(R.string.invalid_password))
        else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                emailLogin()
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun emailLogin() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.login(
            login_email,
            et_Password_.text.toString().trim(), "1", ""
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        startActivity(Intent(this@PasswordActivity, DashBoardActivity::class.java)
                            .putExtra("flagcomingFrom", "0"))
                        finish()
                    } /*else if (response.body()!!.status == "0") {
                        startActivity(Intent(this@PasswordActivity, OTPActivity::class.java))
                        finish()
                    }*/
                    displayToast(response.body()!!.message)
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SignupPojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
}