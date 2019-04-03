package stock.com.ui.signup.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_confirmation.*
import kotlinx.android.synthetic.main.activity_forgot_password.*
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
import stock.com.ui.pojo.SignupPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ValidationUtil
import stock.com.utils.networkUtils.NetworkUtils
import java.text.SimpleDateFormat
import java.util.*

class ConfirmationActivity : BaseActivity() {
    val myCalendar = Calendar.getInstance()
    var phoneNumber: String = ""
    var username: String = ""
    var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar_outside)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (intent != null) {
            phoneNumber = intent.getStringExtra(StockConstant.USERPHONE);
            username = intent.getStringExtra(StockConstant.USERNAME);
            email = intent.getStringExtra(StockConstant.USEREMAIL);
        }
        img_back.setOnClickListener {
            onBackPressed()
        }
        tv_requestOtp.setOnClickListener {
            requestOTP()
        }
        bt_next.setOnClickListener {
            checkValidation()
        }
        bt_cancel.setOnClickListener {
            finish()
        }
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
                this@ConfirmationActivity, dateSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        et_dob.setText(sdf.format(myCalendar.time))
    }

    private fun checkValidation() {

        if (et_dob.text.toString().isEmpty())
            AppDelegate.showToast(this, getString(R.string.enter_dob))
        else {
            AppDelegate.hideKeyBoard(this)
            if (NetworkUtils.isConnected()) {
                dobSubmit()
            } else {
                Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun dobSubmit() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> =
            apiService.dob_verify(getFromPrefsString(StockConstant.USERID).toString(), et_dob.text.toString().trim())
        call.enqueue(object : Callback<SignupPojo> {

            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(
                            Intent(this@ConfirmationActivity, ActivityResetPassword::class.java)
                        )
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

    fun requestOTP() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SignupPojo> = apiService.requestOtp(
            getFromPrefsString(StockConstant.USERID).toString(), username, email, phoneNumber)
        call.enqueue(object : Callback<SignupPojo> {

            override fun onResponse(call: Call<SignupPojo>, response: Response<SignupPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        saveUserData(StockConstant.USERDATA, response.body()!!.user_data)
                        startActivity(Intent(this@ConfirmationActivity, OTPActivity::class.java)
                            .putExtra("isReset","reset")
                            .putExtra(StockConstant.USEREMAIL, email)
                            .putExtra(StockConstant.USERNAME, username)
                            .putExtra(StockConstant.USERPHONE, phoneNumber)
                        )
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
}