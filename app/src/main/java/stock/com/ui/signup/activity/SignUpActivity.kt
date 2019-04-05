package stock.com.ui.signup.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_signup.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.application.FantasyApplication
import stock.com.constant.IntentConstant
import stock.com.model.SocialModel
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.login.activity.LoginActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.SignupPojo
import stock.com.ui.signup.apiRequest.SignUpRequest
import stock.com.ui.signup.pojo.CountryDataPojo
import stock.com.utils.networkUtils.NetworkUtils
import android.app.DatePickerDialog
import android.view.View.GONE
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import stock.com.constant.Tags
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.*
import java.util.*
import java.text.SimpleDateFormat


class SignUpActivity : BaseActivity(), View.OnClickListener, CountryCodePicker.OnCountryChangeListener {
    lateinit var countrycodeList: ArrayList<CountryDataPojo>
    var countryCodePicker: CountryCodePicker? = null;
    val myCalendar = Calendar.getInstance()
    private var term_condition_accept: Int = 1
    private var notification_accept: Int = 0
    private var socialId: String = "";
    lateinit var socialmodel: SocialModel

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(stock.com.R.layout.activity_signup)
        StockConstant.ACTIVITIES.add(this)
        initViews()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateLabel()
            }

        }
        iv_calendar.setOnClickListener {

            val dialog = DatePickerDialog(
                this@SignUpActivity, dateSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }

//         getCountryList()
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            stock.com.R.id.btn_Register -> {
                if (userData != null) {
                    socialId = userData!!.social_id;
                    checkValidationSocial()
                } else
                    checkValidation()

            }
            R.id.txt_Login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.img_back -> {
                onBackPressed()
            }


            /* R.id.txt_TC -> {
                 startActivity(Intent(this, WebUrlActivity::class.java))

             }
 */
        }
    }

    var userData: SocialModel? = null
//    var ccp: CountryCodePicker? = null


    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        et_dob.setText(sdf.format(myCalendar.time))
    }

    private fun initViews() {
        countrycodeList = ArrayList()
        countryCodePicker = findViewById(R.id.countryCodeHolder)
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        img_back.setOnClickListener(this)
        btn_Register.setOnClickListener(this)
        txt_Login.setOnClickListener(this)
        iv_calendar.setOnClickListener(this)
//        txt_TC.setOnClickListener(this)
        countryCodePicker!!.setOnCountryChangeListener(this)
        try {
            userData = intent.getParcelableExtra(IntentConstant.DATA)
        } catch (e: Exception) {

        }
        if (userData != null) {
            et_Email.setText(userData!!.email)
            et_Email.isEnabled = false
//            if (userData!!.isSocial.equals("0")) {
            rl_password.visibility = GONE
            rl_Con_pass.visibility = GONE
//            }
        }
        accept_term_condition.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                term_condition_accept = 1
            else
                term_condition_accept = 0
        }
        notification_check.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                notification_accept = 1
            }
        }

    }

    private fun checkValidationSocial() {
        if (et_Mobile.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_phone_number))
        else if (!ValidationUtil.isPhoneValid(et_Mobile.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_phone_number))
        else if (et_Email.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_email))
        else if (!ValidationUtil.isEmailValid(et_Email.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_email))
        else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                registerSocial("social", "1")
            } else
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
        }
    }


    private fun checkValidation() {
        println("Term and condition" + term_condition_accept)
        if (et_UserNamefirst.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter first name");
        } else if (et_UserNamelast.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter last name");
        } else if (et_UserName.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter user name")
        } else if (et_dob.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter date of birth")
        } else if (et_Mobile.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_phone_number))
        else if (!ValidationUtil.isPhoneValid(et_Mobile.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_phone_number))
        else if (et_Email.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_email))
        else if (!ValidationUtil.isEmailValid(et_Email.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_email))
        else if (et_Password.text.toString().length < 6)
            AppDelegate.showToast(this, getString(R.string.short_password))
        else if (!(et_Password.text.toString().matches(".*[A-Za-z]+.*[0-9]+.*".toRegex()) || et_Password.text.toString().matches(
                ".*[0-9]+.*[A-Za-z]+.*".toRegex()
            ))

        )
            AppDelegate.showToast(this, getString(R.string.invalid_password))
        else if (!et_conf_Password.text.toString().equals(et_Password.text.toString()))
            AppDelegate.showToast(this, "password does not match")
        else if (term_condition_accept == 0) {
            AppDelegate.showToast(this, "Please accept term and condition")
        } else {
            if (NetworkUtils.isConnected()) {
                register("normal", "2")
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun register(type: String, socialType: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.register(
            et_Email.text.toString().trim(),
            et_Password.text.toString(),
            et_EnviteCode.text.toString().trim(),
            countryCodeHolder.selectedCountryCode +
                    et_Mobile.text.toString().trim(),
            et_dob.text.toString(),
            et_UserName.text.toString().trim(),
            et_UserNamefirst.text.toString(),
            et_UserNamelast.text.toString(),
            "1",
            SessionManager.getInstance(this@SignUpActivity).token,
            notification_accept.toString(),
            term_condition_accept.toString(),
            type
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
//                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(
                            Intent(this@SignUpActivity, OTPActivity::class.java)
                                .putExtra("phoneNumber", et_Mobile.text.toString().trim())
                                .putExtra("isReset", "signup")
                        )
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

    fun registerSocial(type: String, socialType: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.registerSocial(
            et_Email.text.toString().trim(),
            et_Password.text.toString(),
            et_EnviteCode.text.toString().trim(),
            countryCodeHolder.selectedCountryCode +
                    et_Mobile.text.toString().trim(),
            et_dob.text.toString(),
            et_UserName.text.toString().trim(),
            et_UserNamefirst.text.toString(),
            et_UserNamelast.text.toString(),
            "1",
            SessionManager.getInstance(this@SignUpActivity).token,
            notification_accept.toString(),
            term_condition_accept.toString(),
            socialId,
            socialType,
            type
        )
        call.enqueue(object : Callback<SignupPojo> {
            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_data!!.id)
//                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(
                            Intent(this@SignUpActivity, OTPActivity::class.java)
                                .putExtra("phoneNumber", et_Mobile.text.toString().trim())
                                .putExtra("isReset", "signup")
                        )
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

    fun getCountryList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.getCountry()
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>?) {
                d.dismiss()
                if (response?.body() != null) {

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

    override fun onCountrySelected() {
        Toast.makeText(this, "Country Code " + countryCodePicker!!.selectedCountryCode, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        startActivity(Intent(this@SignUpActivity, WelcomeActivity::class.java))
        finish()
    }


}
