package stock.com.ui.dashboard.Team

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_contest_list.view.*
import stock.com.R
import stock.com.ui.pojo.SectorListPojo

class SectorAdapter(
    val mContext: Context,
    val mContest: List<SectorListPojo.Sector>,
    val filter: String,
    val onItemCheckListener: OnItemCheckListener
) :

    RecyclerView.Adapter<SectorAdapter.FeatureListHolder>() {
    var checkedHolder: BooleanArray? = null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_list, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: String)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val currentItem: SectorListPojo.Sector = mContest.get(position);
        holder.itemView.checkboxContest.setText(mContest.get(position).sector)
        holder.itemView.checkboxContest.setChecked(checkedHolder?.get(position)!!);

        val parts = filter.split(",")
        for (i in 0 until parts.size) {
            if (currentItem.sector.equals(parts[i])) {
                Log.e("NUMBER------", parts[i])
                holder.itemView.checkboxContest.isChecked = true;
                onItemCheckListener.onItemCheck(currentItem.sector);
            }
        }
        holder.itemView.checkboxContest.setOnClickListener {
            if (holder.itemView.checkboxContest.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkboxContest.isChecked();
                onItemCheckListener.onItemCheck(currentItem.sector);
            } else {
                onItemCheckListener.onItemUncheck(currentItem.sector);
                checkedHolder!![position] = false;
            }
        }


    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }
}