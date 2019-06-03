package stock.com.ui.dashboard.home.adapter

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.*
import android.view.View.GONE
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.pojo.TrainingPojo
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerTraining(
    val context: Context,
    val list: List<TrainingPojo.TraniningContest>,
    val userid: String
) : PagerAdapter() {
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
        val ll_Circular: RelativeLayout = view.findViewById(R.id.ll_Circular)
        val card_view: CardView = view.findViewById(R.id.card_view)
        val txtjoin: TextView = view.findViewById(R.id.txtjoin)
        val ivStock: AppCompatImageButton = view.findViewById(R.id.ivStock)
        val circular_progress: CircularProgressIndicator = view.findViewById(R.id.circular_progress)
        val tvConfirmWin: TextView = view.findViewById(R.id.tvConfirmWin)
        val tvMulJoin: TextView = view.findViewById(R.id.tvMulJoin)
        val tvContestType: TextView = view.findViewById(R.id.tvContestType)
        val tvtotal: TextView = view.findViewById(R.id.tvtotal)
        val llWinners: LinearLayout = view.findViewById(R.id.llWinners)

        tvtotal.visibility = GONE
        tvTotalWinnings.visibility = GONE
        entry_fee.setText(list.get(position).entryFees)
        tvTime.setText(list.get(position).exchangename)
        tvWinnersTotal.setText(list.get(position).totalwinners)
        tvTotalWinnings.setText(list.get(position).winningAmount)
        tvContestType.setText(list.get(position).catname)


        if (TextUtils.isEmpty(list.get(position).confirm_winning))
            tvConfirmWin.visibility = View.GONE
        else
            tvConfirmWin.visibility = View.VISIBLE

        if (TextUtils.isEmpty(list.get(position).join_multiple))
            tvMulJoin.visibility = View.GONE
        else
            tvMulJoin.visibility = View.VISIBLE

        if (list.get(position).marketname.equals("Equity")) {
            Glide.with(context).load(AppDelegate.EXCHANGE_URL + list.get(position).exchangeimage.trim())
                .into(ivStock)
            tvStockName.setText(list.get(position).exchangename)
        } else {
            tvStockName.setText(list.get(position).marketname)
            Glide.with(context).load(R.drawable.ic_business)
                .into(ivStock)
        }

        var sports: Double =
            (list.get(position).contestSize.toInt() - list.get(position).teamsJoined.toInt()).toDouble()
        tvSprortsLeft.setText(
            list.get(position).contest_teamremaining.toString() + "/" +
                    list.get(position).contestSize
        )

        tvTime.setText(parseDateToddMMyyyy(list.get(position).scheduleStart))
        if (!list.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(list.get(position).scheduleStart)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            if (diff.toString().contains("-")) {
                tvTimeLeft.setText("00H:00M:00S")
                circular_progress.progressBackgroundColor =
                    ContextCompat.getColor(context, R.color.GrayColor)
            } else if (diff.equals("3600000")) {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        val diffSec = diff / 1000
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        tvTimeLeft.setText(hours.toString() + "H: " + minutes.toString() + "M: ")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()

            } else {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        if (diff < 900000) {
                            txtjoin.setTextSize(22.00f)
                            txtjoin.setText(context.getString(R.string.live_now))
                            circular_progress.progressBackgroundColor =
                                ContextCompat.getColor(context, R.color.GrayColor)
                        }
                        val diffSec = diff / 1000
                        val seconds = diffSec % 60
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600

                        tvTimeLeft.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }
        val contestLeft: Double =
            list.get(position).contestSize.toDouble() - list.get(position).contest_teamremaining.toDouble()
        circular_progress.isAnimationEnabled
        circular_progress.setProgress(
            contestLeft,
            list.get(position).contestSize.toDouble()
        )
//        holder.itemView.circular_progress.setMaxProgress(10000.0);
        circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)


        /* llWinners.setOnClickListener {
             val manager = (context as AppCompatActivity).supportFragmentManager
             val bottomSheetDialogFragment =
                 BottomSheetWinningListFragment(list.get(position).priceBreak, list.get(position).winningAmount)
             bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
         }*/

        ll_Circular.setOnClickListener {
            if (userid.equals("")) {
                AppDelegate.showAlertRegister(
                    context, context.getResources().getString(R.string.app_name),
                    context.getString(R.string.login_default)
                )
            } else {
                var intent = Intent(context, ContestDetailActivity::class.java);
                intent.putExtra(StockConstant.CONTESTID, list.get(position).contestid)
                intent.putExtra(StockConstant.EXCHANGEID, list.get(position).exchangeid)
                ActivityCompat.startActivityForResult(context as Activity, intent, 404, null);
            }
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
            val sb = ""
            sb
        }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var timeZone: String = Calendar.getInstance().getTimeZone().getID();
        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date.time + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()))
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
        dialogue.setCanceledOnTouchOutside(true)
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