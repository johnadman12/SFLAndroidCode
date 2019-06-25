package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import stock.com.ui.pojo.HomeSearchPojo

class ContestHomeAdapter(
    val mContext: Context,
    val contests: ArrayList<HomeSearchPojo.Contests>
) :
    RecyclerView.Adapter<ContestHomeAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestHomeAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_items, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return contests.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}