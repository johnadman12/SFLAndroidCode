package stock.com.ui.createTeam.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_graph.*
import stock.com.AppBase.BaseActivity
import stock.com.R


class GraphActivity : BaseActivity(), View.OnClickListener {

    val xVals_earnings = ArrayList<String>()
    val total_earnings = ArrayList<Entry>()
    val total_earnings_candle = ArrayList<CandleEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        line_graph!!.setOnClickListener(this)
        candle_stick_chart!!.setOnClickListener(this)
        back_arrow.setOnClickListener(this)
        for(i in 0 until 10) {
            xVals_earnings.add(i.toString())
            val total_sale_value = i.toDouble()
            val v1e1 = Entry(total_sale_value.toFloat(), i)
            val v1e2 = CandleEntry(0,total_sale_value.toFloat(), i.toFloat(), 0.0f, 0.0f)
            total_earnings.add(v1e1)
            total_earnings_candle.add(v1e2)
        }

        // candle graph

        for(j in 0 until 10) {
            xVals_earnings.add(j.toString())
            val total_sale_value = j.toDouble()
            val v1e2 = CandleEntry(0,total_sale_value.toFloat(), j.toFloat(), 0.0f, 0.0f)
            total_earnings_candle.add(v1e2)
        }
        // Line Graph
        val barDataSet = LineDataSet(total_earnings, "")
        val data = LineData(xVals_earnings, barDataSet)
        chart.data = data
        chart.animateXY(500, 500)
        chart.legend.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDoubleTapToZoomEnabled = false
        chart.setMaxVisibleValueCount(0)
        chart.setDescription("")
        barDataSet.color = ContextCompat.getColor(this, R.color.colorPrimary)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.axisLeft.setDrawGridLines(false)
        xAxis.textColor = ContextCompat.getColor(this, R.color.black)
        val yAxis = chart.axisLeft
        yAxis.setAxisMinValue(0f)
        yAxis.textColor = ContextCompat.getColor(this, R.color.black)
        val rightAxis = chart.axisRight
        rightAxis.setDrawLabels(false)

        //Candle stick graph
        val barDataSetCandle = CandleDataSet(total_earnings_candle, "")
        val data_candle = CandleData(xVals_earnings, barDataSetCandle)
        candle_chart.data = data_candle
        candle_chart.animateXY(500, 500)
        candle_chart.legend.isEnabled = false
        candle_chart.setTouchEnabled(true)
        candle_chart.isDoubleTapToZoomEnabled = false
        candle_chart.setMaxVisibleValueCount(0)
        candle_chart.setDescription("")
        barDataSet.color = ContextCompat.getColor(this, R.color.colorPrimary)
        val xAxis_candle = candle_chart.xAxis
        xAxis_candle.position = XAxis.XAxisPosition.BOTTOM
        chart.axisLeft.setDrawGridLines(false)
        xAxis_candle.textColor = ContextCompat.getColor(this, R.color.black)
        val yAxis_candle = candle_chart.axisLeft
        yAxis_candle.setAxisMinValue(0f)
        yAxis_candle.textColor = ContextCompat.getColor(this, R.color.black)
        val rightAxis_candle = candle_chart.axisRight
        rightAxis_candle.setDrawLabels(false)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_arrow -> onBackPressed()

            R.id.line_graph -> {
                chart.visibility = View.VISIBLE
                candle_chart.visibility = View.GONE
            }

            R.id.candle_stick_chart -> {
                chart.visibility = View.GONE
                candle_chart.visibility = View.VISIBLE
            }
        }
    }
}
