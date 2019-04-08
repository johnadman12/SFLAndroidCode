package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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
        holder.itemView.tvStockName.setText(mContest.get(position).exchangename)
        holder.itemView.tvTime.setText(mContest.get(position).exchangename)
        holder.itemView.tvTotalWinnings.setText(mContest.get(position).winningAmount)
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + mContest.get(position).exchangeimage.trim())
            .into(holder.itemView.ivStock)
        var sports: Double =
            (mContest.get(position).contestSize.toInt() - mContest.get(position).teamsJoined.toInt()).toDouble()
        holder.itemView.tvSprortsLeft.setText(
            sports.toString() + "/" +
                    mContest.get(position).contestSize
        )
        mContest.get(position).setCalculatePosition(sports.toInt())
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(mContest.get(position).scheduleStart))
        if (!mContest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(mContest.get(position).scheduleStart)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            mContest.get(position).setDate(diff.toInt())
            if (diff.toString().contains("-")) {
                holder.itemView.tvTimeLeft.setText("0:0:0: Left")
            } else {
                val diffSec = diff / 1000
                val days = diffSec / SECONDS_IN_A_DAY
                val secondsDay = diffSec % SECONDS_IN_A_DAY
                val seconds = secondsDay % 60
                val minutes = secondsDay / 60 % 60
                val hours = secondsDay / 3600
                holder.itemView.tvTimeLeft.setText(days.toString() + " : " + hours.toString() + " : " + minutes.toString() + " : " + seconds.toString())
            }
        }
        holder.itemView.circular_progress.isAnimationEnabled
        holder.itemView.circular_progress.setProgress(500.00, 1000.00)
//        holder.itemView.circular_progress.setMaxProgress(10000.0);
        holder.itemView.circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

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
}

