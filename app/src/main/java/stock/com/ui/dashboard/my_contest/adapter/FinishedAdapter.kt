package stock.com.ui.dashboard.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_finished.view.*

import stock.com.R
import stock.com.ui.dashboard.Team.LiveScoreActivity
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class FinishedAdapter(
    val mContext: Context,
    val contest: ArrayList<LobbyContestPojo.Contest>
) : RecyclerView.Adapter<FinishedAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_finished, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.entry_fee.setText(contest.get(position).entryFees)
        holder.itemView.tvTotalWinnings.setText(contest.get(position).winningAmount)
        holder.itemView.tvWinnersTotal.setText(contest.get(position).totalWinners)
        holder.itemView.tvContestType.setText(contest.get(position).catname)

        var amount: String = contest.get(position).entryFees.substring(1)
        if (amount.equals("0") && contest.get(position).priceBreak.size <= 0) {
            holder.itemView.tvTotalWinnings.setText("Free")
            holder.itemView.text_totalwin.visibility = GONE
            holder.itemView.llWinners.isEnabled = false
        } else {
            holder.itemView.text_totalwin.visibility = VISIBLE
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
            contest.get(position).contestSize - contest.get(position).contest_teamremaining

        holder.itemView.tvSprortsLeft.setText(sports.toString() + " Participants")
        contest.get(position).setCalculatePosition(sports.toInt())
        holder.itemView.tvRank.setText(contest.get(position).rank)
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(contest.get(position).scheduleStart))
        holder.itemView.tvScore.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, LiveScoreActivity::class.java)
                    .putExtra(StockConstant.CONTESTID, contest.get(position).contestId)
                    .putExtra(StockConstant.MARKETID, contest.get(position).mid)
            )
        }
    }


    override fun getItemCount(): Int {
        return contest.size;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM HH:mm:ss"
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

}

