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

import kotlinx.android.synthetic.main.row_view_watch_list.view.*
import stock.com.R


class WatchListAdapter(val mContext: Context/*, val mContest: List<String>*/):
    RecyclerView.Adapter<WatchListAdapter.SlideMenuHolder>() {
    // This object helps you save/restore the open/close state of each view
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideMenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_watch_list, parent, false)
        return SlideMenuHolder(view)
    }
    override fun onBindViewHolder(holder: SlideMenuHolder, position: Int) {
       // holder.itemView. tv_title_menu.setText(mContest.get(position));

        viewBinderHelper.bind(holder.itemView.swipeRevealLayout, ""+position);

    }
    override fun getItemCount(): Int {
        //return mContest.size;
        return 20;
    }
    inner class SlideMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun saveStates(outState: Bundle) {
        viewBinderHelper.saveStates(outState)
    }

    /**
     * Only if you need to restore open/close state when the orientation is changed.
     * Call this method in [android.app.Activity.onRestoreInstanceState]
     */
    fun restoreStates(inState: Bundle) {
        viewBinderHelper.restoreStates(inState)
    }
}

