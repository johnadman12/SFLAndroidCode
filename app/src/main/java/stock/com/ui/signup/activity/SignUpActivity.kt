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
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import stock.com.constant.Tags
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.*
import java.util.*
import java.text.SimpleDateFormat
import android.text.Html
import android.text.method.LinkMovementMethod
import android.R.id
import android.text.TextUtils
import android.widget.TextView


class SignUpActivity : BaseActivity(), View.OnClickListener, CountryCodePicker.OnCountryChangeListener {
    lateinit var countrycodeList: ArrayList<CountryDataPojo>
    var countryCodePicker: CountryCodePicker? = null;
    val myCalendar = Calendar.getInstance()
    private var term_condition_accept: Int = 0
    private var notification_accept: Int = 0
    private var socialId: String = "";
    lateinit var socialmodel: SocialModel
    val regexStr = "^.*(?=.*\\d)(?=.*[a-zA-Z]).*$"

    private var flag: String = "";

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(stock.com.R.layout.activity_signup)
        initViews()

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val userAge = GregorianCalendar(year, monthOfYear, dayOfMonth)
                val minAdultAge = GregorianCalendar()
                minAdultAge.add(Calendar.YEAR, -18)
                if (minAdultAge.before(userAge)) {
                    displayToast("Age should be above 18", "error")
                } else
                    updateLabel()
            }

        }
        et_dob.setOnClickListener {
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
                this.finish()
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
        et_dob.setOnClickListener(this)
        tvagree.isClickable = true
        tvagree.movementMethod = LinkMovementMethod.getInstance()
//        val text = "<a href='http://www.google.com'> Google </a>"
//        tvagree.text = Html.fromHtml(text)
        countryCodePicker!!.setOnCountryChangeListener(this)

        /*et_Password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length != 0) {
                    et_conf_Password.isEnabled = true
                    et_conf_Password.setText("")
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
            }
        })*/

        /*  et_conf_Password.addTextChangedListener(object : TextWatcher {
              override fun afterTextChanged(s: Editable) {
  //                val regexStr = "/^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)\$/"
                  if (s.length != 0) {
                      if (!(et_Password.text.toString().matches(regexStr.toRegex()))) {
                          AppDelegate.showToast(this@SignUpActivity, getString(R.string.invalid_password))
                          et_Password.setError(getString(R.string.invalid_password))
                          et_conf_Password.isEnabled = false
                      } else if (!et_conf_Password.text.toString().equals(et_Password.text.toString())) {
                          et_conf_Password.isEnabled = true
                          et_conf_Password.setError(getString(R.string.doesnot_match))
                      }
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
          })*/




        if (intent.extras != null) {
            flag = intent!!.getStringExtra(StockConstant.FLAG)
        }

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
                displayToast(getString(R.string.error_network_connection), "error")
        }
    }


    private fun checkValidation() {
        println("Term and condition" + term_condition_accept)
        if (et_UserNamefirst.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter First Name");
        } else if (et_UserNamelast.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter Last Name");
        } else if (et_UserName.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter Username")
        } else if (et_dob.text.toString().trim().isEmpty()) {
            AppDelegate.showToast(this, "Please enter Date Of Birth")
        } else if (et_Mobile.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_phone_number))
        else if (!ValidationUtil.isPhoneValid(et_Mobile.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_phone_number))
        else if (et_Email.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_email))
        else if (!ValidationUtil.isEmailValid(et_Email.text.toString()))
            AppDelegate.showToast(this, getString(R.string.valid_email))
        else if (TextUtils.isEmpty(et_Password.text.toString()))
            AppDelegate.showToast(this, getString(R.string.enter_password))
        else if (et_Password.text.toString().length < 6) {
            et_Password.setError(getString(R.string.short_password))
            AppDelegate.showToast(this, getString(R.string.short_password))
        }
        else if (!(et_Password.text.toString().matches(regexStr.toRegex()))) {
            et_Password.setError(getString(R.string.invalid_password))
            AppDelegate.showToast(this, getString(R.string.invalid_password))
        }
        else if (TextUtils.isEmpty(et_conf_Password.text.toString())) {
            et_conf_Password.setError(getString(R.string.enter_confirm_password))
            AppDelegate.showToast(this, getString(R.string.enter_confirm_password))
        }
        else if (!et_conf_Password.text.toString().equals(et_Password.text.toString())) {
            et_conf_Password.setError(getString(R.string.doesnot_match))
        AppDelegate.showToast(this, getString(R.string.doesnot_match))
        }
        else if (term_condition_accept == 0) {
            AppDelegate.showToast(
                this,
                "Please check the box to confirm agreement to DFXchange T&C and Privacy Policy."
            )
        } else {
            if (NetworkUtils.isConnected()) {
                register("normal", "2")
            } else {
                displayToast(getString(R.string.error_network_connection), "error")
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
            et_Mobile.text.toString().trim(),
            countryCodeHolder.selectedCountryNameCode,
            et_dob.text.toString(),
            et_UserName.text.toString().trim(),
            et_UserNamefirst.text.toString(),
            et_UserNamelast.text.toString(),
            "0",
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
                                .putExtra("userId", response.body()!!.user_data!!.id)
                                .putExtra(StockConstant.USERCOUNTRYCODE, countryCodeHolder.selectedCountryNameCode)
                                .putExtra("isReset", "signup")
                        )
                        finish()
                    }
                    displayToast(response.body()!!.message, "sucess")
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

    fun registerSocial(type: String, socialType: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.registerSocial(
            et_Email.text.toString().trim(),
            et_Password.text.toString(),
            et_EnviteCode.text.toString().trim(),
            et_Mobile.text.toString().trim(),
            countryCodeHolder.selectedCountryNameCode,
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
                                .putExtra(StockConstant.USERCOUNTRYCODE, countryCodeHolder.selectedCountryNameCode)
                                .putExtra("isReset", "signup")
                        )
                        finish()
                    }
                    displayToast(response.body()!!.message, "sucess")
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
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>?, t: Throwable?) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    override fun onCountrySelected() {
        Toast.makeText(this, "Country Code " + countryCodePicker!!.selectedCountryCode, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (!flag.equals("false")) {
            startActivity(Intent(this@SignUpActivity, WelcomeActivity::class.java))
            finish()
        } else {
            super.onBackPressed();
        }
    }
}
