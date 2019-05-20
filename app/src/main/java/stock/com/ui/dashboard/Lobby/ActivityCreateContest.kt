package stock.com.ui.dashboard.Lobby

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.content_create_contest.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ActivityCreateContest : BaseActivity() {
    val myCalendar = Calendar.getInstance()
    var sSports: String = ""
    var startdate: Date? = null
    var enddate: Date? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contest)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        val dateSetListener1 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                enddate == myCalendar.time
                updateLabel(endDate)
            }

        }
        val dateSetListener2 = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                startdate == myCalendar.time
                updateLabel(startDate)
            }

        }

        val timeSetListener1 = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTime(startTime)
        }
        val timeSetListener2 = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            updateTime(endTime)
        }

        endDate.setOnClickListener {
            if (TextUtils.isEmpty(startDate.text.toString())) {
                displayToast("please Select Start Date First", "error")
            } else {
                val dialog = DatePickerDialog(
                    this@ActivityCreateContest, dateSetListener1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                )
                dialog.datePicker.minDate = myCalendar!!.timeInMillis
                dialog.show()
            }
        }


        startDate.setOnClickListener {
            val dialog = DatePickerDialog(
                this@ActivityCreateContest, dateSetListener2, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
        }

        startTime.setOnClickListener {
            TimePickerDialog(
                this@ActivityCreateContest, timeSetListener1,
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true
            ).show()
        }
        endTime.setOnClickListener {
            if (TextUtils.isEmpty(startTime.text.toString())) {
                displayToast("please Select Start Time First", "error")
            } else {
                val dialog = TimePickerDialog(
                    this@ActivityCreateContest, timeSetListener2,
                    myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true
                )
                dialog.updateTime( myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE))
//                dialog.setMinTime( myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE));
                dialog.show()
            }
        }
        edtWinningAmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                var original = s.toString()
                if (original.length != 0) {
                    if (sSports != null && !sSports.equals("0"))
                        calculateContestFee(edtWinningAmount.text.toString(), original)
                } else
                    edtSports.setText("")
            }
        })

        edtSports.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                var originalString = s.toString()
                if (originalString.length != 0) {
                    if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }
                    if (TextUtils.isEmpty(edtWinningAmount.text.toString()))
                        displayToast("please fill winning amount first", "sucess")
                    else
                        calculateContestFee(edtWinningAmount.text.toString(), originalString)
                } else
                    displayToast("please fill sports", "warning")
                sSports = originalString
            }
        })
    }

    private fun updateLabel(date: AppCompatTextView) {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    private fun updateTime(date: AppCompatTextView) {
        val myFormat = "HH:mm" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    fun calculateContestFee(winAmount: String, s1: String) {
        var collection: Long = (winAmount.toLong() * 100) / 80
        val entryFee: Long = collection / s1.toLong()
        tvContestFee.setText("$" + entryFee.toString())
    }

}