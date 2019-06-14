package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.content_create_contest.*
import kotlinx.android.synthetic.main.dialogue_wallet_new.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.ContestList
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityCreateContest : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.rel1 -> {
                clickPlusIcon(recycle_spin1, iv1/*, rel1*/)
            }
            R.id.rel2 -> {
                clickPlusIcon(recycle_spin2, iv2/*, rel2*/)
            }
        }
    }

    val myCalendar = Calendar.getInstance()
    var sSports: String = ""
    var admission_comm: Long? = null
    var startdate: Long? = null
    var enddate: Long? = null
    var startmilisec: Long? = null
    var startTimemilisec: Long? = null
    var exchangeId: String = ""
    var marketId: String = ""
    var joinMultiple: Int = 1
    var entryFee: Long? = null
    var list1: ArrayList<ContestList.Market>? = null
    var list2: ArrayList<ContestList.Exchangelist>? = null
    var exchangeAdapter: ExchangespinAdapter? = null
    var marketAdapter: SpinnerAdapter? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_contest)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView() {
        list1 = ArrayList()
        list2 = ArrayList()
        rel1.setOnClickListener(this)
        rel2.setOnClickListener(this)
        getExchangeForContest()
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
                    if (diff > 7200000)
                        updateTime(endTime)
                    else
                        displayToast("Time difference should be more than 2 hour ", "error")
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


        toggleButton1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                joinMultiple = 0
            }
        }
        edtSports.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                var originalString = s.toString()
                if (originalString.length != 0) {
                    //if (originalString.toInt() <= 100) {
                    if (originalString.toInt() >= 2 && originalString.toInt() <= 100) {
                        if (originalString.contains(",")) {
                            originalString = originalString.replace(",".toRegex(), "")
                        }
                        if (TextUtils.isEmpty(edtWinningAmount.text.toString()))
                            displayToast("please enter winning amount first", "error")
                        else
                            calculateContestFee(edtWinningAmount.text.toString(), originalString)
                    } else if (originalString.toInt() > 100)
                        displayToast("spots cannot be exceed from 100", "error")

                } /*else
                    displayToast("please fill sports", "warning")*/
            }
        })

        btn_Next.setOnClickListener {
            if (checkValidation())
                goToPercentage()
        }


    }

    private fun getTimeDifference() {

    }

    private fun updateLabel(date: AppCompatTextView) {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))

    }

    private fun updateTime(date: AppCompatTextView) {
        val myFormat = "hh:mm a" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        date.setText(sdf.format(myCalendar.time))
    }

    /* fun calculateContestFee(winAmount: String, s1: String) {
         val collection: Long = (winAmount.toLong() * 100) / 80
         val entryFee: Long = collection / s1.toLong()
         tvContestFee.setText("$" + entryFee.toString())
     } */


    fun calculateContestFee(winAmount: String, s1: String) {
        admission_comm = winAmount.toLong() * 20 / 100
        val collection: Long = (winAmount.toLong() * 100) / 100 - admission_comm!!
        entryFee= collection / s1.toLong()
        tvContestFee.setText("$" + entryFee.toString())
    }


    fun goToPercentage() {
        getTimeDifference()
        startActivityForResult(
            Intent(this@ActivityCreateContest, ActivityPriceBreak::class.java)
                .putExtra("spot", edtSports.text.toString())
                .putExtra("winningamount", edtWinningAmount.text.toString())
                .putExtra("fee", tvContestFee.text.toString())
                .putExtra("startTime", startTime.text)
                .putExtra("endTime", endTime.text)
                .putExtra("startDate", startDate.text)
                .putExtra("endDate", endDate.text)
                .putExtra("marketId", marketId)
                .putExtra("exchangeId", exchangeId)
                .putExtra("contestName", edtContestName.text.toString())
                .putExtra("admission_commision", admission_comm)
                .putExtra("joinMultiple", joinMultiple.toString()), StockConstant.REDIRECT_CREATED
        )
    }

    fun getExchangeForContest() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestList> =
            apiService.getExchangeForContest(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString()
            )
        call.enqueue(object : Callback<ContestList> {

            override fun onResponse(call: Call<ContestList>, response: Response<ContestList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.market.size > 0)
                            list1 = response.body()!!.market
                        setmarketAdapter(response.body()!!.market)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setmarketAdapter(item: ArrayList<ContestList.Market>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_spin1!!.layoutManager = llm
        recycle_spin1!!.adapter = ExchangespinAdapter(this@ActivityCreateContest, item, this)
    }

    @SuppressLint("WrongConstant")
    private fun setExchangeAdapter(item: ArrayList<ContestList.Exchangelist>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_spin2!!.layoutManager = llm
        recycle_spin2!!.adapter = SpinnerAdapter(this@ActivityCreateContest, item, this)
    }

    private fun clickPlusIcon(
        lin_child_title: RecyclerView,
        header_plus_icon: ImageView/*,
        perspectiveLay: RelativeLayout*/
    ) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.dropdown)
            lin_child_title.visibility = View.VISIBLE
//            perspectiveLay.visibility = View.VISIBLE
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.dropdown)
            lin_child_title.visibility = View.GONE
//            perspectiveLay.visibility = View.GONE
        }
    }

    fun setExchange(name: String, list: ArrayList<ContestList.Exchangelist>, id: String) {
        spinText1.setText(name)
        marketId = id
        recycle_spin1.visibility = View.GONE
        ll_exchange.visibility = View.VISIBLE
        if (list != null && list.size > 0)
            setExchangeAdapter(list)
    }

    fun setExchange(name: String, id: String) {
        marketId = id
        spinText1.setText(name)
        recycle_spin1.visibility = View.GONE
        ll_exchange.visibility = View.GONE
    }


    fun setMarket(name: String, id: String) {
        exchangeId = id   //name reference is opposite
        spinText2.setText(name)
        recycle_spin2.visibility = View.GONE
    }


    fun checkValidation(): Boolean {
        var amount: String = edtWinningAmount.text.toString()
        var spots: String = edtSports.text.toString()
        if (TextUtils.isEmpty(startDate.text)) {
            showSneakBarRed("Please Select StartDate First", "error")
            return false
        } else if (TextUtils.isEmpty(startTime.text)) {
            showSneakBarRed("Please Select StartTime First", "error")
            return false
        } else if (TextUtils.isEmpty(endDate.text)) {
            showSneakBarRed("Please Select EndDate First", "error")
            return false
        } else if (TextUtils.isEmpty(endTime.text)) {
            showSneakBarRed("Please Select EndTime First", "error")
            return false
        } else if (TextUtils.isEmpty(spinText1.text)) {
            showSneakBarRed("Please Select Market First", "error")
            return false
        } else if (TextUtils.isEmpty(edtWinningAmount.text.toString())) {
            showSneakBarRed("Please put some amount to create contest", "error")
            return false
        } else if (amount.toLong() < 100 || amount.toLong() > (10000)) {
            showSneakBarRed("Amount should be in 100 - 10000 range", "error")
            return false
        } else if (TextUtils.isEmpty(edtSports.text.toString())) {
            showSneakBarRed("Please provide contest size", "error")
            return false
        } else if (spots.toLong() < 2 || spots.toLong() > 100) {
            showSneakBarRed("Spots should be between 2-100", "error")
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.REDIRECT_CREATED) {
            if (resultCode == RESULT_OK && data != null) {
                var intent = Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

}