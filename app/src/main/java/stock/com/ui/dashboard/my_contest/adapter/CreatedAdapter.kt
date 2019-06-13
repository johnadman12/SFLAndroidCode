package stock.com.ui.dashboard.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
        holder.itemView.tvContestType.setText(usercontest.get(position).ucontestName);
        holder.itemView.entry_fee.setText(usercontest.get(position).entryFees);
        holder.itemView.tvWinnersTotal.setText(usercontest.get(position).contestsWinner);
        holder.itemView.tvTotalWinnings.setText("$ " + usercontest.get(position).totalWinning);
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(usercontest.get(position).scheduleEnd));

        var sports: Int =
            usercontest.get(position).contestSize - usercontest.get(position).teamJoined
        holder.itemView.tvSprortsLeft.setText(sports.toString() + " /" + usercontest.get(position).contestSize)


        if (usercontest.get(position).name.equals("Equity")) {
            Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + usercontest.get(position).exchangeimage.trim())
                .into(holder.itemView.ivStock)
            holder.itemView.tvStockName.setText(usercontest.get(position).exchangename)
        } else {
            holder.itemView.tvStockName.setText(usercontest.get(position).name)
            Glide.with(mContext).load(R.drawable.ic_business)
                .into(holder.itemView.ivStock)
        }

        holder.itemView.tvInvite.setOnClickListener {
            if (usercontest.get(position).invited_code != null) {
                mContext.startActivity(
                    Intent(mContext, ContestInvitationActivity::class.java)
                        .putExtra(StockConstant.CONTESTCODE, usercontest.get(position).invited_code)
                )
            } else {
                mContext.startActivity(
                    Intent(mContext, ContestInvitationActivity::class.java)
                        .putExtra(StockConstant.CONTESTCODE, "")
                )
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

