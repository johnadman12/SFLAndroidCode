package stock.com.ui.dashboard.my_contest.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_my_contest.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.UpcomingContestDetailActivity
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpcomingAdapter(
    val mContext: Context,
    val contest: ArrayList<LobbyContestPojo.Contest>
) : RecyclerView.Adapter<UpcomingAdapter.ContestListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_my_contest, parent, false)

        return ContestListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContestListHolder, position: Int) {
        holder.itemView.entry_fee.setText(contest.get(position).entryFees)
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

        holder.itemView.tvSprortsLeft.setText(
            contest.get(position).contest_teamremaining.toString() + "/" +
                    contest.get(position).contestSize
        )

        holder.itemView.tvTime.setText(parseDateToddMMyyyy(contest.get(position).scheduleStart))
        if (!contest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.get(position).scheduleStart)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            contest.get(position).setDate(diff.toInt())
            if (diff.toString().contains("-")) {
                holder.itemView.tvTimeLeft.setText("00H:00M:00S")
            } else if (diff.equals("3600000")) {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
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
                        holder.itemView.tvTimeLeft.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }

        holder.itemView.setOnClickListener {
            if (contest.get(position).marketname.equals("Equity")) {
                var intent = Intent(mContext, UpcomingContestDetailActivity::class.java);
                intent.putExtra(StockConstant.CONTESTID, contest.get(position).contestid)
                intent.putExtra(StockConstant.EXCHANGEID, contest.get(position).exchangeid)
                ActivityCompat.startActivityForResult(mContext as Activity, intent, 404, null);
            } else {
                var intent = Intent(mContext, UpcomingContestDetailActivity::class.java);
                intent.putExtra(StockConstant.CONTESTID, contest.get(position).contestid)
                intent.putExtra(StockConstant.EXCHANGEID, contest.get(position).exchangeid)
                ActivityCompat.startActivityForResult(
                    mContext as Activity,
                    intent,
                    StockConstant.REDIRECT_UPCOMING_MARKET,
                    null
                );
            }
        }

    }

    override fun getItemCount(): Int {
        return contest.size;
    }


    inner class ContestListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
