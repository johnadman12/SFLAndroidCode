package stock.com.ui.login.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_signup.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.application.FantasyApplication
import stock.com.constant.IntentConstant
import stock.com.constant.PrefConstant
import stock.com.constant.Tags
import stock.com.data.Prefs
import stock.com.model.FbDetails
import stock.com.model.SocialModel
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.activity.ForgotPasswordActivity
import stock.com.ui.signup.activity.OTPActivity
import stock.com.ui.signup.activity.PasswordActivity
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var pass_remembered: Int = 0
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
                        Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
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
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            R.id.img_back -> {
                finish()
            }
            R.id.text_forgot -> {
                startActivity(Intent(this, ForgotPasswordActivity::class.java))
                finish()
            }

        }
    }

    lateinit var socialModel: SocialModel
    fun phoneLoginApi() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.phoneLogin(
            et_email.text.toString().trim(),
            et_pass.text.toString().trim(),
            "1",
            getFromPrefsString(Tags.FCMtoken).toString()
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
                        if (pass_remembered == 1)
                            saveIntoPrefsString(StockConstant.PASSWORD, et_pass.text.toString().trim())
                        startActivity(
                            Intent(this@LoginActivity, DashBoardActivity::class.java)
                        )
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

    fun emailLogin() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.login(
            et_email.text.toString(),
            et_pass.text.toString().trim(), "1", "123456"
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
                        if (pass_remembered == 1)
                            saveIntoPrefsString(StockConstant.PASSWORD, et_pass.text.toString().trim())
                        startActivity(
                            Intent(this@LoginActivity, DashBoardActivity::class.java)
                        )
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
        checkRemebered.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                pass_remembered = 1
            }
        }
        if (checkRemebered.isChecked) {
            et_email.setText(pref!!.userdata!!.email)
            et_pass.setText(pref!!.userdata!!.password)
        }

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