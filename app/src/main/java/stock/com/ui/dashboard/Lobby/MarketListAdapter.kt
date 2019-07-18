package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.pojo.FilterPojo

class MarketListAdapter(
    val mContext: Context,
    val mContest: List<FilterPojo.Market>,
    val filters: String
) :
    RecyclerView.Adapter<MarketListAdapter.FeatureListHolder>() {
    var checkedHolder: BooleanArray? = null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_list, parent, false)
        return FeatureListHolder(view)
    }



    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val currentItem: FilterPojo.Market = mContest.get(position);
        holder.itemView.checkCountry.setText(mContest.get(position).name)
        Glide.with(mContext).load(mContest.get(position).imageUrl).into(holder.itemView.ivFlag)

        Log.e("marketid", mContest.get(position).name)
        holder.itemView.checkCountry.setChecked(checkedHolder?.get(position)!!);

        val parts = filters.split(",")
        for (i in 0 until parts.size) {
            if (currentItem.id.toString().equals(parts[i])) {
                Log.e("NUMBER------", parts[i].toString())
                holder.itemView.checkCountry.isChecked = true;
            }
        }

        holder.itemView.checkCountry.setOnClickListener {
            if (holder.itemView.checkCountry.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkCountry.isChecked();
            } else {
                checkedHolder!![position] = false;
            }
        }

    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.checkCountry.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }

    fun getSeletedtIds():String {
        var ids:String="";
        for (i in 0 until mContest.size) {
            if (checkedHolder!!.get(i)){
                ids = if (ids.equals("")) mContest.get(i).id.toString() else ids + "," + mContest.get(i).id.toString();
            }
        }
        Log.d("SelectedIdsCountry","----"+ids)
        return ids;
    }
}
