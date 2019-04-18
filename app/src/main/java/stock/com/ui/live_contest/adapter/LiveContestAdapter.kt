package stock.com.ui.live_contest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R

class LiveContestAdapter(val mContext: Context): RecyclerView.Adapter<LiveContestAdapter.WatchListHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_live_contest, parent, false)
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

