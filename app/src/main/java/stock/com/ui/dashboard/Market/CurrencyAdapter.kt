package stock.com.ui.dashboard.Market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
class CurrencyAdapter(val mContext: Context) : RecyclerView.Adapter<CurrencyAdapter.FeatureListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
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
