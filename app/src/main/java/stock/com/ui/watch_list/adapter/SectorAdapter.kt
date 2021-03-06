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

class SectorAdapter(
    val mContext: Context,
    val searchList: List<WatchListFilterPojo.sector>
    , val filter: String
) :
    RecyclerView.Adapter<SectorAdapter.WatchListHolder>() {
    var checkedHolder: BooleanArray? = null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(searchList.size)
    }

    init {
        createCheckedHolder();
    }

  /*  interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: Int)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_list, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        val currentItem: WatchListFilterPojo.sector = searchList.get(position);
        holder.itemView.checkboxContest.setText(searchList.get(position).sector)
        holder.itemView.checkboxContest.setChecked(checkedHolder?.get(position)!!);
        val parts = filter.split(",")
        for (i in 0 until parts.size) {
            if (currentItem.sector.equals(parts[i])) {
                Log.e("NUMBER------", parts[i])
                holder.itemView.checkboxContest.isChecked = true;
                    checkedHolder!![position] = true;
                //onItemCheckListener.onItemCheck(currentItem.sector);
            }
        }
        holder.itemView.checkboxContest.setOnClickListener {
            if (holder.itemView.checkboxContest.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkboxContest.isChecked();
                //onItemCheckListener.onItemCheck(currentItem.sector);
            } else {
                //onItemCheckListener.onItemUncheck(position);
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

    fun getSeletedtIds():String {
        var ids:String="";
        for (i in 0 until searchList.size) {
            if (checkedHolder!!.get(i)){
                ids = if (ids.equals("")) searchList.get(i).sector else ids + "," + searchList.get(i).sector;
            }
        }
        Log.d("SelectedIds","----"+ids)
        return ids;
    }

}

