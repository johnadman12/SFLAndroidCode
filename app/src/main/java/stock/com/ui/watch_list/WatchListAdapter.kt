package stock.com.ui.watch_list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import kotlinx.android.synthetic.main.row_view_slide_menu.view.*
import stock.com.ui.support.SupportActivity
import stock.com.ui.watch_list.WatchListActivity
import com.chauthai.swipereveallayout.ViewBinderHelper

import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_latest_news.view.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*

import kotlinx.android.synthetic.main.row_view_watch_list.view.*
import stock.com.R
import stock.com.ui.pojo.StockPojo
import stock.com.utils.AppDelegate


class WatchListAdapter(val mContext: Context, val mContest: List<StockPojo.Stock>,val activity: WatchListActivity):
    RecyclerView.Adapter<WatchListAdapter.WatchListHolder>() {
    // This object helps you save/restore the open/close state of each view
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_watch_list, parent, false)
        return WatchListHolder(view)
    }
    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
       // holder.itemView. tv_title_menu.setText(mContest.get(position));
         viewBinderHelper.bind(holder.itemView.swipeRevealLayout, ""+position);
         holder.itemView.tv_company_name.setText(mContest.get(position).symbol);
         holder.itemView.tv_sector.setText(mContest.get(position).sector);
         holder.itemView.tv_change_percentage.setText(mContest.get(position).changePercent);
         holder.itemView.tv_market_open.setText(mContest.get(position).marketopen);


        holder.itemView.img_btn_remove.setOnClickListener {
            if(activity!= null){
                activity.callApiRemoveWatch(mContest.get(position).id)
            }
        }

        if(!mContest.get(position).image.equals("")){
            Glide.with(mContext).load(mContest.get(position).image).into(holder.itemView.imageView)
        }

     }
    override fun getItemCount(): Int {
        return mContest.size;
    }
    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}

