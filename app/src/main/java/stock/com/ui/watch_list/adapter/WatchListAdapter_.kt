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
import stock.com.ui.watch_list.WatchListActivity

class WatchListAdapter_(val mContext: Context, val mContest: MutableList<StockPojo.Stock>, val activity: WatchListActivity):
    RecyclerView.Adapter<WatchListAdapter_.WatchListHolder>(), Filterable {

    private var searchList: List<StockPojo.Stock>? = null

    init {
        this.searchList=mContest;
    }

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_watch_list, parent, false)
        return WatchListHolder(view)
    }
    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
         viewBinderHelper.bind(holder.itemView.swipeRevealLayout, ""+position);
         holder.itemView.tv_company_name.setText(searchList!!.get(position).symbol);
         holder.itemView.tv_sector.setText(searchList!!.get(position).sector);
         holder.itemView.tv_change_percentage.setText(searchList!!.get(position).changePercent);
         holder.itemView.tv_market_open.setText(searchList!!.get(position).marketopen);

        holder.itemView.img_btn_remove.setOnClickListener {
            if(activity!= null){
                activity.callApiRemoveWatch(searchList!!.get(position).id)
            }
        }
        if(!searchList!!.get(position).image.equals("")){
            Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.imageView)
        }else{
            holder.itemView.imageView.setImageResource(0)
        }
     }
    override fun getItemCount(): Int {
        return searchList!!.size;
    }
    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = mContest
                } else {
                    val filteredList = ArrayList<StockPojo.Stock>()
                    for (row in mContest) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                       }
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }
            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<StockPojo.Stock>
                notifyDataSetChanged()
            }
        }
    }
}

