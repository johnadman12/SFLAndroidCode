package stock.com.ui.dashboard.home.adapter

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_latest_news.view.*
import stock.com.R
import stock.com.ui.news.activity.ActivityNewsDetail
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LatestNewsAdapter(
    val mContext: Context,
    val mContest: ArrayList<CityfalconNewsPojo.Story>,
    val identifires: String
) :
    RecyclerView.Adapter<LatestNewsAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_latest_news, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        Glide.with(mContext).load(mContest.get(position).source!!.imageUrls!!.thumb).into(holder.itemView.imageViewNews)
        holder.itemView.tvNewsTitle.setText(mContest.get(position).title)
//        holder.itemView.tvPercentage.setText(mContest.get(position).newspercentage)
        holder.itemView.tvNewsAuthor.setText(mContest.get(position).source!!.brandName)
        holder.itemView.tvDescription.setText(mContest.get(position).description)
//        holder.itemView.tvNewsTime.setText(parseDateToddMMyyyy(mContest.get(position).publishTime))
        holder.itemView.tvNewsTime.setText(DateUtils.getRelativeTimeSpanString(parseDateToddMMyyyy(mContest.get(position).publishTime)!!,
            Calendar.getInstance().timeInMillis,DateUtils.MINUTE_IN_MILLIS))
        holder.itemView.setOnClickListener {

            mContext.startActivity(
                Intent(mContext, ActivityNewsDetail::class.java)
                    .putExtra(StockConstant.NEWS, mContest.get(position))
            )
        }

    }


    override fun getItemCount(): Int {
        if (mContest.size > 10)
            return 10
        else
            return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun parseDateToddMMyyyy(time: String): Long? {
        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
        val outputPattern: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var dateInMillis: Long = 0
        try {
            val date = outputPattern.parse(time)
            dateInMillis = date.getTime()
            return dateInMillis
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }


}

