package stock.com.ui.dashboard.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import stock.com.ui.pojo.HomeSearchPojo

class CryptoHomeAdapter(
    val mContext: Context,
    crypto: ArrayList<HomeSearchPojo.Crypto>
) :
    RecyclerView.Adapter<CryptoHomeAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHomeAdapter.FeatureListHolder {
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