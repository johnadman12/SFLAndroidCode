package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import stock.com.R
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.FilterPojo


class CountryListAdapter(
    val mContext: Context,
    val mContest: Country,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<CountryListAdapter.Countryclass>() {

    var checkedHolder: BooleanArray?=null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.country!!.size)
    }

    init {
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Countryclass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_list, parent, false)
        return Countryclass(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: String)
    }

    override fun onBindViewHolder(holder: Countryclass, position: Int) {
        val currentItem: Country.CountryPojo = mContest.country!!.get(position);
        holder.itemView.tvFlagName.setText(currentItem.name)
        Glide.with(mContext).load(currentItem.flagUrl6464).into(holder.itemView.ivFlag)

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
        return mContest.country!!.size
    }

    inner class Countryclass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.checkCountry.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }

}