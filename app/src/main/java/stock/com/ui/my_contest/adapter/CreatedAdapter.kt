package stock.com.ui.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_my_contest.view.*

import stock.com.R
import stock.com.ui.pojo.CreateContest
import stock.com.utils.AppDelegate


class CreatedAdapter(
    val mContext: Context,
    val usercontest: MutableList<CreateContest.Usercontest>
) : RecyclerView.Adapter<CreatedAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_my_contest, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tvContestType.setText(usercontest.get(position).ucontestName);
        holder.itemView.entry_fee.setText(usercontest.get(position).entryFees);
        holder.itemView.tvWinnersTotal.setText(usercontest.get(position).contestsWinner);
        /* if (usercontest.get(position).marketname.equals("Equity")) {
             Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + usercontest.get(position).exchangeimage.trim())
                 .into(holder.itemView.ivStock)
             holder.itemView.tvStockName.setText(usercontest.get(position).exchangename)

         } else {
             holder.itemView.tvStockName.setText(usercontest.get(position).marketname)
             Glide.with(mContext).load(R.drawable.ic_business)
                 .into(holder.itemView.ivStock)
         }*/


    }


    override fun getItemCount(): Int {
        return usercontest.size;
//        return 10;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}

