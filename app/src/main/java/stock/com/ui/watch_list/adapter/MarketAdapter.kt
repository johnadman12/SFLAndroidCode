package stock.com.ui.watch_list.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.row_view_watch_list.view.*
import stock.com.R
import stock.com.ui.pojo.StockPojo
import android.widget.Filter
import android.widget.Filterable
import kotlinx.android.synthetic.main.row_contest_list.view.*
import stock.com.ui.pojo.WatchListFilterPojo
import stock.com.ui.watch_list.WatchListActivity

class MarketAdapter(val mContext: Context,val searchList: List<WatchListFilterPojo.market>): RecyclerView.Adapter<MarketAdapter.WatchListHolder>() {



    // This object helps you save/restore the open/close state of each view
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_list, parent, false)
        return WatchListHolder(view)
    }
    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {

        holder.itemView.tvContestName.setText(searchList.get(position).name)

     }
    override fun getItemCount(): Int {
        return searchList!!.size;
    }
    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}
