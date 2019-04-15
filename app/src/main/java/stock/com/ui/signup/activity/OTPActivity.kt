package stock.com.ui.signup.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.content_otp.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.SignupPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils
import android.os.CountDownTimer
import android.util.TypedValue
import androidx.core.content.ContextCompat
import stock.com.ui.dashboard.DashBoardActivity


class OTPActivity : BaseActivity(), View.OnClickListener {

    var phoneNumber: String = ""
    var username: String = ""
    var email: String = ""
    var userId: String = ""
    var comingFromActivity: String = ""
    var flag: Boolean = false
    var flagResend: Boolean = false


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_Submit -> {
                println("otp is  " + otp_view.text.toString())
                if (otp_view.text.toString().isEmpty()) {
                    AppDelegate.showToast(this, "Please enter OTP")
                } else {
                    if (NetworkUtils.isConnected()) {
                        if (flag)
                            forgotVerifyOtp()
                        else if(comingFromActivity.equals("profile")){
                            verifyOTPApiContact()
                        }else
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
                    if (flag)
                        resendRequestOTP()
                    else
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
        if (intent != null) {
            comingFromActivity = intent.getStringExtra("isReset")
            if (comingFromActivity.equals("reset")) {
                flag = true
                phoneNumber = intent.getStringExtra(StockConstant.USERPHONE);
                username = intent.getStringExtra(StockConstant.USERNAME);
                email = intent.getStringExtra(StockConstant.USEREMAIL);
                userId = intent.getStringExtra(StockConstant.USERID);
            }else if(comingFromActivity.equals("profile")){
                flag = false
                phoneNumber = intent.getStringExtra("phoneNumber")
                //displayToast(phoneNumber)
            }
            else {
                flag = false
                phoneNumber = intent.getStringExtra("phoneNumber")
            }
        }
       // println("Phone number is   " + phoneNumber)
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


    fun resendOTP() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.resendOtp(phoneNumber)
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        object : CountDownTimer(30000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                resendOTPTv.setText("seconds remaining: " + millisUntilFinished / 1000)
                                resendOTPTv.isEnabled = false
                                resendOTPTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
                                resendOTPTv.setBackgroundDrawable(ContextCompat.getDrawable(this@OTPActivity, R.drawable.red_fill_button))
                                //here you can have your logic to set text to edittext
                            }

                            override fun onFinish() {
                                resendOTPTv.setText("RESEND")
                                resendOTPTv.isEnabled=true
                                resendOTPTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f)
                                resendOTPTv.setBackgroundDrawable(ContextCompat.getDrawable(this@OTPActivity, R.drawable.blue_fill_button))

                            }

                        }.start()
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

    fun resendRequestOTP() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.resendRequestOtp(
            phoneNumber,
            username, email,
            userId
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        object : CountDownTimer(30000, 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                resendOTPTv.setText("seconds remaining: " + millisUntilFinished / 1000)
                                resendOTPTv.isEnabled = false
                                resendOTPTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f)
                                resendOTPTv.setBackgroundDrawable(ContextCompat.getDrawable(this@OTPActivity, R.drawable.red_fill_button))

                                //here you can have your logic to set text to edittext
                            }

                            override fun onFinish() {
                                resendOTPTv.setText("RESEND")
                                resendOTPTv.isEnabled=true
                                resendOTPTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f)
                                resendOTPTv.setBackgroundDrawable(ContextCompat.getDrawable(this@OTPActivity, R.drawable.blue_fill_button))
                            }

                        }.start()
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
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
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
    fun verifyOTPApiContact() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.verify_otp_new(getFromPrefsString(StockConstant.USERID).toString(),otp_view.text.toString(),phoneNumber)
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
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

    fun forgotVerifyOtp() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> =
            apiService.forgot_verify_otp(getFromPrefsString(StockConstant.USERID).toString(), otp_view.text.toString())
        call.enqueue(object : Callback<SignupPojo> {

            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(Intent(this@OTPActivity, DashBoardActivity::class.java))
                        finish()
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

    private suspend fun setTimerForOTP() {
        for (i in 90 downTo 0) {
            val remainingTime = i
            if (i == 0) {
                resendOTPTv.text = getString(R.string.resend_otp)
                resendOTPTv.isEnabled = true
            } else {
                val resendTxt =
                    getString(R.string.resend_in) + " " + remainingTime.toString() + " " + getString(R.string.sec)
                resendOTPTv.text = resendTxt
            }
            delay(1000)
        }
    }

    /*private fun showOTPDialog(Otp: String) {
        val logoutAlertDialog = AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle).create()
        logoutAlertDialog.setTitle(getString(R.string.app_name))
        logoutAlertDialog.setMessage(Html.fromHtml("Use <B>" + Otp + "</B> as your login OTP."))

        logoutAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok)) { dialog, id ->
            logoutAlertDialog.dismiss()
        }
        logoutAlertDialog.show()
    }*/

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
