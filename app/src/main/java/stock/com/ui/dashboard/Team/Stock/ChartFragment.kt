package stock.com.ui.dashboard.Team.Stock

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_chart.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.CandlesticChartmarket
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException
import kotlin.collections.ArrayList


class ChartFragment : BaseFragment(), View.OnClickListener {
    var data: CandlesticChartmarket.Data? = null
    var type = "application/json"
    var chartToken = "1efc85ca-e723-4546-9f72-cf795e499eaf"
    var symbol: String = ""
    var url: String = "https://dfxchange.com/dfxchange/api/controllers/graph.php/"
    private var loadingFinished = true
    private var redirect = false

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.txt_expand -> {
                startActivity(
                    Intent(activity, StockChartActivity::class.java)
                        .putExtra(StockConstant.CHART, symbol)
                )
            }

            R.id.imgPlus -> {
                chart11.zoomOut()
            }
            R.id.imgMinus -> {
                chart11.zoomIn()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txt_expand.setOnClickListener(this)
        if (arguments != null)
            symbol = arguments!!.getString("Stockname")

        if (symbol != null) {
//            getChartData(symbol)
            url = url + symbol
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setBackgroundColor(0x00000000);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.loadUrl(url)
        }


    }

    fun getChartData(symbol: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientCharts()!!.create(ApiInterface::class.java)
        val call: Call<CandlesticChartmarket> =
            apiService.getChartApi(
                type, chartToken, symbol, getdateOld(), getdate()

            )
        call.enqueue(object : Callback<CandlesticChartmarket> {

            override fun onResponse(call: Call<CandlesticChartmarket>, response: Response<CandlesticChartmarket>) {
                d.dismiss()
                if (response.body() != null) {
                    val yValsCandleStick = ArrayList<CandleEntry>()
                    data = response.body()!!.data
                    var list = response.body()!!.data!!.quotes!!
                    for (i in 0 until list!!.size) {
                        var usd = list!!.get(i).quote!!.uSD
                        if (usd != null)
                            yValsCandleStick.add(
                                CandleEntry(
                                    parseDateToddMMyyyy(usd.timestamp),
                                    usd.high,
                                    usd.low,
                                    usd.open,
                                    usd.close
                                )
                            )
                    }
                    setChart(yValsCandleStick)

                }
                /*else {
                   displayToast(resources.getString(R.string.internal_server_error), "error")
                   d.dismiss()
               }*/
            }

            override fun onFailure(call: Call<CandlesticChartmarket>, t: Throwable) {

                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

    private fun setChart(yValsCandleStick: ArrayList<CandleEntry>) {
        val set1 = CandleDataSet(yValsCandleStick, "")
        set1.setShadowColor(Color.GRAY);
        set1.setShadowWidth(1.5f);



        set1.decreasingColor = Color.rgb(191, 39, 12)
        set1.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.increasingColor = Color.rgb(148, 196, 116)
        set1.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.setNeutralColor(Color.MAGENTA);
        set1.setHighlightLineWidth(0.5f)


        set1.setDrawValues(false);
        chart11.animateX(2000)

        /* for (set in chart11.getData().getDataSets())
             set.setDrawValues(!set.isDrawValuesEnabled())*/

        chart11.setOnClickListener(this)

        chart11.legend.isEnabled = false
        chart11.setTouchEnabled(true)
        chart11.isDoubleTapToZoomEnabled = false

        //chart11.setMaxVisibleValueCount(0)

        val xAxis_candle = chart11.xAxis
        xAxis_candle.setValueFormatter { value, _ ->
            SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).format(value.toLong())
        }

        xAxis_candle.position = XAxis.XAxisPosition.BOTTOM
        chart11.axisLeft.setDrawGridLines(false)
        xAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)

        val yAxis_candle = chart11.axisLeft
        yAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)
        val rightAxis_candle = chart11.axisRight
        rightAxis_candle.setDrawLabels(false)
        val data = CandleData(set1)
        // set data
        chart11.setData(data)
        chart11.invalidate()
    }

    fun getdate(): String {
        val c = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = df.format(c)
        return formattedDate
    }

    fun getdateOld(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -11)
        val date = calendar.time
        val df = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = df.format(date)
        return formattedDate
    }

    fun parseDateToddMMyyyy(time: String): Float {
//        val inputPattern = "EEE MMM dd HH:mm:ss zzz yyyy"
        val outputPattern: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        outputPattern.setTimeZone(TimeZone.getTimeZone("UTC"));
        var dateInMillis: Long = 0
        try {
            val date = outputPattern.parse(time)
            dateInMillis = date.getTime()
            return dateInMillis.toFloat()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0.0f
    }

}

