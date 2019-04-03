package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.pojo.FilterPojo

class CountryListAdapter(val mContext: Context, val mContest: List<FilterPojo.Country>) :
    RecyclerView.Adapter<CountryListAdapter.Country>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Country {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_list, parent, false)
        return Country(view)
    }

    override fun onBindViewHolder(holder: Country, position: Int) {
        holder.itemView.tvFlagName.setText(mContest.get(position).name)
        Glide.with(mContext).load(mContest.get(position).flagUrl6464).into(holder.itemView.ivFlag)
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class Country(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}