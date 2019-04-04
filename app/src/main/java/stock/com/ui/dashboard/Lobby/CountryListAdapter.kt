package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.pojo.FilterPojo



class CountryListAdapter(
    val mContext: Context,
    val mContest: List<FilterPojo.Country>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<CountryListAdapter.Country>() {

    var checkedHolder: BooleanArray?=null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Country {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_list, parent, false)
        return Country(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: String)
    }

    override fun onBindViewHolder(holder: Country, position: Int) {
        val currentItem: FilterPojo.Country = mContest.get(position);
        holder.itemView.tvFlagName.setText(mContest.get(position).name)
        Glide.with(mContext).load(mContest.get(position).flagUrl6464).into(holder.itemView.ivFlag)

        holder.itemView.checkCountry.setChecked(checkedHolder?.get(position)!!);

        holder.itemView.checkCountry.setOnClickListener {
            if (holder.itemView.checkCountry.isChecked()) {
                checkedHolder!![position] = holder.itemView.checkCountry.isChecked();
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

    inner class Country(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.checkCountry.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }

}