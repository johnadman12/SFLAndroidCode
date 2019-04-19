package stock.com.ui.dashboard.Team.Stock

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_latest_news.view.*
import stock.com.R
import stock.com.ui.dashboard.home.adapter.LatestNewsAdapter
import stock.com.ui.news.activity.ActivityNewsDetail
import stock.com.ui.pojo.NewsPojo

class StockNewsAdapter (val mContext: Context, val mContest: List<NewsPojo.News>) :
    RecyclerView.Adapter<StockNewsAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_latest_news, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        Glide.with(mContext).load(mContest.get(position).image).into(holder.itemView.imageViewNews)
        holder.itemView.tvNewsTitle.setText(mContest.get(position).title)
        holder.itemView.tvPercentage.setText(mContest.get(position).newspercentage)
        holder.itemView.tvNewsAuthor.setText(mContest.get(position).channel)
        holder.itemView.tvDescription.setText(mContest.get(position).description)
        holder.itemView.tvNewsTime.setText(mContest.get(position).timeleft)
        holder.itemView.setOnClickListener {

            mContext.startActivity(
                Intent(mContext, ActivityNewsDetail::class.java).putExtra(
                    "newsId",
                    mContest.get(position).id
                )
            )
        }

    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}