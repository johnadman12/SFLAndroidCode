package stock.com.ui.signup.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_otp.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.application.FantasyApplication
import stock.com.constant.IntentConstant
import stock.com.constant.Tags
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.apiRequest.VerifyOtpRequest
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils

class OTPActivity : BaseActivity(), View.OnClickListener {

    var phoneNumber: String = ""

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_Submit -> {
                println("otp is  " + otp_view.text.toString())
                if (otp_view.text.toString().isEmpty()) {
                    AppDelegate.showToast(this, "Please enter OTP")
                } else {
                    if (NetworkUtils.isConnected()) {
                        verifyOTPApi()
                    } else {
                        Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
                    }

                }

                /*var intent=(Intent(this, DashBoardActivity::class.java))
                intent.putExtra("welcome","2")
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img_AppIcon , "img_logo")
                startActivity(intent*//*, options.toBundle()*//*)
                finish()*/
            }
            R.id.img_back -> {
                onBackPressed()
            }
            R.id.resendOTPTv -> {
                if (NetworkUtils.isConnected()) {
                    resendOTP()
                } else {
                    Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        StockConstant.ACTIVITIES.add(this)
        phoneNumber = intent.getStringExtra("phoneNumber")
        println("Phone number is   " + phoneNumber)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener(this)
        btn_Submit.setOnClickListener(this)
        resendOTPTv.setOnClickListener(this)
        /*btn_Submit.setOnClickListener(this)
        otp = intent.getStringExtra(IntentConstant.OTP)
        phone = intent.getStringExtra(IntentConstant.MOBILE)
        user_id = intent.getStringExtra(IntentConstant.USER_ID)
        resendOTPLayout.setOnClickListener(this)
        resendOTPLayout.isEnabled = false
        showOTPDialog(otp)
//      setTimerForOTP()
        GlobalScope.launch(Dispatchers.Main) {
            setTimerForOTP()
        }*/
    }

    fun verifyOTPApi() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.otpVerify(
            getFromPrefsString(StockConstant.USERID).toString(),
            otp_view.text.toString()
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        startActivity(Intent(this@OTPActivity, DashBoardActivity::class.java))
                        finish()
                    }
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

    fun resendOTP() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.resendOtp(phoneNumber)
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {

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
    /*override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_Submit -> {
                if (otp_view.text.toString().isNotEmpty() && otp_view.text.toString().length == 6) {
                    var getOtp = otp_view.text.toString()
                    if (getOtp == otp) {
                        if (AppDelegate.isNetworkAvailable(this))
                            prepareData()
                    } else
                        AppDelegate.showToast(this, getString(R.string.invalid_otp))
                } else
                    AppDelegate.showToast(this, getString(R.string.invalid_otp))

            }
            R.id.resendOTPLayout -> {
                if (NetworkUtils.isConnected()) {
                    callResendOtpApi()
                } else
                    AppDelegate.showToast(this, getString(R.string.error_network_connection))
            }

        }
    }

    private fun prepareData() {
        val verifyOtpRequest = VerifyOtpRequest()
        verifyOtpRequest.device_id = pref!!.fcMtokeninTemp
        verifyOtpRequest.otp = otp
        verifyOtpRequest.language = FantasyApplication.getInstance().getLanguage()
        verifyOtpRequest.device_type = Tags.device_type
        verifyOtpRequest.user_id = user_id
        callVarifyOtpApi(verifyOtpRequest)
    }

    var otp = ""
    var user_id = ""
    var phone = ""




    private fun callVarifyOtpApi(verifyOtpRequest: VerifyOtpRequest) {
        GlobalScope.launch(Dispatchers.Main) {
            AppDelegate.showProgressDialog(this@OTPActivity)
            try {
                val request = ApiClient.client
                    .getRetrofitService()
                    .verify_otp(verifyOtpRequest)
                val response = request.await()
                AppDelegate.LogT("Response=>" + response);
                AppDelegate.hideProgressDialog(this@OTPActivity)
                if (response.response!!.status) {
                    AppDelegate.showToast(this@OTPActivity, response.response!!.message)
                    pref!!.userdata = response.response!!.data
                    startActivity(Intent(this@OTPActivity, DashBoardActivity::class.java))
                    finish()
                } else {
                    AppDelegate.showToast(this@OTPActivity, response.response!!.message)
                }
            } catch (exception: Exception) {
                AppDelegate.hideProgressDialog(this@OTPActivity)
            }
        }
    }

    private fun callResendOtpApi() {
        val resendOTPRequest = HashMap<String, String>()
        resendOTPRequest["mobile_number"] = phone
        resendOTPRequest["language"] = FantasyApplication.getInstance().getLanguage()
        GlobalScope.launch(Dispatchers.Main) {
            AppDelegate.showProgressDialog(this@OTPActivity)
            try {
                val request = ApiClient.client
                    .getRetrofitService()
                    .resend_otp(resendOTPRequest)
                val response = request.await()
                AppDelegate.LogT("Response=>" + response);
                AppDelegate.hideProgressDialog(this@OTPActivity)
                if (response.response!!.status) {
                    AppDelegate.showToast(this@OTPActivity, response.response!!.message)
                    otp = response.response!!.data!!.otp
                    showOTPDialog(response.response!!.data!!.otp)
                    resendOTPLayout.isEnabled = false
                    setTimerForOTP()
                } else {
                    AppDelegate.showToast(this@OTPActivity, response.response!!.message)
                }
            } catch (exception: Exception) {
                AppDelegate.hideProgressDialog(this@OTPActivity)
            }
        }
    }

    private suspend fun setTimerForOTP() {
        for (i in 60 downTo 0) {
            val remainingTime = i
            if (i==0){
                resendOTPTv.text = getString(R.string.resend_otp)
                resendOTPLayout.isEnabled = true
            }else {
                val resendTxt =
                    getString(R.string.resend_in) + " " + remainingTime.toString() + " " + getString(R.string.sec)
                resendOTPTv.text = resendTxt
            }
            delay(1000)
        }

    }

    private fun showOTPDialog(Otp: String) {
        val logoutAlertDialog = AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create()
        logoutAlertDialog.setTitle(getString(R.string.app_name))
        logoutAlertDialog.setMessage(Html.fromHtml("Use <B>" + Otp + "</B> as your login OTP."))

        logoutAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, id ->
            logoutAlertDialog.dismiss()
        }
        logoutAlertDialog.show()
    }*/
}
