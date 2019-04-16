package stock.com.ui.dashboard.Team.Stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.fragment_chart.*
import stock.com.AppBase.BaseFragment
import stock.com.R

class ChartFragment : BaseFragment() {
    val xVals_earnings = ArrayList<String>()
    val total_earnings = ArrayList<Entry>()
    val total_earnings_candle = ArrayList<CandleEntry>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        for(i in 0 until 10) {
            xVals_earnings.add(i.toString())
            val total_sale_value = i.toDouble()
            val v1e1 = Entry(total_sale_value.toFloat(), i)
            val v1e2 = CandleEntry(0,total_sale_value.toFloat(), i.toFloat(), 0.0f, 0.0f)
            total_earnings.add(v1e1)
            total_earnings_candle.add(v1e2)
        }

        // Line Graph
        val barDataSet = LineDataSet(total_earnings, "")
        val barDataSetCandle = CandleDataSet(total_earnings_candle, "")
        val data_candle = CandleData(xVals_earnings, barDataSetCandle)
        chart11.data = data_candle
        chart11.animateXY(500, 500)
        chart11.legend.isEnabled = false
        chart11.setTouchEnabled(true)
        chart11.isDoubleTapToZoomEnabled = false
        chart11.setMaxVisibleValueCount(0)
        chart11.setDescription("")
        barDataSet.color = ContextCompat.getColor(activity!!, R.color.colorPrimary)
        val xAxis_candle = chart11.xAxis
        xAxis_candle.position = XAxis.XAxisPosition.BOTTOM
        chart11.axisLeft.setDrawGridLines(false)
        xAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)
        val yAxis_candle = chart11.axisLeft
        yAxis_candle.setAxisMinValue(0f)
        yAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)
        val rightAxis_candle = chart11.axisRight
        rightAxis_candle.setDrawLabels(false)

    }

}