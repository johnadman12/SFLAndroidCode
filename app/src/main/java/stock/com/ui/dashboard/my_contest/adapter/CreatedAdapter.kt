package stock.com.ui.dashboard.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_my_contest.view.*

import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.contest_invitation.ContestInvitationActivity
import stock.com.ui.pojo.CreateContest
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CreatedAdapter(
    val mContext: Context,
    val usercontest: MutableList<CreateContest.Usercontest>
) : RecyclerView.Adapter<CreatedAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_my_contest, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tvContestType.setText(usercontest.get(position).catname);
        holder.itemView.entry_fee.setText(usercontest.get(position).entryFees);
        holder.itemView.tvWinnersTotal.setText(usercontest.get(position).totalWinners);
        holder.itemView.tvTotalWinnings.setText(usercontest.get(position).winningAmount);
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(usercontest.get(position).scheduleEnd));

        var sports: Int =
            usercontest.get(position).contestSize - usercontest.get(position).teamsJoined
        holder.itemView.tvSprortsLeft.setText(sports.toString() + " /" + usercontest.get(position).contestSize)


        if (usercontest.get(position).marketname.equals("Equity")) {
            if (!TextUtils.isEmpty(usercontest.get(position).exchangeimage))
                Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + usercontest.get(position).exchangeimage.trim())
                    .into(holder.itemView.ivStock)
            if (usercontest.get(position).exchangename != null)
                holder.itemView.tvStockName.setText(usercontest.get(position).exchangename)
        } else {
            if (usercontest.get(position).marketname != null)
                holder.itemView.tvStockName.setText(usercontest.get(position).marketname)
            Glide.with(mContext).load(R.drawable.ic_business)
                .into(holder.itemView.ivStock)
        }

        holder.itemView.tvInvite.setOnClickListener {
            if (usercontest.get(position).invitedCode != null) {
                mContext.startActivity(
                    Intent(mContext, ContestInvitationActivity::class.java)
                        .putExtra(StockConstant.CONTESTCODE, usercontest.get(position).invitedCode)
                        .putExtra(StockConstant.CONTESTID, usercontest.get(position).contestid)
                )
            } else {
                mContext.startActivity(
                    Intent(mContext, ContestInvitationActivity::class.java)
                        .putExtra(StockConstant.CONTESTCODE, "")
                        .putExtra(StockConstant.CONTESTID, usercontest.get(position).contestid)
                )
            }
        }

        if (!usercontest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            //date = inputFormat.parse(usercontest.get(position).scheduleStart)
            date = inputFormat.parse(usercontest.get(position).scheduleEnd)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date)
            Log.d("dkdklsdjkjdklkl",date.toString());
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            Log.d("dkldjsdjdlk",diff.toString());
            //usercontest.get(position).setDate(diff.toInt())
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
                        holder.itemView.tvTimeLeft.setText("Completed");
                    }
                }
                newtimer.start()

            } else {
                val newtimer = object : CountDownTimer(diff, 1000) {
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
                        holder.itemView.tvTimeLeft.setText("Complete");
                    }
                }
                newtimer.start()
            }
        }

        /*holder.itemView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ContestDetailActivity::class.java).putExtra(
                    StockConstant.CONTESTID,
                    usercontest.get(position).userContestId.toInt()
                ).putExtra(
                    StockConstant.EXCHANGEID,
                    usercontest.get(position).exchangeId.toInt()
                )
            )
        }*/

    }


    override fun getItemCount(): Int {
        return usercontest.size;
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

