package stock.com.ui.dashboard.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.TrainingPojo
import stock.com.utils.AppDelegate
import stock.com.utils.DateUtils
import stock.com.utils.ViewAnimationUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TranningContestAdapter(val mContext: Context, val mContest: List<TrainingPojo.TraniningContest>) :
    RecyclerView.Adapter<TranningContestAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_featured_contest, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.entry_fee.setText(mContest.get(position).entryFees)
        holder.itemView.tvStockName.setText(mContest.get(position).exchangename)
        holder.itemView.tvTime.setText(mContest.get(position).exchangename)
        holder.itemView.tvTotalWinnings.setText(mContest.get(position).winningAmount)
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + mContest.get(position).exchangeimage)
            .into(holder.itemView.ivStock)
        holder.itemView.tvTime.setText(mContest.get(position).scheduleStart)

        if (!mContest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(mContest.get(position).scheduleStart)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
//            val days = diff / (24 * 60 * 60 * 1000)
            val day = TimeUnit.SECONDS.toDays(diff).toInt()
            val hour = TimeUnit.SECONDS.toHours(diff) - (day * 24)
            val minute = TimeUnit.SECONDS.toMinutes(diff) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(diff));
            val seconds = TimeUnit.SECONDS.toSeconds(diff) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(diff));

            holder.itemView.tvTimeLeft.setText(hour.toString() + " : " + minute.toString() + " : " + seconds.toString())
        }
        holder.itemView.circular_progress.isAnimationEnabled
        holder.itemView.circular_progress.setProgress(500.00, 1000.00)
//        holder.itemView.circular_progress.setMaxProgress(10000.0);
        holder.itemView.circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private fun clickPlusIcon(lin_child_title: LinearLayout, header_plus_icon: ImageView) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
        }
    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = "Join Now"
            sb
        }
}

