package stock.com.ui.offer_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import stock.com.R
import java.util.*
import kotlinx.android.synthetic.main.row_view_offers.view.*
import stock.com.ui.pojo.OffersPojo


class OfferListAdapter(val mContext: Context, val list: ArrayList<OffersPojo.offers>) :
    RecyclerView.Adapter<OfferListAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_offers, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

        holder.itemView.tv_title.setText(list.get(position).title);
        holder.itemView.tv_expire_date.setText(list.get(position).expire_date);
        holder.itemView.tv_offer_detail.setText(list.get(position).description);
        holder.itemView.tv_offer_detail.setText(list.get(position).description);
        holder.itemView.tv_created.setText(list.get(position).created);

        Glide.with(mContext).load(list!!.get(position).image).into(holder.itemView.iv_company_logo)



    }


    override fun getItemCount(): Int {
        return list.size;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}

