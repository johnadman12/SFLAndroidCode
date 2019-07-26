package stock.com.ui.dashboard.home.Currency

import android.content.Context
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Filter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_currency_team.view.*
import stock.com.R
import stock.com.ui.dashboard.home.MarketList.ActivityMarketTeam
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate

class CurrencyTeamAdapter(
    val mContext: Context,
    val mContest: MutableList<CurrencyPojo.Currency>,
    var mContestOld: MutableList<CurrencyPojo.Currency>,
    val activity: ActivityCurrencyTeam, val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<CurrencyTeamAdapter.FeatureListHolder>() {


    override fun getItemCount(): Int {
        return mContestOld.size
    }

    var checkedHolder: BooleanArray? = null;


    private var searchList: List<CurrencyPojo.Currency>? = null


      private fun createCheckedHolder() {
          checkedHolder = BooleanArray(mContest.size)
      }

    init {
        this.searchList = mContestOld;
        createCheckedHolder();
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val anim = AlphaAnimation(0.1f, 1.0f)
        anim.duration = 50 //You can manage the blinking time with this parameter
        anim.startOffset = 20

        Glide.with(mContext).load(searchList!!.get(position).firstflag).centerInside().into(holder.itemView.img1)
        Glide.with(mContext).load(searchList!!.get(position).secondflag).centerInside().into(holder.itemView.img2)

        if (!TextUtils.isEmpty(searchList!!.get(position).changeper)) {
            if (searchList!!.get(position).changeper.contains("-")) {
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setText(
                    searchList!!.get(position).changeper + "%" + "  (" + searchList!!.get(
                        position
                    ).daychange + ")"
                )
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText(
                    searchList!!.get(position).changeper + "%" + "  (" + searchList!!.get(
                        position
                    ).daychange + ")"
                )
            }
        }

        holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
        holder.itemView.bid.setText(searchList!!.get(position).bid)
        holder.itemView.ask.setText(searchList!!.get(position).ask)
        holder.itemView.img_add.setOnClickListener {
            if (activity.getTeamText() > 11) {
                AppDelegate.showSneakBarRed(
                    mContext,
                    "You have selected maximum number of Currency for your team.",
                    "DFX"
                )
            } else {
                holder.itemView.llremoveStock.visibility = View.VISIBLE
                holder.itemView.img_add.visibility = View.GONE
                searchList!!.get(position).addedToList = 1
                onItemCheckListener.onItemCheck(searchList!!.get(position));
            }
        }
        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
            searchList!!.get(position).addedToList = 0
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }
        if (searchList!!.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = View.VISIBLE
            holder.itemView.img_add.visibility = View.GONE
        } else if (searchList!!.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
        }

        if (searchList!!.get(position).cryptoType.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (searchList!!.get(position).cryptoType.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(searchList!!.get(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: CurrencyPojo.Currency)
        fun onItemUncheck(item: CurrencyPojo.Currency)
        fun onItemClick(item: CurrencyPojo.Currency)
        fun onToggleCheck(item: CurrencyPojo.Currency)
        fun onToggleUncheck(item: CurrencyPojo.Currency)
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.img_add.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }


}