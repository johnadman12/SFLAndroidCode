package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.fragment_analystic.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.createTeam.activity.GraphActivity
import stock.com.ui.watch_list.WatchFilterActivity
import stock.com.ui.watch_list.WatchListActivity

import java.text.DecimalFormat
import java.util.ArrayList
class AnalysticFragment : BaseFragment(), OnChartValueSelectedListener,View.OnClickListener {


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.piechart ->{
                startActivity(Intent(context, PieChartGraph::class.java))
            }
            R.id.linechart ->{
                startActivity(Intent(context, LineChartGraph::class.java))
            }
        }

    }

    var mChart: PieChart? = null
    var mChart1: LineChart? = null
    var ll_piechart:LinearLayout?=null
    val mSeekBarX: SeekBar? = null
    val mSeekBarY: SeekBar? = null
    val tvX: TextView? = null
    val tvY: TextView? = null
    
    val mParties = arrayOf("SELL 6%", "HOLD 44%", "BUY 50%")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_analystic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mChart = view!!.findViewById(R.id.piechart) as PieChart
        mChart1 = view!!.findViewById(R.id.linechart) as LineChart

       piechart.setOnClickListener(this)
       linechart.setOnClickListener(this)


        setRatingAdapter()
        getAnalysticsList()
        getLineChartList()
    }

    @SuppressLint("WrongConstant")
    private fun setRatingAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_list_ratings!!.layoutManager = llm
        rv_list_ratings.visibility = View.VISIBLE
        rv_list_ratings!!.adapter = StockRatingAdapter(context!!);
    }

    fun getAnalysticsList() {
        mChart!!.setUsePercentValues(true)
        mChart!!.description.isEnabled = false
        mChart!!.setExtraOffsets(0f, 5f, 30f, 5f)
        mChart!!.dragDecelerationFrictionCoef = 0.95f
        //mChart.setCenterText(generateCenterSpannableText());
        mChart!!.isDrawHoleEnabled = true
        mChart!!.setHoleColor(Color.WHITE)
        mChart!!.setTransparentCircleColor(Color.WHITE)
        mChart!!.holeRadius = 50f
        mChart!!.transparentCircleRadius = 50f
        mChart!!.setDrawCenterText(true)
        mChart!!.rotationAngle = 0f
        mChart!!.setTouchEnabled(true)
       // mChart!!.isHighlightPerTapEnabled = true
        mChart!!.setOnChartValueSelectedListener(this)
        mChart!!.setTouchEnabled(true)
        setData1(3, 100f)
        val l = mChart!!.legend
        l.xEntrySpace = 1f
        l.yEntrySpace = 15f
        l.textSize = 12f
        l.position = Legend.LegendPosition.RIGHT_OF_CHART_CENTER
        l.formSize = 15f
        l.form = Legend.LegendForm.SQUARE
        l.yOffset = 5f
       // l.isEnabled = true
        // entry label styling
        mChart!!.setEntryLabelColor(Color.WHITE)
        mChart!!.setEntryLabelTextSize(0f)

    }

    fun getLineChartList(){

        mChart1!!.setOnChartGestureListener(this)
        mChart1!!.setOnChartValueSelectedListener(this)
        mChart1!!.setDrawGridBackground(false)
        setData()

        val l = mChart1!!.getLegend()
        l.form = Legend.LegendForm.LINE

        mChart1!!.setTouchEnabled(true)
        mChart1!!.setDrawBorders(true)
        // enable scaling and dragging
        mChart1!!.setDragEnabled(true)
        mChart1!!.setScaleEnabled(true)



        val upper_limit = LimitLine(100f, "Upper Limit")
        upper_limit.setLineWidth(4f)
        upper_limit.enableDashedLine(5f, 5f, 0f)
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP)
        upper_limit.setTextSize(10f)


        val lower_limit = LimitLine(0f, "Lower Limit")
        lower_limit.lineWidth = 4f
        lower_limit.enableDashedLine(10f, 10f, 0f)
        lower_limit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        lower_limit.textSize = 10f

        val leftAxis = mChart1!!.getAxisLeft()
        leftAxis.removeAllLimitLines() // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit)
        leftAxis.addLimitLine(lower_limit)
        leftAxis.setAxisMaxValue(100f)
        leftAxis.setAxisMinValue(-0f)
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f)
        leftAxis.setDrawZeroLine(false)

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true)
        mChart1!!.getAxisRight().setEnabled(true)
        mChart1!!.getAxisLeft().setEnabled(false)
        /* YAxis rightYAxis = mChart.getAxisRight();
        rightYAxis.setEnabled(false);*/

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);
        mChart1!!.getXAxis().position = XAxis.XAxisPosition.BOTTOM
        mChart!!.setTouchEnabled(false)

        mChart1!!.animateX(2000, Easing.EasingOption.EaseInOutQuart)
        //  dont forget to refresh the drawing
        mChart1!!.invalidate()

    }
    // fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}

    private fun setData1(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        for (i in 0 until count) {
            entries.add(
                PieEntry((Math.random() * range.toFloat() + range.toFloat() / 3).toFloat(), mParties[i % mParties.size],
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.sliceSpace = 0f
        dataSet.iconsOffset = MPPointF(0f, 60f)
        dataSet.selectionShift = 3f

        // add a lot of colors
        val colors = ArrayList<Int>()
        val redcolor = resources.getColor(R.color.colorRed)
        val yellowcolor = resources.getColor(R.color.yellowcolor)
        val lightGreenwcolor = resources.getColor(R.color.green_candle)

        colors.add(redcolor)
        colors.add(yellowcolor)
        colors.add(lightGreenwcolor)
        dataSet.colors = colors


        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.WHITE)

        mChart!!.setData(data)
        // undo all highlights
        mChart!!.highlightValues(null)
        mChart!!.invalidate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight) {
        if (e == null)
            return
        Log.i("VAL_SELECTED", "Value: " + e.y + ", index: " + h.x + ", DataSet index: " + h.dataSetIndex)
    }

    override fun onNothingSelected() {
        Log.i("PieChart", "nothing selected")
    }


    fun onStartTrackingTouch(seekBar: SeekBar) {
        // TODO Auto-generated method stub
    }

    fun onStopTrackingTouch(seekBar: SeekBar) {}

    fun setXAxisValues(): ArrayList<String> {
        val xVals = ArrayList<String>()
        xVals.add("10")
        xVals.add("20")
        xVals.add("30")
        xVals.add("40")
        xVals.add("50")
        xVals.add("60")
        xVals.add("70")
        xVals.add("80")

        return xVals
    }

    fun setYAxisValues(): ArrayList<Entry> {
        val yVals = ArrayList<Entry>()
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

    fun setData() {
        val xVals = setXAxisValues()
        val yVals = setYAxisValues()
        val set1: LineDataSet
        // create a dataset and give it a type
        set1 = LineDataSet(yVals, "DataSet 1")
        set1.setFillAlpha(100)
        // set1.setFillColor(Color.RED);
        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK)
        set1.setCircleColor(Color.BLACK)
        set1.setLineWidth(0.5f)
        set1.setCircleRadius(3f)
        set1.setDrawCircleHole(false)
        set1.setValueTextSize(10f)
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the datasets

        // create a data object with the datasets
        val data = LineData(dataSets)
        // set data
        mChart1!!.setData(data)
    }

}



private fun LineChart.setOnChartGestureListener(analysticFragment: AnalysticFragment) {


}

    /* private fun LineDataProvider.setOnChartGestureListener(analysticFragment: AnalysticFragment) {
         var mChart1: LineChart? = null

      fun onValueSelected(e: Entry, h: Highlight) {
             Log.i("Entry selected", e.toString())
             Log.i(
                 "LOWHIGH", "low: " + mChart1!!.getLowestVisibleX()
                         + ", high: " + mChart1.getHighestVisibleX()
             )

             Log.i(
                 "MIN MAX", "xmin: " + mChart1.getXChartMin()
                         + ", xmax: " + mChart1.getXChartMax()
                         + ", ymin: " + mChart1.getYChartMin()
                         + ", ymax: " + mChart1.getYChartMax()
             )
         }

         fun onNothingSelected() {
             Log.i("Nothing selected", "Nothing selected.")
         }


     }*/









