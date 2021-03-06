package stock.com.ui.dashboard.home.MarketList

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate

class MarketListAdapter(
    val mContext: Context, val mContest: MutableList<MarketList.Crypto>,
    var mContestOld: MutableList<MarketList.Crypto>,
    val activity: ActivityMarketTeam, val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<MarketListAdapter.FeatureListHolder>() {


    override fun getItemCount(): Int {
        return mContestOld.size
    }

    var checkedHolder: BooleanArray? = null;


    private var searchList: List<MarketList.Crypto>? = null
//    var list: ArrayList<MarketList.Crypto> = ArrayList()


    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        this.searchList = mContest;
        createCheckedHolder();
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        try {
            holder.itemView.tvSymbol.setText(mContest.get(position).symbol)
            holder.itemView.tvCompanyName.setText(mContest.get(position).name)
        } catch (e: java.lang.Exception) {
        }

        val animBlink: Animation
        animBlink = AnimationUtils.loadAnimation(
            mContext,
            R.anim.blink
        )
        try {
            if (mContestOld.get(position).latestPrice != null)
                if (mContestOld.get(position).latestPrice!!.toDouble() < 1)
                    holder.itemView.tvPercentage.setText(
                        "$" + String.format(
                            "%.6f",
                            mContestOld.get(position).latestPrice!!.toDouble()
                        )
                    )
                else
                    holder.itemView.tvPercentage.text = "$" + String.format(
                        "%.2f",
                        mContestOld.get(position).latestPrice!!.toDouble()
                    )
        } catch (e: Exception) {

        }
        var priceText = ""
        try {
            priceText = mContestOld.get(position).latestPrice!!;
            if (mContestOld.size == mContest.size) {
                if (!TextUtils.isEmpty(priceText)) {
                    if (priceText.equals("$" + mContest.get(position).latestPrice)) {
                        holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    } else if (priceText.toDouble() > mContest.get(position).latestPrice!!.toDouble()) {
                        holder.itemView.llPrice.startAnimation(animBlink);
                        holder.itemView.tvPercentage.startAnimation(animBlink);
                        animBlink.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                holder.itemView.tvPercentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_empty_rect
                                    )
                                )
                            }

                            override fun onAnimationStart(p0: Animation?) {
                                try {
                                    if (mContest.get(position).latestPrice!!.toDouble() < 1)
                                        holder.itemView.tvPercentage.setText(
                                            "$" + String.format(
                                                "%.6f", mContest.get(position).latestPrice!!.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tvPercentage.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                mContest.get(position).latestPrice!!.toDouble()
                                            )
                                        )
                                    holder.itemView.tvPercentage.setTextColor(
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
                        })
                    } else if (priceText.toDouble() < mContest.get(position).latestPrice!!.toDouble()) {
                        holder.itemView.llPrice.startAnimation(animBlink);
                        holder.itemView.tvPercentage.startAnimation(animBlink);
                        animBlink.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationRepeat(p0: Animation?) {

                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                holder.itemView.tvPercentage.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_empty_rect
                                    )
                                )
                            }

                            override fun onAnimationStart(p0: Animation?) {
                                try {
                                    if (mContest.get(position).latestPrice!!.toDouble() < 1)
                                        holder.itemView.tvPercentage.setText(
                                            "$" + String.format(
                                                "%.6f", mContest!!.get(position).latestPrice!!.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tvPercentage.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                mContest!!.get(position).latestPrice!!.toDouble()
                                            )
                                        )
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
                                } catch (e: Exception) {

                                }
                            }
                        })

                    }
                } else {
                    try {
                        if (mContest.get(position).latestPrice != null)
                            if (mContest.get(position).latestPrice!!.toDouble() < 1)
                                holder.itemView.tvPercentage.setText(
                                    "$" + String.format(
                                        "%.6f",
                                        mContest.get(position).latestPrice!!.toDouble()
                                    )
                                )
                            else
                                holder.itemView.tvPercentage.setText(
                                    String.format(
                                        "%.2f",
                                        mContest.get(position).latestPrice!!.toDouble()
                                    )
                                )
                    } catch (e: Exception) {

                    }
                }

            }
        } catch (e: java.lang.Exception) {
        }


        try {
            holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
            holder.itemView.tvCompanyName.setText(searchList!!.get(position).name)
            holder.itemView.market.setText("marketCap :" + searchList!!.get(position).marketcapital)
            holder.itemView.ll_market.visibility = View.VISIBLE
            holder.itemView.prev.visibility = View.GONE
            holder.itemView.img_time.visibility = View.GONE
            holder.itemView.tvPrevClose.visibility = View.GONE
            holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
            Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)


            /*if (searchList!!.get(position).decimalchange!!.contains("-")) {
//                    price = price.substring(0, 1) + "$" + price.substring(4, price.length)
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setText(searchList!!.get(position).decimalchange + " (" + searchList!!.get(position).changeper + "%)")
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("$" + searchList!!.get(position).decimalchange + " (+" + searchList!!.get(position).changeper + "%)")
            }*/
            var price: String = searchList!!.get(position).changeper!!
            if (searchList!!.get(position).changeper!!.contains("-")) {
                price = price.substring(0, 1) + "$" + price.substring(4, price.length)
                holder.itemView.tv_change_percentage.setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.redcolor
                    )
                )
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setText(price + " (" + searchList!!.get(position).changeper + " %)")
                holder.itemView.tv_change_percentage.setText(
                    searchList!!.get(position).decimalchange + " (" + searchList!!.get(
                        position
                    ).changeper + " %)"
                )
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText(
                    "$" + searchList!!.get(position).decimalchange + " (+" + searchList!!.get(
                        position
                    ).changeper + " %)"
                )
            }

        } catch (e: java.lang.Exception) {
        } catch (e: Exception) {

        }


        try {
            holder.itemView.img_add.setOnClickListener {
                if (activity.getTeamText() > 11) {
                    AppDelegate.showSneakBarRed(
                        mContext,
                        "You have selected maximum number of Crypto for your team.",
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

                Log.d("sdadad---", "--" + searchList!!.get(position).changeper)
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
        } catch (e: Exception) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: MarketList.Crypto)
        fun onItemUncheck(item: MarketList.Crypto)
        fun onItemClick(item: MarketList.Crypto)
        fun onToggleCheck(item: MarketList.Crypto)
        fun onToggleUncheck(item: MarketList.Crypto)
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.img_add.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }


}