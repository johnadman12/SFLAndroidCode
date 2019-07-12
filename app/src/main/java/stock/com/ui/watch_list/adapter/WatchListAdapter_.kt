package stock.com.ui.watch_list.adapter

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.row_view_watch_list.view.*
import stock.com.R
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat

import stock.com.ui.pojo.WatchlistPojo
import stock.com.ui.watch_list.WatchListActivity

class WatchListAdapter_(
    val mContext: Context,
    val mContest: MutableList<WatchlistPojo.WatchStock>,
    val activity: WatchListActivity
) :
    RecyclerView.Adapter<WatchListAdapter_.WatchListHolder>(), Filterable {

    private var searchList: List<WatchlistPojo.WatchStock>? = null

    init {
        this.searchList = mContest;
    }

    private val viewBinderHelper = ViewBinderHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_watch_list, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        viewBinderHelper.bind(holder.itemView.swipeRevealLayout, "" + position);
        holder.itemView.tv_company_name.setText(searchList!!.get(position).symbol);
        holder.itemView.tv_id.setText(searchList!!.get(position).id);
        holder.itemView.tv_sector.setText(searchList!!.get(position).companyName);
        holder.itemView.tv_change_percentage.setText(searchList!!.get(position).changePercent);
        holder.itemView.tv_market_open.setText(searchList!!.get(position).latestPrice);
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.imageView)
        holder.itemView.img_btn_remove.setOnClickListener {
            if (activity != null) {
                activity.callApiRemoveWatch(holder.itemView.tv_id.text.toString())
            }
        }

        if (!TextUtils.isEmpty(searchList!!.get(position).changePercent))
            if (searchList!!.get(position).changePercent.contains("-")) {
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.green));
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
                    searchList = mContest;

                } else {
                    val filteredList = ArrayList<WatchlistPojo.WatchStock>()
                    for (row in mContest) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        } else if (row.companyName!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<WatchlistPojo.WatchStock>
                notifyDataSetChanged()
            }
        }
    }
}

