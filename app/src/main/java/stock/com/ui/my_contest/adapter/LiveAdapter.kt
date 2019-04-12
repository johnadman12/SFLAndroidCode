package stock.com.ui.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import stock.com.R


class LiveAdapter(val mContext: Context) : RecyclerView.Adapter<LiveAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_live, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        /* holder.itemView.card_view.setPadding(0,0,0,0);
         holder.itemView.card_view.setUseCompatPadding(true);
         holder.itemView.card_view.setContentPadding(-6,-600,-6,-6);
         holder.itemView.card_view.setPreventCornerOverlap(false);*/

    }


    override fun getItemCount(): Int {
        // return list.size;
        return 10;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}

