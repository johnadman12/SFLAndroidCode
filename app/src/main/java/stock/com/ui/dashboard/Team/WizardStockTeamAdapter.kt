package stock.com.ui.dashboard.Team

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.StockPojo
import stock.com.ui.pojo.StockTeamPojo

class WizardStockTeamAdapter(
    val mContext: Context, val mContest: MutableList<StockTeamPojo.Stock>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<WizardStockTeamAdapter.FeatureListHolder>(), Filterable {
    var checkedHolder: BooleanArray? = null;

      private var searchList: List<StockTeamPojo.Stock>? = null


    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        this.searchList = mContest;

        //Toast.makeText(mContext,""+ searchList!!.size.toString(),1000).show()
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: StockTeamPojo.Stock)
        fun onItemUncheck(item: StockTeamPojo.Stock)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

        holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
        holder.itemView.tvCompanyName.setText(searchList!!.get(position).companyName)
        holder.itemView.tvPrevClose.setText(searchList!!.get(position).previousClose)
        holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
        holder.itemView.tvPercentage.setText(searchList!!.get(position).changePercent)
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)

        holder.itemView.llremoveStock.visibility = VISIBLE
        holder.itemView.img_add.visibility = GONE

       /* if (searchList!!.get(position).addedStock.equals("yes")) {
            holder.itemView.toggleButton1.isChecked = true
        } else {
            holder.itemView.toggleButton1.isChecked = false
        }
*/
        holder.itemView.img_add.setOnClickListener {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
            onItemCheckListener.onItemCheck(searchList!!.get(position));
        }
        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }
    }


    override fun getItemCount(): Int {
        return searchList!!.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.img_add.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }

    override
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = mContest
                } else {
                    val filteredList = ArrayList<StockTeamPojo.Stock>()
                    for (row in mContest) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                            Log.d("dadada", "---" + filteredList.size);
                        }
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<StockTeamPojo.Stock>
                notifyDataSetChanged()
            }
        }
    }
}