package stock.com.ui.signup.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ValidationUtil
import stock.com.utils.networkUtils.NetworkUtils
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.content_login.*


class ForgotPasswordActivity : BaseActivity() {
    var email: String = "";
    var phone: String = "";
    var username: String = ""

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
        btnCancle.setOnClickListener {
            onBackPressed()
        }
        et_Email_.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.length != 0)
                    if (isNumeric(s.toString())) {
                        countryCode.visibility = VISIBLE
                        et_email.inputType = InputType.TYPE_CLASS_PHONE
                    } else {
                        countryCode.visibility = GONE;
                        et_email.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    }
                else {
                    countryCode.visibility = GONE
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

    fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(.\\d+)?".toRegex())
    }

    private fun checkValidation() {
        var text = et_Email_.text.toString()
        if (text.isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_email))
        else {
            if (text.contains("@")) {
                if (!ValidationUtil.isEmailValid(text))
                    AppDelegate.showToast(this, getString(R.string.valid_email))
                else
                    email = et_Email_.text.toString()
            } else if (Patterns.PHONE.matcher(text).matches()) {
                phone = text;
            } else
                username = et_Email_.text.toString()
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected())
                forgotPass()
            else
                displayToast(getString(R.string.error_network_connection), "error")
        }
    }

    fun forgotPass() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.forgot_pass(
            email, username,
            countryCode.selectedCountryNameCode,
            phone
        )
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>?) {
                d.dismiss()
                if (response?.body() != null) {
                    if (response.body()!!.status == "1") {
//                        saveIntoPrefsString(StockConstant.USERID, response.body()!!.user_id)
                        startActivity(
                            Intent(this@ForgotPasswordActivity, ConfirmationActivity::class.java)
                                .putExtra(StockConstant.USEREMAIL, email)
                                .putExtra(StockConstant.USERNAME, username)
                                .putExtra(StockConstant.USERPHONE, phone)
                                .putExtra(StockConstant.USERID, response.body()!!.user_id)
                                .putExtra(StockConstant.FLAG, "false")
                        )
                        finish()
                    }
                    displayToast(response.body()!!.message, "sucess")
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


}