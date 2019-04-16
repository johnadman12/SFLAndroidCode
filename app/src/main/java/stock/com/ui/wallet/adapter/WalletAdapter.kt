package stock.com.ui.wallet.adapter

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

class WalletAdapter(val mContext: Context): RecyclerView.Adapter<WalletAdapter.WatchListHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_history, parent, false)
        return WatchListHolder(view)
    }
    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {

       // holder.itemView.tvContestName.setText(searchList.get(position).asset_type)

     }
    override fun getItemCount(): Int {
        //return searchList!!.size;
        return 10;
    }
    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}

