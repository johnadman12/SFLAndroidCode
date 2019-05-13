package stock.com.ui.dashboard.Team.Stock

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.activity_line_chart_graph.*
import stock.com.AppBase.BaseActivity
import stock.com.R


class LineChartGraph : BaseActivity(), View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {
    var mChart: LineChart? = null
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_arrow -> onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_chart_graph)

        mChart = findViewById(R.id.linechart) as LineChart
        mChart!!.setOnChartGestureListener(this)
        mChart!!.setOnChartValueSelectedListener(this)
        mChart!!.setDrawGridBackground(false)
        //back_arrow.setOnClickListener(this)
        back_arrow.setOnClickListener(this)
        setData()

        val l = mChart!!.getLegend()
        l.form = Legend.LegendForm.LINE
        mChart!!.setTouchEnabled(true)
        mChart!!.setDrawBorders(true)

        mChart!!.setDragEnabled(true)
        mChart!!.setScaleEnabled(true)

        val upper_limit = LimitLine(100f, "Upper Limit")
        upper_limit.setLineWidth(4f)
        upper_limit.enableDashedLine(5f, 5f, 0f)
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP)
        upper_limit.setTextSize(10f)

        val lower_limit = LimitLine(0f, "Lower Limit")
        lower_limit.setLineWidth(4f)
        lower_limit.enableDashedLine(10f, 10f, 0f)
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM)
        lower_limit.setTextSize(10f)

        val leftAxis = mChart!!.getAxisLeft()
        leftAxis.removeAllLimitLines()
        leftAxis.addLimitLine(upper_limit)
        leftAxis.addLimitLine(lower_limit)
        leftAxis.setAxisMaxValue(100f)
        leftAxis.setAxisMinValue(-0f)

        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)
        leftAxis.setDrawLimitLinesBehindData(true)

        mChart!!.getAxisRight().isEnabled = true
        mChart!!.getAxisLeft().isEnabled = false

        val rightYAxis = mChart!!.getAxisRight()
        rightYAxis.isEnabled = false
        mChart!!.getXAxis().position = XAxis.XAxisPosition.BOTTOM
        mChart!!.animateX(2000, Easing.EasingOption.EaseInOutQuart)

        //  dont forget to refresh the drawing
        mChart!!.invalidate()
    }

    private fun setData() {
        val xVals = setXAxisValues()
        val yVals = setYAxisValues()
        val set1: LineDataSet

        // create a dataset and give it a type
        set1 = LineDataSet(yVals, "DataSet 1")
        set1.setFillAlpha(100)

        set1.setColor(Color.BLACK)
        set1.setCircleColor(Color.LTGRAY)
        set1.setLineWidth(0.5f)
        set1.setCircleRadius(1f)
        set1.setDrawCircleHole(false)
        set1.setValueTextSize(10f)
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the datasets

        // create a data object with the datasets
        val data = LineData(dataSets)
        // set data
        mChart!!.setData(data)
    }
    private fun setXAxisValues(): java.util.ArrayList<String> {
        val xVals = java.util.ArrayList<String>()
        xVals.add("$10")
        xVals.add("$20")
        xVals.add("$30")
        xVals.add("$40")
        xVals.add("$50")
        xVals.add("$60")
        xVals.add("$70")
        xVals.add("$80")

        return xVals
    }

    private fun setYAxisValues(): java.util.ArrayList<Entry> {
        val yVals = java.util.ArrayList<Entry>()
        yVals.add(Entry(50f, 0f))
        yVals.add(Entry(80f, 10f))
        yVals.add(Entry(90f, 20f))
        yVals.add(Entry(68f, 30f))
        yVals.add(Entry(90f, 40f))
        yVals.add(Entry(100.9f, 50f))
        yVals.add(Entry(110f, 60f))
        yVals.add(Entry(130f, 70f))

        return yVals
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.i("Entry selected", e.toString())
        Log.i("LOWHIGH", "low: " + mChart!!.getLowestVisibleX() + ", high: " + mChart!!.getHighestVisibleX())
        Log.i("MIN MAX", "xmin: " + mChart!!.getXChartMin() + ", xmax: " + mChart!!.getXChartMax()
                + ", ymin: " + mChart!!.getYChartMin() + ", ymax: " + mChart!!.getYChartMax())
    }
    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
    }
    override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY)
    }
    override fun onChartSingleTapped(me: MotionEvent?) {
        Log.i("SingleTap", "Chart single-tapped.")
    }
    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
        Log.i("Gesture", "END, lastGesture: $lastPerformedGesture")
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart!!.highlightValues(null)
    }
    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
        Log.i("Scale / Zoom", "ScaleX: $scaleX, ScaleY: $scaleY")
    }
    override fun onChartLongPressed(me: MotionEvent?) {
        Log.i("LongPress", "Chart longpressed.")
    }
    override fun onChartDoubleTapped(me: MotionEvent?) {
        Log.i("DoubleTap", "Chart double-tapped.")
    }
    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
        Log.i("Translate / Move", "dX: $dX, dY: $dY")
    }


}

