package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_view_contest.*
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.StockPojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant

class StockTeamAdapter(
    val mContext: Context,
    val mContestOld: MutableList<StockTeamPojo.Stock>,
    val mContest: MutableList<StockTeamPojo.Stock>,
    val activity: ActivityCreateTeam,
    val onItemCheckListener: OnItemCheckListener

) :
    RecyclerView.Adapter<StockTeamAdapter.FeatureListHolder>(), Filterable {
    var checkedHolder: BooleanArray? = null;


    private var searchList: List<StockTeamPojo.Stock>? = null
    var list: ArrayList<StockTeamPojo.Stock> = ArrayList()


    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        this.searchList = mContest;
        //Toast.makeText(mContext,""+ searchList!!.size.toString(),1000).show()
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: StockTeamPojo.Stock)
        fun onItemUncheck(item: StockTeamPojo.Stock)
        fun onItemClick(item: StockTeamPojo.Stock)
        fun onToggleCheck(item:StockTeamPojo.Stock)
        fun onToggleUncheck(item: StockTeamPojo.Stock)
    }


    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

        val anim = AlphaAnimation(0.1f, 1.0f)
        anim.duration = 50 //You can manage the blinking time with this parameter
        anim.startOffset = 20
        try {
            if (mContestOld!!.get(position).latestPrice != null)
                if (mContestOld!!.get(position).latestPrice!!.toDouble() < 1)
                    holder.itemView.tvPercentage.setText(
                        "$" + String.format(
                            "%.4f",
                            mContestOld!!.get(position).latestPrice!!.toDouble()
                        )
                    )
                else
                    holder.itemView.tvPercentage.text = "$" + String.format(
                        "%.2f",
                        mContestOld!!.get(position).latestPrice!!.toDouble()
                    )
        } catch (e: Exception) {

        }
        var priceText = ""
        try {
            priceText = mContestOld.get(position).latestPrice!!;
        } catch (e: java.lang.Exception) {
        }

        if (mContestOld.size == mContest.size) {
            if (!TextUtils.isEmpty(priceText)) {
                if (priceText.equals("$" + mContest.get(position).latestPrice)) {
                    holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                } else if (priceText.toDouble() > mContest.get(position).latestPrice!!.toDouble()) {
                    val newtimer = object : CountDownTimer(1000, 500) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.e("timeerror", millisUntilFinished.toString())
                            holder.itemView.tvPercentage.setTextColor(
                                ContextCompat.getColor(mContext, R.color.white)
                            )
                            holder.itemView.llPrice.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    mContext,
                                    R.drawable.gray_green_fill
                                )
                            )
                            holder.itemView.llPrice.animation = anim
                        }

                        override fun onFinish() {
                            try {
                                if (mContest!!.get(position).latestPrice!!.toDouble() < 1)
                                    holder.itemView.tvPercentage.setText(
                                        "$" + String.format(
                                            "%.4f", mContest!!.get(position).latestPrice!!.toDouble()
                                        )
                                    )
                                else
                                    holder.itemView.tvPercentage.setText(
                                        "$" + String.format(
                                            "%.2f",
                                            mContest!!.get(position).latestPrice!!.toDouble()
                                        )
                                    )
                                /*  search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                             search!!.get(position).changeper = cryptoListNew.get(position).changeper*/
                                holder.itemView.tvPercentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                holder.itemView.tv_change_percentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_green_fill
                                    )
                                )
                            } catch (e: Exception) {

                            }

                        }
                    }
                    newtimer.start()
                    holder.itemView.tvPercentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.black
                        )
                    )


                } else if (priceText.toDouble() < mContest.get(position).latestPrice!!.toDouble()) {
                    Log.d("sddasdasdad", "-------3333333--")
                    val newtimer = object : CountDownTimer(500, 500) {
                        override fun onTick(millisUntilFinished: Long) {
                            holder.itemView.tvPercentage.setTextColor(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.white
                                )
                            )
                            holder.itemView.llPrice.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    mContext,
                                    R.drawable.gray_red_fill
                                )
                            )
                            holder.itemView.llPrice.animation = anim
                        }

                        override fun onFinish() {

                            try {
                                if (mContest.get(position).latestPrice!!.toDouble() < 1)
                                    holder.itemView.tvPercentage.setText(
                                        "$" + String.format(
                                            "%.4f",
                                            mContest.get(position).latestPrice!!.toDouble()
                                        )
                                    )
                                else
                                    holder.itemView.tvPercentage.setText(
                                        "$" + String.format(
                                            "%.2f",
                                            mContest!!.get(position).latestPrice!!.toDouble()
                                        )
                                    )
                                /* search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                              search!!.get(position).changeper = cryptoListNew.get(position).changeper*/
                                holder.itemView.tvPercentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                holder.itemView.tv_change_percentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_red_fill
                                    )
                                )
                            } catch (e: Exception) {
                            }

                        }
                    }
                    newtimer.start()
                    holder.itemView.tvPercentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.black
                        )
                    )
                }
            } else {
                try {
                    if (mContest!!.get(position).latestPrice != null)
                        if (mContest!!.get(position).latestPrice!!.toDouble() < 1)
                            holder.itemView.tvPercentage.setText(
                                "$" + String.format(
                                    "%.4f",
                                    mContest!!.get(position).latestPrice!!.toDouble()
                                )
                            )
                        else
                            holder.itemView.tvPercentage.setText(
                                String.format(
                                    "%.2f",
                                    mContest!!.get(position).latestPrice!!.toDouble()
                                )
                            )
                } catch (e: Exception) {

                }
            }

        }



        holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
        holder.itemView.tvCompanyName.setText(searchList!!.get(position).companyName)
        holder.itemView.tvPrevClose.setText(searchList!!.get(position).previousClose)
        holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)


        if (!TextUtils.isEmpty(searchList!!.get(position).changePercent)) {
            var priceText: Double = (searchList!!.get(position).changePercent)!!.toDouble() * 0.01
            var price = (priceText.toString())
            if (searchList!!.get(position).changePercent!!.contains("-")) {
                price = price.substring(0, 1) + "$" + price.substring(4, price.length)
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setText(price + " (" + searchList!!.get(position).changePercent + " %)")
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
//                holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("$" + price + " (+" + searchList!!.get(position).changePercent + " %)")
            }
        }



        holder.itemView.img_add.setOnClickListener {
            if (activity.getTeamText() > 11) {
                AppDelegate.showSneakBarRed(
                    mContext,
                    "You have selected maximum number of stocks for your team.",
                    "DFX"
                )
            } else {
                holder.itemView.llremoveStock.visibility = VISIBLE
                holder.itemView.img_add.visibility = GONE
                searchList!!.get(position).addedToList = 1
                onItemCheckListener.onItemCheck(searchList!!.get(position));
            }
        }
        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
            searchList!!.get(position).addedToList = 0
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }
        if (searchList!!.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
        } else if (searchList!!.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
        }

        if (searchList!!.get(position).stock_type.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (searchList!!.get(position).stock_type.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(searchList!!.get(position))
        }

//        1-sell,0-buy

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                onItemCheckListener.onToggleCheck(searchList!!.get(position))
//                searchList!!.get(position).cryptoType = "0";
            } else {
                onItemCheckListener.onToggleUncheck(searchList!!.get(position))
//                searchList!!.get(position).cryptoType = "1";

            }
        }
    }


    override fun getItemCount(): Int {
        return mContestOld.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.img_add.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }

    override
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = mContest
                } else {
                    val filteredList = ArrayList<StockTeamPojo.Stock>()
                    for (row in mContest) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                            Log.d("dadada", "---" + filteredList.size);
                        } else if (row.companyName!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<StockTeamPojo.Stock>
                notifyDataSetChanged()
            }
        }
    }

}