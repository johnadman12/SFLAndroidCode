package stock.com.ui.dashboard.Team.Stock

import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import kotlinx.android.synthetic.main.activity_stock_chart.*
import stock.com.R
import stock.com.ui.pojo.CandlesticChartmarket
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class StockChartActivity : AppCompatActivity(), View.OnClickListener {
    var data: CandlesticChartmarket.Data? = null
    var list: ArrayList<CandlesticChartmarket.Data.Quote>? = null
    val yValsCandleStick = ArrayList<CandleEntry>()
    var url: String = ""
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.back_arrow ->
                onBackPressed()
        }
    }

    /*  override fun onBackPressed() {
          super.onBackPressed()
          finish()
      }
  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_chart)
        back_arrow.setOnClickListener(this)
        if (intent != null)
//            data = intent.getSerializableExtra(StockConstant.CHART) as CandlesticChartmarket.Data?
            url = intent!!.getStringExtra(StockConstant.CHART)
        if (url != null) {
            /* list = data!!.quotes
             initView()*/
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.setBackgroundColor(0x00000000);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.loadUrl(url)
        }
    }

    private fun initView() {
        for (i in 0 until list!!.size) {
            val usd = list!!.get(i).quote!!.uSD
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

        val set1 = CandleDataSet(yValsCandleStick, "")
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.colorASPrimary));
        set1.setShadowWidth(2.9f);

        set1.setDecreasingColor(getResources().getColor(R.color.red_candle));
        set1.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.setIncreasingColor(getResources().getColor(R.color.green_candle));
        set1.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.setNeutralColor(Color.LTGRAY);

        set1.setDrawValues(false);
        val data = CandleData(set1)
        // set data
        chart11.setData(data)
        chart11.zoomToCenter(28f, 0f)
        chart11.invalidate()
        chart11.animateXY(500, 500)
        chart11.getDescription().setEnabled(false);
        chart11.legend.isEnabled = false
        chart11.setTouchEnabled(true)
        chart11.isDoubleTapToZoomEnabled = false
        chart11.setMaxVisibleValueCount(0)
        chart11.setBorderColor(Color.GREEN)
        val xAxis_candle = chart11.xAxis
        xAxis_candle.setValueFormatter { value, _ ->
            SimpleDateFormat("yyyy/MM/dd", Locale.US).format(value.toLong())
        }
        xAxis_candle.position = XAxis.XAxisPosition.BOTTOM
        chart11.axisLeft.setDrawGridLines(false)
        xAxis_candle.textColor = ContextCompat.getColor(this, R.color.black)
        val yAxis_candle = chart11.axisLeft
        yAxis_candle.textColor = ContextCompat.getColor(this, R.color.black)
        val rightAxis_candle = chart11.axisRight
        rightAxis_candle.setDrawLabels(false)

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



