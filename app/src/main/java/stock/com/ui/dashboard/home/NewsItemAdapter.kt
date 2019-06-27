package stock.com.ui.dashboard.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_search_items.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.dashboard.profile.ActivityOtherUserProfile
import stock.com.ui.pojo.Demo
import stock.com.utils.StockConstant

class NewsItemAdapter(
    val mContext: Context,
    val users: ArrayList<Demo>,
    val title: String
) :
    RecyclerView.Adapter<NewsItemAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_items, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.name.setText(users.get(position).name)

        holder.itemView.setOnClickListener {
            if (title.equals("Contest", true)) {
            } else if (title.equals("Stocks", true)) {
                var intent = Intent(mContext, ActivityStockDetail::class.java);
                intent.putExtra("cryptoId", users.get(position).id)
                intent.putExtra(StockConstant.STOCKLIST, "")
                intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
                ActivityCompat.startActivityForResult(mContext as Activity, intent, 411, null);

            } else if (title.equals("Users", true)) {
                mContext.startActivity(
                    Intent(mContext, ActivityOtherUserProfile::class.java)
                        .putExtra(StockConstant.FRIENDID, users.get(position).id)
                )
            } else if (title.equals("Cryptocurrency", true)) {
                var intent = Intent(mContext, ActivityMarketDetail::class.java);
                intent.putExtra("cryptoId", users.get(position).id)
                intent.putExtra(StockConstant.MARKETLIST, "")
                intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
                ActivityCompat.startActivityForResult(mContext as Activity, intent, 410, null);

            }
        }
    }

    override fun getItemCount(): Int {
        return users.size

    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}