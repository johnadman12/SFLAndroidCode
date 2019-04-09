package stock.com.ui.dashboard.home.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.viewpager.widget.PagerAdapter
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_information.*
import stock.com.R
import stock.com.ui.pojo.TrainingPojo
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerTraining(val context: Context, val list: List<TrainingPojo.TraniningContest>) : PagerAdapter() {
    var SECONDS_IN_A_DAY = 24 * 60 * 60

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun getCount(): Int {

        return list.size;
        // return 5;
    }

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        // Get the view from pager page layout
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_featured_contest, parent, false)

        val entry_fee: TextView = view.findViewById(R.id.entry_fee)
        val tvStockName: TextView = view.findViewById(R.id.tvStockName)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTotalWinnings: TextView = view.findViewById(R.id.tvTotalWinnings)
        val tvSprortsLeft: TextView = view.findViewById(R.id.tvSprortsLeft)
        val tvTimeLeft: TextView = view.findViewById(R.id.tvTimeLeft)
        val tvWinnersTotal: TextView = view.findViewById(R.id.tvWinnersTotal)
        val iv_info: AppCompatImageButton = view.findViewById(R.id.iv_info)

        val ivStock: AppCompatImageButton = view.findViewById(R.id.ivStock)
        val circular_progress: CircularProgressIndicator = view.findViewById(R.id.circular_progress)


        entry_fee.setText(list.get(position).entryFees)
        tvStockName.setText(list.get(position).exchangename)
        tvTime.setText(list.get(position).exchangename)
        tvWinnersTotal.setText(list.get(position).totalwinners)
        tvTotalWinnings.setText(list.get(position).winningAmount)

        Glide.with(context).load(AppDelegate.EXCHANGE_URL + list.get(position).exchangeimage.trim())
            .into(ivStock)


        var sports: Double =
            (list.get(position).contestSize.toInt() - list.get(position).teamsJoined.toInt()).toDouble()
        tvSprortsLeft.setText(
            sports.toString() + "/" +
                    list.get(position).contestSize
        )

        tvTime.setText(parseDateToddMMyyyy(list.get(position).scheduleStart))
        if (!list.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(list.get(position).scheduleStart)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            if (diff.toString().contains("-")) {
                tvTimeLeft.setText("0:0:0: Left")
            } else {
                val diffSec = diff / 1000
                val days = diffSec / SECONDS_IN_A_DAY
                val secondsDay = diffSec % SECONDS_IN_A_DAY
                val seconds = secondsDay % 60
                val minutes = secondsDay / 60 % 60
                val hours = secondsDay / 3600
                tvTimeLeft.setText(days.toString() + " : " + hours.toString() + " : " + minutes.toString() + " : " + seconds.toString())
            }
        }
        circular_progress.isAnimationEnabled
        circular_progress.setProgress(
            list.get(position).teamsJoined.toDouble(),
            list.get(position).contestSize.toDouble()
        )
//        holder.itemView.circular_progress.setMaxProgress(10000.0);
        circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)


        val llWinners: LinearLayout = view.findViewById(R.id.llWinners)
        llWinners.setOnClickListener {
            val manager = (context as AppCompatActivity).supportFragmentManager
            val bottomSheetDialogFragment = BottomSheetWinningListFragment(list.get(position).priceBreak, list.get(position).winningAmount)
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }
        iv_info.setOnClickListener {
            showInfoDialogue(list.get(position).description);
        }


        // Add the view to the parent
        parent?.addView(view)

        // Return the view
        return view
    }

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        // Remove the view from view group specified position
        parent.removeView(`object` as View)
    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = "Join" +
                    " Now"
            sb
        }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    fun showInfoDialogue(textView: String) {
        var dialogue = Dialog(context)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_information)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.tvInfo.setText(textView)
        dialogue.btnOK.setOnClickListener {
            if (dialogue.isShowing)
                dialogue.dismiss()
        }
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }


}