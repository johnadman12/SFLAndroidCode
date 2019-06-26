package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_search_items.view.*
import stock.com.R
import stock.com.ui.pojo.Demo
import stock.com.ui.pojo.HomeSearchPojo

class NewsItemAdapter(
    val mContext: Context,
    val users: ArrayList<Demo>
) :
    RecyclerView.Adapter<NewsItemAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_items, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.name.setText(users.get(position).name)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}