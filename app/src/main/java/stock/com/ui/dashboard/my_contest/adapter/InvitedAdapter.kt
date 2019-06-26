package stock.com.ui.dashboard.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_invited.view.*
import stock.com.R
import stock.com.ui.dashboard.my_contest.fragment.InvitedFragment
import stock.com.ui.pojo.InvitedList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class InvitedAdapter(
    val mContext: Context,
    val contest: List<InvitedList.Usercontest>?,
    val fragment: InvitedFragment
) : RecyclerView.Adapter<InvitedAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_invited, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tvTimeLeft.setText("Invited")
        holder.itemView.tvScore.setText("Accept")
        holder.itemView.lin_rank.visibility = View.GONE
        holder.itemView.entry_fee.setText(contest!!.get(position).entryFees)
        holder.itemView.tvTotalWinnings.setText(contest.get(position).winningAmount)
        holder.itemView.tvWinnersTotal.setText(contest.get(position).totalWinners)
        holder.itemView.tvContestType.setText(contest.get(position).catname)

        if (contest.get(position).inviteStatus.equals("0")) {
            holder.itemView.tvaccepted.visibility = View.GONE
            holder.itemView.llAccept.visibility = View.VISIBLE
        } else {
            holder.itemView.tvaccepted.visibility = View.VISIBLE
            holder.itemView.llAccept.visibility = View.GONE
        }

        var amount: String = contest.get(position).entryFees.substring(1)
        if (amount.equals("0") && contest.get(position).priceBreak!!.size <= 0) {
            holder.itemView.tvTotalWinnings.setText("Free")
            holder.itemView.text_totalwin.visibility = View.GONE
            holder.itemView.llWinners.isEnabled = false
        } else {
            holder.itemView.text_totalwin.visibility = View.VISIBLE
            holder.itemView.tvTotalWinnings.setText(contest.get(position).winningAmount)
            holder.itemView.llWinners.isEnabled = true
        }

        if (contest.get(position).marketname.equals("Equity")) {
            Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + contest.get(position).exchangeimage.trim())
                .into(holder.itemView.ivStock)
            holder.itemView.tvStockName.setText(contest.get(position).exchangename)

        } else {
            holder.itemView.tvStockName.setText(contest.get(position).marketname)
            Glide.with(mContext).load(R.drawable.ic_business)
                .into(holder.itemView.ivStock)
        }

        var sports: Int =
            contest.get(position).contestSize.toInt() - contest.get(position).contestTeamremaining.toInt()

        holder.itemView.tvSprortsLeft.setText(sports.toString() + " Participants")
//        contest.get(position).setCalculatePosition(sports.toInt())
//        holder.itemView.tvRank.setText(contest.get(position).ra)


        if (!contest.get(position).scheduleEnd.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.get(position).scheduleEnd)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            if (diff.toString().contains("-")) {
                holder.itemView.tvTime.setText("00H:00M:00S")
            } else if (diff.equals("3600000")) {
                val newtimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        val diffSec = diff / 1000
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        holder.itemView.tvTime.setText(hours.toString() + "H: " + minutes.toString() + "M: ")
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
        holder.itemView.tvScore.setOnClickListener {
            fragment.acceptInvitation(contest.get(position).manageInvitedId, "1")
        }

        holder.itemView.tv_reject.setOnClickListener {
            fragment.acceptInvitation(contest.get(position).manageInvitedId, "0")
        }

    }


    override fun getItemCount(): Int {
        return contest!!.size;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
}