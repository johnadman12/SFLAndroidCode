package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import stock.com.ui.pojo.HomeSearchPojo

class StockHomeAdapter(
    val mContext: Context,
    stocks: ArrayList<HomeSearchPojo.Stock>
) :
    RecyclerView.Adapter<StockHomeAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockHomeAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_search_items, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}