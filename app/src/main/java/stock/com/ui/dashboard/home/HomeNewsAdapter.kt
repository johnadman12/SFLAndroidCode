package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_search_header.view.*
import stock.com.R
import stock.com.ui.pojo.HomeSearchPojo

class HomeNewsAdapter(val mContext: Context, val data: HomeSearchPojo.Data?) :
    RecyclerView.Adapter<HomeNewsAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_header, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        if (data!!.users!!.size > 0) {
            holder.itemView.tv_header.visibility = View.VISIBLE
            holder.itemView.recycle_search_items.visibility = View.VISIBLE
            holder.itemView.tv_header.setText("Users")
            val mAdapter = NewsItemAdapter(mContext, data.users!!)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)
        } /*else {
            holder.itemView.tv_header.visibility = View.GONE
            holder.itemView.recycle_search_items.visibility = View.GONE

        }*/

        if (data.stocks!!.size > 0) {
            holder.itemView.tv_header.visibility = View.VISIBLE
            holder.itemView.recycle_search_items.visibility = View.VISIBLE
            holder.itemView.tv_header.setText("Stocks")
            val mAdapter = NewsItemAdapter(mContext, data.stocks!!)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)
        } /*else {
            holder.itemView.tv_header.visibility = View.GONE
            holder.itemView.recycle_search_items.visibility = View.GONE

        }*/

        if (data.contests!!.size > 0) {
            holder.itemView.tv_header.visibility = View.VISIBLE
            holder.itemView.recycle_search_items.visibility = View.VISIBLE
            holder.itemView.tv_header.setText("Contests")
            val mAdapter = NewsItemAdapter(mContext, data.contests!!)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)
        } /*else {
            holder.itemView.tv_header.visibility = View.GONE
            holder.itemView.recycle_search_items.visibility = View.GONE

        }*/

        if (data.crypto!!.size > 0) {
            holder.itemView.tv_header.visibility = View.VISIBLE
            holder.itemView.recycle_search_items.visibility = View.VISIBLE
            holder.itemView.tv_header.setText("CryptoCurrencies")
            val mAdapter = NewsItemAdapter(mContext, data.crypto!!)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)
        } /*else {
            holder.itemView.tv_header.visibility = View.GONE
            holder.itemView.recycle_search_items.visibility = View.GONE

        }*/

        if (data.currency!!.size > 0) {
            holder.itemView.tv_header.visibility = View.VISIBLE
            holder.itemView.recycle_search_items.visibility = View.VISIBLE
            holder.itemView.tv_header.setText("Currencies")
            val mAdapter = NewsItemAdapter(mContext, data.currency!!)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)
        } /*else {
            holder.itemView.tv_header.visibility = View.GONE
            holder.itemView.recycle_search_items.visibility = View.GONE
        }*/


    }


    override fun getItemCount(): Int {
        return 4
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}