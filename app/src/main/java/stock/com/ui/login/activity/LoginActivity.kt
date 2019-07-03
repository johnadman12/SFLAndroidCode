package stock.com.ui.login.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.model.SocialModel
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.activity.ForgotPasswordActivity
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.AppDelegate
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils
import androidx.annotation.StyleRes
import com.jesusm.kfingerprintmanager.KFingerprintManager
import io.michaelrocks.libphonenumber.android.Phonenumber


class LoginActivity : BaseActivity(), View.OnClickListener {
    private var pass_remembered: Int = 0
    private val KEY = "my_Finger"
    @StyleRes
    private var dialogTheme: Int = 0

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_Next -> {
                if (et_email.text.toString().isEmpty())
                    AppDelegate.showToast(this, getString(R.string.enter_phone_number))
                else if (et_pass.text.toString().isEmpty())
                    AppDelegate.showToast(this, getString(R.string.enter_password))
                else {
                    if (NetworkUtils.isConnected()) {
                        if (et_email.text.toString().contains("@")) {
                            emailLogin()
                        } else {
                            phoneLoginApi()
                        }
                    } else {
                        displayToast(getString(R.string.error_network_connection), "error")
                    }


                    /*else if (et_email.text.toString().contains("@") && ValidationUtil.isEmailValid(et_email.text.toString())) {
                        AppDelegate.showToast(this, getString(R.string.valid_email))
                    } else if (ValidationUtil.isPhoneValid(et_email.text.toString())) {
                        AppDelegate.showToast(this, getString(R.string.valid_phone_number))
                    } else
                        if (NetworkUtils.isConnected()) {
                            callLoginApi()
                        } else
                            AppDelegate.showToast(this, getString(R.string.error_network_connection))*/
                }
            }
            R.id.txt_Signup -> {
                startActivity(Intent(this, SignUpActivity::class.java).putExtra(StockConstant.FLAG, "true"))
                finish()
            }
            R.id.img_back -> {
                onBackPressed()
            }
            R.id.text_forgot -> {
                startActivity(Intent(this, ForgotPasswordActivity::class.java))
                finish()
            }
            R.id.faceimg -> {
                startActivity(Intent(this, FingerPrintActivity::class.java))
            }

        }
    }


    private fun createFingerprintManagerInstance(): KFingerprintManager {
        val fingerprintManager = KFingerprintManager(this, KEY)
        fingerprintManager.setAuthenticationDialogStyle(dialogTheme)
        return fingerprintManager
    }

    fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(.\\d+)?".toRegex())
    }

    lateinit var socialModel: SocialModel
    fun phoneLoginApi() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.phoneLogin(
            et_email.text.toString().trim(),
            countryCodelogin.selectedCountryNameCode,
            et_pass.text.toString().trim(),
            "0",
            SessionManager.getInstance(this@LoginActivity).token
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        socialModel = SocialModel()
                        socialModel.isSocial = "1"
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        if (pass_remembered == 1) {
                            saveIntoPrefsString(StockConstant.PASSWORD, et_pass.text.toString().trim())
                        } else {
                            saveIntoPrefsString(StockConstant.PASSWORD, "")
                        }
                        startActivity(
                            Intent(this@LoginActivity, DashBoardActivity::class.java)
                        )
                        finish();
                    }
                    displayToast(response.body()!!.message, "warning")
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SignupPojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun emailLogin() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.login(
            et_email.text.toString(),
            et_pass.text.toString().trim(), "0", SessionManager.getInstance(this@LoginActivity).token
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
                        socialModel = SocialModel()
                        socialModel.isSocial = "1"
                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
                        saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        if (pass_remembered == 1) {
                            saveIntoPrefsString(StockConstant.PASSWORD, et_pass.text.toString().trim())
                        } else {
                            saveIntoPrefsString(StockConstant.PASSWORD, "")
                        }
                        startActivity(
                            Intent(this@LoginActivity, DashBoardActivity::class.java)
                        )
                        finish();
                    } /*else if (response.body()!!.status == "0") {
                        startActivity(Intent(this@PasswordActivity, OTPActivity::class.java))
                        finish()
                    }*/
                    else
                        displayToast(response.body()!!.message, "warning")
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SignupPojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    /* saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
     saveIntoPrefsString(StockConstant.ACCESSTOKEN, response.body()!!.token!!)
     saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
     if (pass_remembered == 1)
     saveIntoPrefsString(StockConstant.PASSWORD, et_pass.text.toString().trim())
     else
     saveIntoPrefsString(StockConstant.PASSWORD, "")
 */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener(this)
        btn_Next.setOnClickListener(this)
        txt_Signup.setOnClickListener(this)
        text_forgot.setOnClickListener(this)
        faceimg.setOnClickListener(this)
        checkRemebered.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                pass_remembered = 1
            }
        }
        //before functionality
        /* if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.PASSWORD))) {
             et_email.setText(getUserData().email)
             et_pass.setText(getFromPrefsString(StockConstant.PASSWORD))
         }*/

        if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.PASSWORD))) {
            et_email.setText(getUserData().email)
            et_pass.setText(getFromPrefsString(StockConstant.PASSWORD))
        }

        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length != 0)
                    if (isNumeric(s.toString())) {
                        countryCodelogin.visibility = VISIBLE
                        et_email.inputType = InputType.TYPE_CLASS_PHONE
                    } else {
                        countryCodelogin.visibility = GONE;
                        et_email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    }
                else {
                    countryCodelogin.visibility = GONE
                    et_email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val regexStr = "^[0-9]*$"
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity(Intent(this@LoginActivity, WelcomeActivity::class.java))
        finish()
    }
}

/*  private fun checkUserVerify(socialModel: SocialModel) {
//      {"fb_id":"124587","google_id":"","language":"en","mobile_number":"9955214155","name":"Nidhi","email":"nidhimittal@m_mailinator.com"}
        val loginRequest = HashMap<String, String>()
        loginRequest["fb_id"] = socialModel.fb_id
        loginRequest["google_id"] = socialModel.google_id
        loginRequest["name"] = socialModel.first_name
        loginRequest["email"] = socialModel.email
        loginRequest["mobile_number"] = ""
        loginRequest["language"] = FantasyApplication.getInstance().getLanguage()
        GlobalScope.launch(Dispatchers.Main) {
            AppDelegate.showProgressDialog(this@LoginActivity)
            try {
                val request = ApiClient.getClient()!!.(loginRequest)
                val response = request.await()
                AppDelegate.LogT("Response=>" + response);
                AppDelegate.hideProgressDialog(this@LoginActivity)
                if (response.response!!.status) {
                    AppDelegate.showToast(this@LoginActivity, response.response!!.message)
                    startActivity(
                        Intent(this@LoginActivity, DashBoardActivity::class.java)
                            .putExtra(IntentConstant.DATA, response.response!!.data!!)
//                            .putExtra(IntentConstant.MOBILE, response.response!!.data!!.phone)
//                            .putExtra(IntentConstant.USER_ID, response.response!!.data!!.user_id)
                    )

                } else {
                    startActivity(
                        Intent(this@LoginActivity, SignUpActivity::class.java)
                            .putExtra(IntentConstant.DATA, socialModel)GoogleSignInOptions
//                            .putExtra(IntentConstant.MOBILE, response.response!!.data!!.phone)
//                            .putExtra(IntentConstant.USER_ID, response.response!!.data!!.user_id)
                    )

                }
            } catch (exception: Exception) {
                AppDelegate.hideProgressDialog(this@LoginActivity)
            }
        }
    }  */