package stock.com.ui.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_finished.view.*

import stock.com.R
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
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
        holder.itemView.tvStockName.setText(contest.get(position).exchangename)
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + contest.get(position).exchangeimage.trim())
            .into(holder.itemView.ivStock)
        var sports: Double =
            (contest.get(position).contestSize.toInt() - contest.get(position).teamsJoined.toInt()).toDouble()
        holder.itemView.tvSprortsLeft.setText(
            sports.toString() + "/" +
                    contest.get(position).contestSize
        )
        contest.get(position).setCalculatePosition(sports.toInt())
        holder.itemView.tvRank.setText(contest.get(position).rank)
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(contest.get(position).scheduleStart))
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

