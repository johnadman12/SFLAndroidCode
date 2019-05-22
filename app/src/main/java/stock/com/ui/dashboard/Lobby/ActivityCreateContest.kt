package stock.com.ui.dashboard.Lobby

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
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
    var startdate: Long? = null
    var enddate: Long? = null
    var startmilisec: Long? = null
    var startTimemilisec: Long? = null
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
                enddate = myCalendar.timeInMillis
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
                startdate = myCalendar.timeInMillis
                updateLabel(startDate)
            }

        }

        val timeSetListener1 = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            if (startdate == null)
                displayToast("please select start date first", "warning")
            else {
                startmilisec = myCalendar.timeInMillis + startdate!!
                startTimemilisec = myCalendar.timeInMillis
                val today = Calendar.getInstance()
                val diff = myCalendar.timeInMillis - today.timeInMillis
                val diffSec = diff / 1000
                val minutes = diffSec / 60 % 60
                val hours = diffSec / 3600
                timePicker.minute = minute
                timePicker.hour = hours.toInt()
                if (diff.toString().contains("-"))
                    displayToast("you can't select previous time", "warning")
                else
                    updateTime(startTime)

            }
        }
        val timeSetListener2 = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)
            if (enddate == null)
                displayToast("please select end date first", "warning")
            else {
                var endtimeMilies = myCalendar.timeInMillis + enddate!!
                val diff = myCalendar.timeInMillis - startTimemilisec!!
                if (endtimeMilies > startmilisec!!) {
                    if (diff > 3600000)
                        updateTime(endTime)
                    else
                        displayToast("Time difference should be more than 1 hour ", "error")
                } else
                    displayToast("Time should be ahead from start time", "error")
            }
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
                dialog.datePicker.minDate = startdate!!
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
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false
            ).show()
        }
        endTime.setOnClickListener {
            if (TextUtils.isEmpty(startTime.text.toString())) {
                displayToast("please Select Start Time First", "error")
            } else {
                val dialog = TimePickerDialog(
                    this@ActivityCreateContest, timeSetListener2,
                    myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false
                )
                dialog.updateTime(myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE))
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
                sSports = edtSports.text.toString()
                if (original.length != 0) {
                    if (original.toInt() <= 10000) {
                        if (!TextUtils.isEmpty(sSports) && sSports.toInt() == 0) {
                            calculateContestFee(edtWinningAmount.text.toString(), sSports)
                        } else {
                            edtSports.setText("")
                        }
                    } else {
                        displayToast("Amount cannot be exceed from 10k", "error")
                    }
                }
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
                    if (originalString.toInt() <= 100) {
                        if (originalString.contains(",")) {
                            originalString = originalString.replace(",".toRegex(), "")
                        }
                        if (TextUtils.isEmpty(edtWinningAmount.text.toString()))
                            displayToast("please enter winning amount first", "error")
                        else
                            calculateContestFee(edtWinningAmount.text.toString(), originalString)
                    } else
                        displayToast("spots cannot be exceed from 100", "error")

                } /*else
                    displayToast("please fill sports", "warning")*/
            }
        })

        btn_Next.setOnClickListener {
            if (toggleButton1.isChecked)
                startActivity(Intent(this@ActivityCreateContest, ActivityPriceBreak::class.java))
        }
    }

    private fun updateLabel(date: AppCompatTextView) {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    private fun updateTime(date: AppCompatTextView) {
        val myFormat = "HH:mm a" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    fun calculateContestFee(winAmount: String, s1: String) {
        val collection: Long = (winAmount.toLong() * 100) / 80
        val entryFee: Long = collection / s1.toLong()
        tvContestFee.setText("$" + entryFee.toString())
    }

}