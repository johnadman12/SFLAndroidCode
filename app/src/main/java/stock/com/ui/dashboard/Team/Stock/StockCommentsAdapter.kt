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
import stock.com.ui.news.activity.ActivityNewsDetail
import stock.com.ui.pojo.NewsPojo

class StockCommentsAdapter  (val mContext: Context) :
    RecyclerView.Adapter<StockCommentsAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_comments, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 10
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}