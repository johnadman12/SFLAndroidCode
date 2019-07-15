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
import android.view.View.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
import kotlinx.android.synthetic.main.dialogue_join_contest.*
import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.pojo.HomePojo
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ViewPagerFeature(
    val context: Context,
    val list: List<HomePojo.FeatureContest>,
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
        val card_view: CardView = view.findViewById(R.id.card_view)
        val tvStockName: TextView = view.findViewById(R.id.tvStockName)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvTotalWinnings: TextView = view.findViewById(R.id.tvTotalWinnings)
        val tvSprortsLeft: TextView = view.findViewById(R.id.tvSprortsLeft)
        val tvTimeLeft: TextView = view.findViewById(R.id.tvTimeLeft)
        val iv_info: AppCompatImageButton = view.findViewById(R.id.iv_info)
        val tvWinnersTotal: TextView = view.findViewById(R.id.tvWinnersTotal)
        val ivStock: AppCompatImageButton = view.findViewById(R.id.ivStock)
        val circular_progress: CircularProgressIndicator = view.findViewById(R.id.circular_progress)
        val llWinners: LinearLayout = view.findViewById(R.id.llWinners)
        val ll_Circular: RelativeLayout = view.findViewById(R.id.ll_Circular)
        val txtjoin: TextView = view.findViewById(R.id.txtjoin)
        val tvConfirmWin: TextView = view.findViewById(R.id.tvConfirmWin)
        val tvMulJoin: TextView = view.findViewById(R.id.tvMulJoin)
        val tvContestType: TextView = view.findViewById(R.id.tvContestType)
        val llbgTime: LinearLayout = view.findViewById(R.id.llbgTime)
        val llSportsLeft: LinearLayout = view.findViewById(R.id.llSportsLeft)
        val tvHint: TextView = view.findViewById(R.id.tvHint)

        entry_fee.setText(list.get(position).entryFees)
        tvWinnersTotal.setText(list.get(position).totalwinners)
        tvTotalWinnings.setText(list.get(position).winningAmount)
        tvContestType.setText(list.get(position).catname)

        if (TextUtils.isEmpty(list.get(position).confirm_winning))
            tvConfirmWin.visibility = GONE
        else
            tvConfirmWin.visibility = VISIBLE

        if (TextUtils.isEmpty(list.get(position).join_multiple))
            tvMulJoin.visibility = GONE
        else
            tvMulJoin.visibility = VISIBLE


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
                circular_progress.progressBackgroundColor =
                    ContextCompat.getColor(context, R.color.GrayColor)
                ll_Circular.isEnabled = false
                llbgTime.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle))
                tvTimeLeft.setText("Contest \n Started")
                tvHint.visibility = GONE
            } else if (diff.equals("3600000")) {
                val newtimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        val diffSec = diff / 1000
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        tvTimeLeft.setText(hours.toString() + "H: \n " + minutes.toString() + "M: ")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()

            } else {
                val newtimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        if (diff < 900000) {
                            txtjoin.setTextSize(16.00f)
                            txtjoin.setText("Starts \n Soon")
                            circular_progress.progressBackgroundColor =
                                ContextCompat.getColor(context, R.color.GrayColor)
                            ll_Circular.isEnabled = false
                            llSportsLeft.visibility = INVISIBLE
//                            llbgTime.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle))
                            val diffSec = diff / 1000
                            val seconds = diffSec % 60
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600
                            tvTimeLeft.setText(hours.toString() + "H: \n" + minutes.toString() + "M: \n" + seconds.toString() + "S")
                        } else if (diff.toString().contains("-")) {
                            circular_progress.progressBackgroundColor =
                                ContextCompat.getColor(context, R.color.GrayColor)
                            ll_Circular.isEnabled = false
                            llbgTime.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle))
                            tvTimeLeft.setText("Contest \n Started")
                            tvHint.visibility = GONE
                        } else {
                            val diffSec = diff / 1000
                            val seconds = diffSec % 60
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600
                            tvTimeLeft.setText(hours.toString() + "H: \n" + minutes.toString() + "M: \n" + seconds.toString() + "S")
                        }
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }



        iv_info.setOnClickListener {
            AppDelegate.showInfoDialogue(list.get(position).description, context);
        }
        ll_Circular.setOnClickListener {
            if (userid.equals("")) {
                AppDelegate.showAlertRegister(
                    context, context.getResources().getString(R.string.app_name),
                    context.getString(R.string.login_default)
                )
            } else if (list.get(position).marketname.equals("Equity")) {
                var intent = Intent(context, ContestDetailActivity::class.java);
                intent.putExtra(StockConstant.CONTESTID, list.get(position).contestid)
                intent.putExtra(StockConstant.EXCHANGEID, list.get(position).exchangeid)
                startActivityForResult(context as Activity, intent, 404, null);
            } else {
                var intent = Intent(context, ContestDetailActivity::class.java);
                intent.putExtra(StockConstant.CONTESTID, list.get(position).contestid)
                intent.putExtra(StockConstant.EXCHANGEID, list.get(position).exchangeid)
                startActivityForResult(context as Activity, intent, StockConstant.REDIRECT_UPCOMING_MARKET, null);
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

        llWinners.setOnClickListener {
            val manager = (context as AppCompatActivity).supportFragmentManager
            val bottomSheetDialogFragment =
                BottomSheetWinningListFragment(list.get(position).priceBreak, list.get(position).winningAmount)
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }

        // Add the view to the parent
        parent.addView(view)

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
        val outputPattern = "dd MMM HH:mm:ss "
//        val outputPattern = "dd MMM h:mm a"
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


    fun timeString(millisUntilFinished: Long): String {
        var millisUntilFinished: Long = millisUntilFinished
        val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

        val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
        millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

        val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
        millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

        // Format the string
        return String.format(
            Locale.getDefault(),
            "%02d day: %02d hour: %02d min: %02d sec",
            days, hours, minutes, seconds
        )
    }


}