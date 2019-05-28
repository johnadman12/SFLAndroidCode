package stock.com.ui.dashboard.home.MarketList

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.MarketList

class MarketWizardAdapter(val mContext: Context, val mContest: MutableList<MarketList.Crypto>,
                          val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<MarketWizardAdapter.FeatureListHolder>(), Filterable {
    var checkedHolder: BooleanArray? = null;

    private var searchList: List<MarketList.Crypto>? = null
    var list: ArrayList<MarketList.Crypto> = ArrayList()

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
        fun onItemCheck(item: MarketList.Crypto)
        fun onItemUncheck(item: MarketList.Crypto)
        fun onItemClick(item: MarketList.Crypto)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        list = ArrayList()
        list = mContest as ArrayList
        holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
        holder.itemView.tvCompanyName.setText(searchList!!.get(position).name)
        holder.itemView.market.setText("marketCap :" + searchList!!.get(position).marketcapital)
        holder.itemView.ll_market.visibility = View.VISIBLE
        holder.itemView.prev.visibility = View.GONE
        holder.itemView.img_time.visibility = View.GONE
        holder.itemView.tvPrevClose.visibility = View.GONE
        holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
        holder.itemView.tvPercentage.setText(searchList!!.get(position).latestPrice)
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)

        if (!TextUtils.isEmpty(searchList!!.get(position).changeper))
            if (searchList!!.get(position).changeper.contains("-"))
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.img_graph)
            else
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.img_graph)

        holder.itemView.llremoveStock.visibility = VISIBLE
        holder.itemView.img_add.visibility = GONE
        searchList!!.get(position).addedToList = 1

        holder.itemView.img_add.setOnClickListener {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
            searchList!!.get(position).addedToList = 1
            onItemCheckListener.onItemCheck(searchList!!.get(position));
        }
        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
            searchList!!.get(position).addedToList = 0
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }

        if (searchList!!.get(position).cryptoType.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (searchList!!.get(position).cryptoType.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                searchList!!.get(position).cryptoType = "0";
            } else
                searchList!!.get(position).cryptoType = "1";

        }
        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(searchList!!.get(position))
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
                    val filteredList = ArrayList<MarketList.Crypto>()
                    for (row in mContest) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<MarketList.Crypto>
                notifyDataSetChanged()
            }
        }
    }
}
