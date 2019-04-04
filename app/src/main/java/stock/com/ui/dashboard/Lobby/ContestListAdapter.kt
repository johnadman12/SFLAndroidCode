package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_contest_list.view.*
import stock.com.R
import stock.com.ui.pojo.FilterPojo
import android.R.id.checkbox


class ContestListAdapter(
    val mContext: Context,
    val mContest: List<FilterPojo.Category>,
    val onItemCheckListener: OnItemCheckListener
) :

    RecyclerView.Adapter<ContestListAdapter.FeatureListHolder>() {
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
        val currentItem: FilterPojo.Category = mContest.get(position);
        holder.itemView.tvContestName.setText(mContest.get(position).name)

        holder.itemView.checkboxContest.setChecked(checkedHolder?.get(position)!!);

        holder.itemView.checkboxContest.setOnClickListener {
            if (holder.itemView.checkboxContest.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkboxContest.isChecked();
                onItemCheckListener.onItemCheck(currentItem.id.toString());
            } else {
                onItemCheckListener.onItemUncheck(currentItem.id.toString());
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
