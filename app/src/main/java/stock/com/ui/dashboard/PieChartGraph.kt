package stock.com.ui.dashboard.Team.Stock
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.activity_pie_chart_graph.*
import stock.com.R
class PieChartGraph() : AppCompatActivity(), SeekBar.OnSeekBarChangeListener, OnChartValueSelectedListener, Parcelable,
    View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.back_arrow ->
                onBackPressed()

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private var mChart: PieChart? = null
    private val mParties = arrayOf("BUY 30%", "SELL 30%", "HOLD 40%")
    constructor(parcel: Parcel) : this() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pie_chart_graph)

        back_arrow.setOnClickListener(this)

        mChart = findViewById(R.id.piechart) as PieChart
        mChart!!.setUsePercentValues(false)
        mChart!!.getDescription().setEnabled(false)
        mChart!!.setExtraOffsets(0f, 10f, 0f, 5f)
        mChart!!.setDragDecelerationFrictionCoef(0.95f)
        mChart!!.setDrawHoleEnabled(true)
        mChart!!.setHoleColor(Color.WHITE)
        mChart!!.setTransparentCircleColor(Color.WHITE)
        mChart!!.setHoleRadius(50f)
        mChart!!.setDrawEntryLabels(false)
        mChart!!.setTransparentCircleRadius(50f)
        mChart!!.setDrawCenterText(false)
        mChart!!.setRotationAngle(0f)
        mChart!!.setHighlightPerTapEnabled(false)
        mChart!!.setOnChartValueSelectedListener(this)


        setData(3, 100f)
        val l = mChart!!.getLegend()
        l.setDrawInside(false)

        val l1 = LegendEntry("Male", Legend.LegendForm.SQUARE, 15f, 40f, null, Color.YELLOW)
        l.setXEntrySpace(8f)
        l.setYEntrySpace(8f)
        l.setTextSize(12f)
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER)
        l.setFormSize(15f)
        l.setForm(Legend.LegendForm.SQUARE)
        l.setYOffset(5f)
        l.setEnabled(true)
        // entry label styling
        mChart!!.setEntryLabelColor(Color.WHITE)
        mChart!!.setEntryLabelTextSize(5f)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
    }

    private fun setData(count: Int, range: Float) {
        val entries = ArrayList<PieEntry>()
        for (i in 0 until count) {
            entries.add(
                PieEntry(
                    (Math.random() * range + range / 3).toFloat(), mParties[i % mParties.size],
                    resources.getDrawable(R.drawable.ic_launcher_background)
                )
            )
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)
        dataSet.setSliceSpace(0f)
        dataSet.setIconsOffset(MPPointF(0f, 0f))
        dataSet.setSelectionShift(5f)
        // add a lot of colors
        val colors = ArrayList<Int>()

        val redcolor = resources.getColor(R.color.redcolor)
        val yellowcolor = resources.getColor(R.color.yellowcolor)
        val lightGreenwcolor = resources.getColor(R.color.lightGreencolor)

        colors.add(redcolor)
        colors.add(yellowcolor)
        colors.add(lightGreenwcolor)

        dataSet.setColors(colors)
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
        Log.i(
            "VAL SELECTED",
            "Value: " + e!!.getY() + ", index: " + h.getX() + ", DataSet index: " + h.getDataSetIndex()
        )
    }

    override fun onNothingSelected() {
    Log.i("PieChart", "nothing selected")
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
    // TODO Auto-generated method stub
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {
     // TODO Auto-generated method stub
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PieChartGraph> {
        override fun createFromParcel(parcel: Parcel): PieChartGraph {
        return PieChartGraph(parcel)
        }

        override fun newArray(size: Int): Array<PieChartGraph?> {
        return arrayOfNulls(size)
        }
    }

}

