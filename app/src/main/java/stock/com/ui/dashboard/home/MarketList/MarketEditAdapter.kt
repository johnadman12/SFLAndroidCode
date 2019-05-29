package stock.com.ui.dashboard.home.MarketList

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.ActivityEditTeam
import stock.com.ui.dashboard.Team.EditTeamAdapter
import stock.com.ui.pojo.MarketList
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate

class MarketEditAdapter (val mContext: Context,
                         val activity: ActivityMarketEdit,
                         val mContest: MutableList<MarketList.Crypto>,
                         val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<MarketEditAdapter.FeatureListHolder>(), Filterable {
    var checkedHolder: BooleanArray? = null;

    private var searchList: List<MarketList.Crypto>? = null
    var list: ArrayList<MarketList.Crypto> = ArrayList()


    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        this.searchList = mContest;

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
        holder.itemView.ll_market.visibility = View.VISIBLE
        holder.itemView.prev.visibility = View.GONE
        holder.itemView.img_time.visibility = View.GONE
        holder.itemView.tvPrevClose.visibility = View.GONE
        holder.itemView.tvPercentage.setText(mContest.get(position).changeper)
        holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
        holder.itemView.tvPercentage.setText(searchList!!.get(position).latestPrice)
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)


        holder.itemView.img_add.setOnClickListener {
            if (activity.getTeamText() > 11) {
                AppDelegate.showSneakBarRed(
                    mContext,
                    "You have selected maximum number of Crypto for your team.",
                    "DFX"
                )
            } else {
                holder.itemView.llremoveStock.visibility = View.VISIBLE
                holder.itemView.img_add.visibility = View.GONE
                searchList!!.get(position).addedToList = 1
                onItemCheckListener.onItemCheck(searchList!!.get(position));
            }
        }

        if (!TextUtils.isEmpty(searchList!!.get(position).changeper))
            if (searchList!!.get(position).changeper.contains("-"))
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.img_graph)
            else
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.img_graph)



        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
            searchList!!.get(position).addedToList = 0
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }

        //default value of addedlisttostock -> 0
        if (searchList!!.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = View.VISIBLE
            holder.itemView.img_add.visibility = View.GONE
        } else if (searchList!!.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
        }
//
        if (searchList!!.get(position).cryptoType.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (searchList!!.get(position).cryptoType.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(searchList!!.get(position))
        }

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                searchList!!.get(position).cryptoType = "0";
            } else
                searchList!!.get(position).cryptoType = "1";
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
                searchList = filterResults.values as ArrayList<MarketList.Crypto>
                notifyDataSetChanged()
            }
        }
    }
}