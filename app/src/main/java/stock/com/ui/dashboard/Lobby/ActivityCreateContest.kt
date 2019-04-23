package stock.com.ui.dashboard.Lobby

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatTextView
import kotlinx.android.synthetic.main.create_contest_activity.*
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
        setContentView(R.layout.create_contest_activity)
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

    }

    private fun updateLabel(date: AppCompatTextView) {
        val myFormat = "yyyy-MM-dd" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

}