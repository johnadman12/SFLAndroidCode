package stock.com.ui.dashboard.Team.Stock

import android.graphics.Color
import android.graphics.Paint
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
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleEntry


class ChartFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val yValsCandleStick = ArrayList<CandleEntry>()
        yValsCandleStick.add(CandleEntry(0.0F, 225.0f, 219.84f, 224.94f, 221.07f))
        yValsCandleStick.add(CandleEntry(1F, 228.35f, 222.57f, 223.52f, 226.41f))
        yValsCandleStick.add(CandleEntry(2F, 226.84f, 222.52f, 225.75f, 287.84f))
        yValsCandleStick.add(CandleEntry(3F, 230.95f, 217.27f, 226.15f, 267.88f))
        yValsCandleStick.add(CandleEntry(4F, 229.56f, 217.28f, 227.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(5F, 233.90f, 218.29f, 227.15f, 235.88f))
        yValsCandleStick.add(CandleEntry(6F, 220.92f, 219.30f, 228.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(7F, 221.94f, 220.47f, 229.15f, 266.88f))
        yValsCandleStick.add(CandleEntry(8F, 240.45f, 217.42f, 230.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(9F, 223.67f, 233.54f, 231.15f, 2232.88f))
        yValsCandleStick.add(CandleEntry(10F, 242.90f, 220.47f, 232.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(11F, 245.68f, 217.237f, 222.15f, 213.88f))
        yValsCandleStick.add(CandleEntry(12F, 220.57f, 224.67f, 222.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(13F, 250.22f, 243.87f, 222.15f, 266.88f))
        yValsCandleStick.add(CandleEntry(14F, 247.95f, 217.87f, 224.15f, 287.88f))
        yValsCandleStick.add(CandleEntry(15F, 248.49f, 243.45f, 222.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(16F, 219.50f, 224.43f, 255.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(17F, 226.60f, 225.29f, 222.15f, 220.88f))
        yValsCandleStick.add(CandleEntry(18F, 222.51f, 216.28f, 246.15f, 289.88f))
        yValsCandleStick.add(CandleEntry(19F, 220.42f, 218.69f, 222.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(20F, 240.55f, 239.59f, 258.15f, 217.88f))
        yValsCandleStick.add(CandleEntry(21F, 232.93f, 240.66f, 222.15f, 240.88f))
        yValsCandleStick.add(CandleEntry(22F, 235.44f, 245.25f, 250.15f, 217.88f))


        val set1 = CandleDataSet(yValsCandleStick, "")
//        val data_candle = CandleData(xVals_earnings, barDataSetCandle)
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(R.color.colorASPrimary));
        set1.setShadowWidth(0.9f);
        set1.setDecreasingColor(getResources().getColor(R.color.red_candle));
        set1.setDecreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.setIncreasingColor(getResources().getColor(R.color.green_candle));
        set1.setIncreasingPaintStyle(Paint.Style.FILL_AND_STROKE);
        set1.setNeutralColor(Color.LTGRAY);
        set1.setDrawValues(false);
        val data = CandleData(set1)


// set data
        chart11.setData(data)
        chart11.invalidate()
        chart11.animateXY(500, 500)
        chart11.legend.isEnabled = false
        chart11.setTouchEnabled(true)
        chart11.isDoubleTapToZoomEnabled = false
        chart11.setMaxVisibleValueCount(0)
//        barDataSet.color = ContextCompat.getColor(activity!!, R.color.colorPrimary)
        val xAxis_candle = chart11.xAxis
        xAxis_candle.position = XAxis.XAxisPosition.BOTTOM
        chart11.axisLeft.setDrawGridLines(false)
        xAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)
        val yAxis_candle = chart11.axisLeft
        yAxis_candle.textColor = ContextCompat.getColor(activity!!, R.color.black)
        val rightAxis_candle = chart11.axisRight
        rightAxis_candle.setDrawLabels(false)

    }

}