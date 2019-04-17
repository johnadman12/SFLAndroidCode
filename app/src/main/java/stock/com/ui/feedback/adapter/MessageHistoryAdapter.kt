package stock.com.ui.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R

class MessageHistoryAdapter(val mContext: Context): RecyclerView.Adapter<MessageHistoryAdapter.WatchListHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_message_history, parent, false)
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

