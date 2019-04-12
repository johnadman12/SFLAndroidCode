package stock.com.ui.offer_list

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity
import stock.com.ui.pojo.HomePojo
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.DateUtils
import stock.com.utils.ViewAnimationUtils
import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException
import java.util.concurrent.TimeUnit
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.row_view_offers.view.*
import kotlinx.android.synthetic.main.row_view_watch_list.view.*
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

