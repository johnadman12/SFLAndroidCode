package stock.com.ui.dashboard.Lobby

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.content_create_contest.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.SimpleDateFormat
import java.util.*

class ActivityCreateContest : BaseActivity() {
    val myCalendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contest)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

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
            val dialog = DatePickerDialog(
                this@ActivityCreateContest, dateSetListener1, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.minDate = System.currentTimeMillis()
            dialog.show()
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
            TimePickerDialog(
                this@ActivityCreateContest, timeSetListener1,
                myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true
            ).show()
        }

    }

    private fun updateLabel(date: AppCompatTextView) {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    private fun updateTime(date: AppCompatTextView) {
        val myFormat = "HH:mm" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }


}