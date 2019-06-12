package stock.com.ui.dashboard.Market

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_contest_list.view.*
import stock.com.R
import stock.com.ui.pojo.MarketTypeFilters

class MarketListFilterAdapter (
    val mContext: Context
    , val searchList: List<MarketTypeFilters.Exchange>
    , val filter: String,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<MarketListFilterAdapter.WatchListHolder>() {
    var checkedHolder: BooleanArray? = null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(searchList.size)
    }

    init {
        createCheckedHolder();
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_list, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        val currentItem: MarketTypeFilters.Exchange = searchList.get(position);
        holder.itemView.checkboxContest.setText(searchList.get(position).name)
        holder.itemView.checkboxContest.setChecked(checkedHolder?.get(position)!!);
        val parts = filter.split(",")
        for (i in 0 until parts.size) {
            if (currentItem.name.equals(parts[i])) {
                Log.e("NUMBER------", parts[i])
                holder.itemView.checkboxContest.isChecked = true;
                onItemCheckListener.onItemCheck(currentItem.name);
            }
        }
        holder.itemView.checkboxContest.setOnClickListener {
            if (holder.itemView.checkboxContest.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkboxContest.isChecked();
                onItemCheckListener.onItemCheck(currentItem.name);
            } else {
                onItemCheckListener.onItemUncheck(currentItem.name);
                checkedHolder!![position] = false;
            }
        }

    }

    override fun getItemCount(): Int {
        return searchList.size;
    }

    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }
}