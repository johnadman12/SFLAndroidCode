package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import kotlinx.android.synthetic.main.row_search_header.view.*
import stock.com.ui.pojo.HomeSearchPojo


class HomeNewsAdapter(val mContext: Context, val data: ArrayList<HomeSearchPojo>) :
    RecyclerView.Adapter<HomeNewsAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_header, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

        if (data.get(position).users!!.size > 0) {
            holder.itemView.tv_header.setText(data.get(position).title)
            val mAdapter = NewsItemAdapter(mContext, data.get(position).users!!, data.get(position).title)
            val mLayoutManager = LinearLayoutManager(mContext)
            holder.itemView.recycle_search_items.setLayoutManager(mLayoutManager)
            holder.itemView.recycle_search_items.setItemAnimator(DefaultItemAnimator())
            holder.itemView.recycle_search_items.setAdapter(mAdapter)

        }
        else{
            holder.itemView.tv_header.visibility= View.GONE
            holder.itemView.recycle_search_items.visibility= View.GONE
        }
        // you code here


    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    /*fun fromJson(jsonString: String, type: Type): Any {
        return Gson().fromJson(jsonString, type)
    }*/


}