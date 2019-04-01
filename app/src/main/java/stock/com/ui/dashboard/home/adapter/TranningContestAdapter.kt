package stock.com.ui.dashboard.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_expendable_layout.view.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity
import stock.com.ui.pojo.HomePojo
import stock.com.utils.DateUtils
import stock.com.utils.ViewAnimationUtils

class TranningContestAdapter(val mContext: Context/*, val mContest: List<HomePojo.FeatureContest>*/) :
    RecyclerView.Adapter<TranningContestAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_featured_contest, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        /* holder.itemView.ll_child_lay.setOnClickListener {
             mContext.startActivity(Intent(mContext, AllContestActivity::class.java))
         }
         holder.itemView.ll_header_lay.setOnClickListener {
             holder.itemView.first_view.visibility = View.VISIBLE
             clickPlusIcon(holder.itemView.ll_child_lay, holder.itemView.nse_image)
         }*/
        /* holder.itemView.entry_fee.setText(mContest.get(position).entryFees)
         holder.itemView.entry_lable.setText(mContest.get(position).exchangename)

 //        Glide.with(mContext).load(mContest.get(position)).into(holder.itemView.nse_image)


         holder.itemView.txtcreated.setText(DateUtils.changeDate(mContest.get(position).scheduleStart))
         holder.itemView.txtStatus.setText(mContest.get(position).contestType)
         holder.itemView.txtCurrentFee.setText(mContest.get(position).entryFees)*/
        holder.itemView.circular_progress.isAnimationEnabled
        holder.itemView.circular_progress.setProgress(500.00, 1000.00)
//        holder.itemView.circular_progress.setMaxProgress(10000.0);
        holder.itemView.circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)
    }


    override fun getItemCount(): Int {
//        return mContest.size
        return 5
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    private fun clickPlusIcon(lin_child_title: LinearLayout, header_plus_icon: ImageView) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
        }
    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = "Join Now"
            sb
        }
}

