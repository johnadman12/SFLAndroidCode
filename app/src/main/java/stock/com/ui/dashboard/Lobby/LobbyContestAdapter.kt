package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.ui.pojo.PriceBreak
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LobbyContestAdapter(
    val mContext: Context,
    val mContest: List<LobbyContestPojo.Contest>
) :
    RecyclerView.Adapter<LobbyContestAdapter.FeatureListHolder>() {
    var SECONDS_IN_A_DAY = 24 * 60 * 60

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_featured_contest, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.entry_fee.setText(mContest.get(position).entryFees)
        holder.itemView.tvContestType.setText(mContest.get(position).catname)
        holder.itemView.tvWinnersTotal.setText(mContest.get(position).totalWinners)
        holder.itemView.tvTotalWinnings.setText(mContest.get(position).winningAmount)
        if (mContest.get(position).marketname.equals("Equity")) {
            Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + mContest.get(position).exchangeimage.trim())
                .into(holder.itemView.ivStock)
            holder.itemView.tvStockName.setText(mContest.get(position).exchangename)
        } else {
            holder.itemView.tvStockName.setText(mContest.get(position).marketname)
            Glide.with(mContext).load(R.drawable.ic_business)
                .into(holder.itemView.ivStock)
        }


        var sports: Double =
            (mContest.get(position).contestSize.toInt() - mContest.get(position).teamsJoined.toInt()).toDouble()


        holder.itemView.tvSprortsLeft.setText(
            mContest.get(position).contest_teamremaining.toString() + "/" +
                    mContest.get(position).contestSize
        )

        if (TextUtils.isEmpty(mContest.get(position).confirm_winning))
            holder.itemView.tvConfirmWin.visibility = View.GONE
        else
            holder.itemView.tvConfirmWin.visibility = View.VISIBLE

        if (TextUtils.isEmpty(mContest.get(position).join_multiple))
            holder.itemView.tvMulJoin.visibility = View.GONE
        else
            holder.itemView.tvMulJoin.visibility = View.VISIBLE



        holder.itemView.tvTime.setText(parseDateToddMMyyyy(mContest.get(position).scheduleStart))
        if (!mContest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(mContest.get(position).scheduleStart)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            mContest.get(position).setDate(diff.toInt())
            if (diff.toString().contains("-")) {
                holder.itemView.txtjoin.setTextSize(16.00f)
                holder.itemView.txtjoin.setText(mContext.getString(R.string.live_now))
                holder.itemView.llSportsLeft.visibility = View.INVISIBLE
                holder.itemView.circular_progress.progressBackgroundColor =
                    ContextCompat.getColor(mContext, R.color.GrayColor)
                holder.itemView.ll_Circular.isEnabled = false
                holder.itemView.llbgTime.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.circle))
                holder.itemView.tvTimeLeft.setText("Contest \n Started")
                holder.itemView.tvHint.visibility = View.GONE
            } else if (diff.equals("3600000")) {
                val newtimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        val diffSec = diff / 1000
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        holder.itemView.tvTime.setText(hours.toString() + "H: \n " + minutes.toString() + "M: ")
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
                            holder.itemView.txtjoin.setTextSize(16.00f)
                            holder.itemView.txtjoin.setText("Starts \n Soon")
                            holder.itemView.circular_progress.progressBackgroundColor =
                                ContextCompat.getColor(mContext, R.color.GrayColor)
                            holder.itemView.ll_Circular.isEnabled = false
                            holder.itemView.isEnabled = false
                            holder.itemView.llSportsLeft.visibility = View.INVISIBLE
//                            llbgTime.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle))
                            val diffSec = diff / 1000
                            val seconds = diffSec % 60
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600
                            holder.itemView.tvTimeLeft.setText(hours.toString() + "H: \n" + minutes.toString() + "M: \n" + seconds.toString() + "S")
                        } else {
                            val diffSec = diff / 1000
                            val seconds = diffSec % 60
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600

                            holder.itemView.tvTimeLeft.setText(hours.toString() + "H: \n" + minutes.toString() + "M: \n" + seconds.toString() + "S")
                        }

                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }

        holder.itemView.iv_info.setOnClickListener {
            AppDelegate.showInfoDialogue(mContest.get(position).description, mContext);
        }

        val contestLeft: Double =
            mContest.get(position).contestSize.toDouble() - mContest.get(position).contest_teamremaining.toDouble()
        holder.itemView.circular_progress.isAnimationEnabled
        holder.itemView.circular_progress.setProgress(
            contestLeft,
            mContest.get(position).contestSize.toDouble()
        )

        holder.itemView.circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)

        holder.itemView.llWinners.setOnClickListener {
            val manager = (mContext as AppCompatActivity).supportFragmentManager
            val bottomSheetDialogFragment = BottomSheetWinningListFragment(
                mContest.get(position).priceBreak as ArrayList<PriceBreak>,
                mContest.get(position).winningAmount
            )
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }
        holder.itemView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ContestDetailActivity::class.java).putExtra(
                    StockConstant.CONTESTID,
                    mContest.get(position).contestid.toInt()
                ).putExtra(
                    StockConstant.EXCHANGEID,
                    mContest.get(position).exchangeid.toInt()
                )
            )
        }
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = ""
            sb
        }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM HH:mm:ss"
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


}

