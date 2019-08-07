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
    val mContest: ArrayList<WatchlistPojo.WatchStock>,
    val activity: WatchListActivity
) :
    RecyclerView.Adapter<WatchListAdapter_.WatchListHolder>(), Filterable {

    var searchList: ArrayList<WatchlistPojo.WatchStock>? = null

    init {
        this.searchList = mContest;
    }

    private val viewBinderHelper = ViewBinderHelper()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_watch_list, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        Glide.with(mContext).load(searchList!!.get(position).currency_firstflag).into(holder.itemView.img1)
        Glide.with(mContext).load(searchList!!.get(position).currency_secondflag).into(holder.itemView.img2)
        //holder.itemView.tv_change_percentage.setText(searchList!!.get(position).changePercent);
        holder.itemView.tv_id.setText(searchList!!.get(position).id);
        holder.itemView.tv_market_open.setText(searchList!!.get(position).latestPrice);

        if (searchList!!.get(position).marketname.equals("Currency")) {
            if (TextUtils.isEmpty(searchList!!.get(position).changePercent))
                searchList!!.get(position).changePercent = searchList!!.get(position).currency_netchange
            holder.itemView.rl_double_flag.visibility = View.VISIBLE;
            holder.itemView.imageView.visibility = View.GONE;
            holder.itemView.tv_company_name.setText(searchList!!.get(position).currency_symbol);
            holder.itemView.tv_sector.setText("");
            //holder.itemView.tv_change_percentage.setText(searchList!!.get(position).currency_perchange);
            if (!TextUtils.isEmpty(searchList!!.get(position).currency_perchange)) {
                if (searchList!!.get(position).currency_perchange!! > 0.toString()) {
                    Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                    holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                } else {
                    Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                    holder.itemView.tv_change_percentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.colorRed
                        )
                    );
                    holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                }
            }
        } else {
            holder.itemView.rl_double_flag.visibility = View.GONE
            holder.itemView.imageView.visibility = View.VISIBLE;
            holder.itemView.tv_company_name.setText(searchList!!.get(position).symbol);
            holder.itemView.tv_change_percentage.setText(searchList!!.get(position).changePercent);
            Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.imageView)
            holder.itemView.tv_sector.setText(searchList!!.get(position).companyName);

            if (!TextUtils.isEmpty(searchList!!.get(position).changePercent))
                if (searchList!!.get(position).changePercent!!.contains("-")) {
                    Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                    holder.itemView.tv_change_percentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.colorRed
                        )
                    );
                    holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
                } else {
                    Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                    holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    holder.itemView.tv_market_open.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                }

        }

        holder.itemView.img_btn_remove.setOnClickListener {
            if (mContext != null) {
                activity.callApiRemoveWatch(holder.itemView.tv_id.text.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        return searchList!!.size;
    }

    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    /* override fun getFilter(): Filter {
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
                             Log.d("dadada", "---" + filteredList.size);
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
                 searchList =
                     filterResults.values as ArrayList<WatchlistPojo.WatchStock>?
                 notifyDataSetChanged()
             }
         }
     }*/

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = mContest;
                } else {
                    val filteredList = ArrayList<WatchlistPojo.WatchStock>()
                    for (row in mContest) {
                        if (row.symbol != null) {
                            if (row.symbol!!.toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(row)
                        } else if (row.companyName != null) {
                            if (row.companyName!!.toLowerCase().contains(charString.toLowerCase()))
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
                searchList =
                    filterResults.values as ArrayList<WatchlistPojo.WatchStock>? /*as java.util.ArrayList<WatchlistPojo.WatchStock>*/
                notifyDataSetChanged()
            }
        }
    }
}

