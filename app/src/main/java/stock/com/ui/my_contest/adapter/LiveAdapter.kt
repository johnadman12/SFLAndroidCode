package stock.com.ui.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_live.view.*

import stock.com.R
import stock.com.ui.live_contest.LiveContestActivity
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LiveAdapter(
    val mContext: Context,
    val contest: ArrayList<LobbyContestPojo.Contest>
) : RecyclerView.Adapter<LiveAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_live, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.entry_fee.setText(contest.get(position).entryFees)
        holder.itemView.tvTotalWinnings.setText(contest.get(position).winningAmount)
        holder.itemView.tvWinnersTotal.setText(contest.get(position).totalWinners)
        holder.itemView.tvStockName.setText(contest.get(position).exchangename)
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + contest.get(position).exchangeimage.trim())
            .into(holder.itemView.ivStock)
        var sports: Double =
            (contest.get(position).contestSize.toInt() - contest.get(position).teamsJoined.toInt()).toDouble()
        holder.itemView.tvSprortsLeft.setText(
            contest.get(position).contest_teamremaining.toString()+ "/" +
                    contest.get(position).contestSize
        )
        contest.get(position).setCalculatePosition(sports.toInt())
        holder.itemView.tvRank.setText(contest.get(position).rank)

        if (!contest.get(position).scheduleEnd.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.get(position).scheduleEnd)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            contest.get(position).setDate(diff.toInt())
            if (diff.toString().contains("-")) {
                holder.itemView.tvTime.setText("0:0:0: Left")
            } else {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis

                        val diffSec = diff / 1000
                        val seconds = diffSec % 60
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        holder.itemView.tvTime.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }
        holder.itemView.setOnClickListener {
            mContext.startActivity(Intent(mContext, LiveContestActivity::class.java))
        }

    }


    override fun getItemCount(): Int {
        return contest.size;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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

}

