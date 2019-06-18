package stock.com.ui.dashboard.home

import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_search_header.view.*
import stock.com.R
import stock.com.ui.dashboard.Lobby.PriceBreakUpAdapter

class HomeNewsAdapter(val mContext: Context) :
    RecyclerView.Adapter<HomeNewsAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_header, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val mAdapter = NewsItemAdapter(mContext)
        val mLayoutManager = LinearLayoutManager(mContext)
        holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
        holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
        holder.itemView.recycle_search_items.setAdapter(mAdapter)

    }


    override fun getItemCount(): Int {
        return 5
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}