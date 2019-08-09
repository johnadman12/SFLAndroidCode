package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_forex.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.dashboard.home.Currency.ActivityCurrencyDetail
import stock.com.ui.pojo.CurrencyPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant

class ForexAdapter(
    val mContext: Context,
    val oldData: ArrayList<CurrencyPojo.Currency>,
    val frgament: CurrencyFragment,
    val forexList: ArrayList<CurrencyPojo.Currency>
) :
    RecyclerView.Adapter<ForexAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForexAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_forex, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForexAdapter.FeatureListHolder, position: Int) {
        val animBlink: Animation
        animBlink = AnimationUtils.loadAnimation(
            mContext,
            R.anim.blink
        )
        Glide.with(mContext).load(oldData.get(position).firstflag).into(holder.itemView.img1)
        Glide.with(mContext).load(oldData.get(position).secondflag).into(holder.itemView.img2)

//askcase
        try {
            if (oldData.get(position).ask.equals(forexList!!.get(position).ask)) {
                holder.itemView.ask.setText(forexList.get(position).ask)
            } else if (oldData.get(position).ask!!.toDouble() < forexList.get(position).ask!!.toDouble()) {
                holder.itemView.llPrice.startAnimation(animBlink);
                holder.itemView.ask.startAnimation(animBlink);
                holder.itemView.tv_ask.startAnimation(animBlink);
                animBlink.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        holder.itemView.ask.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.tv_ask.setTextColor(
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
                            if (forexList.get(position).ask!!.toDouble() < 1)
                                holder.itemView.ask.setText(
                                    String.format(
                                        "%.4f",
                                        forexList.get(position).ask!!.toDouble()
                                    )
                                )
                            else
                                holder.itemView.ask.setText(
                                    String.format(
                                        "%.2f",
                                        forexList.get(position).ask!!.toDouble()
                                    )
                                )
                            holder.itemView.ask.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.tv_ask.setTextColor(ContextCompat.getColor(mContext, R.color.white))
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

            } else if (oldData.get(position).ask!!.toDouble() > forexList!!.get(position).ask!!.toDouble()) {
                holder.itemView.llPrice.startAnimation(animBlink);
                holder.itemView.ask.startAnimation(animBlink);
                holder.itemView.tv_ask.startAnimation(animBlink);

                animBlink.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        holder.itemView.ask.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.tv_ask.setTextColor(
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
                            if (oldData!!.get(position).ask!!.toDouble() < 1)
                                holder.itemView.ask.setText(
                                    String.format(
                                        "%.4f",
                                        forexList!!.get(position).ask!!.toDouble()
                                    )
                                )
                            else
                                holder.itemView.ask.setText(
                                    String.format(
                                        "%.2f",
                                        forexList!!.get(position).ask!!.toDouble()
                                    )
                                )
                            holder.itemView.ask.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.tv_ask.setTextColor(ContextCompat.getColor(mContext, R.color.white))
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
            } else {
                Log.e("sddasdasdad", "-------444444444--")
                if (oldData!!.get(position).ask!!.toDouble() < 1) {
                    holder.itemView.ask.setText(String.format("%.4f", forexList!!.get(position).ask!!.toDouble()))
                    Log.e("sddasdasdad", "-------55555555--")
                } else {
                    Log.e("sddasdasdad", "-------66666666--")
                    holder.itemView.ask.setText(String.format("%.2f", forexList!!.get(position).ask!!.toDouble()))
                }
            }
        } catch (ee: Exception) {
        }

//bid case
        try {
            if (oldData.get(position).bid.equals(forexList!!.get(position).bid)) {

                holder.itemView.bid.setText(forexList.get(position).bid)

            } else if (oldData.get(position).bid!!.toDouble() < forexList.get(position).bid!!.toDouble()) {
                holder.itemView.llchange.startAnimation(animBlink);
                holder.itemView.bid.startAnimation(animBlink);
                holder.itemView.tv_bid.startAnimation(animBlink);
                animBlink.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        holder.itemView.bid.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.tv_bid.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.llchange.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                mContext,
                                R.drawable.gray_empty_rect
                            )
                        )
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        try {
                            if (forexList!!.get(position).bid!!.toDouble() < 1)
                                holder.itemView.bid.setText(
                                    String.format(
                                        "%.4f",
                                        forexList!!.get(position).bid!!.toDouble()
                                    )
                                )
                            else
                                holder.itemView.bid.setText(
                                    String.format(
                                        "%.2f",
                                        forexList!!.get(position).bid!!.toDouble()
                                    )
                                )
                            holder.itemView.bid.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.tv_bid.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.llchange.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    mContext,
                                    R.drawable.gray_green_fill
                                )
                            )
                        } catch (e: Exception) {

                        }
                    }
                })

            } else if (oldData.get(position).bid!!.toDouble() > forexList.get(position).bid!!.toDouble()) {
                Log.d("sddasdasdad", "-------3333333--")
                holder.itemView.llchange.startAnimation(animBlink);
                holder.itemView.bid.startAnimation(animBlink);
                holder.itemView.tv_bid.startAnimation(animBlink);
                animBlink.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        holder.itemView.bid.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.tv_bid.setTextColor(
                            ContextCompat.getColor(
                                mContext,
                                R.color.black
                            )
                        )
                        holder.itemView.llchange.setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                mContext,
                                R.drawable.gray_empty_rect
                            )
                        )
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        try {
                            if (oldData!!.get(position).bid!!.toDouble() < 1)
                                holder.itemView.bid.setText(
                                    String.format(
                                        "%.4f",
                                        forexList!!.get(position).bid!!.toDouble()
                                    )
                                )
                            else
                                holder.itemView.bid.setText(
                                    String.format(
                                        "%.2f",
                                        forexList!!.get(position).bid!!.toDouble()
                                    )
                                )
                            holder.itemView.bid.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.tv_bid.setTextColor(ContextCompat.getColor(mContext, R.color.white))
                            holder.itemView.llchange.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    mContext,
                                    R.drawable.gray_red_fill
                                )
                            )
                        } catch (e: Exception) {
                        }
                    }
                })
            } else {
                Log.e("sddasdasdad", "-------444444444--")
                if (oldData!!.get(position).bid!!.toDouble() < 1) {
                    holder.itemView.bid.setText(String.format("%.4f", forexList!!.get(position).bid!!.toDouble()))
                    Log.e("sddasdasdad", "-------55555555--")
                } else {
                    Log.e("sddasdasdad", "-------66666666--")
                    holder.itemView.bid.setText(String.format("%.2f", forexList!!.get(position).bid!!.toDouble()))
                }
            }
        } catch (ee: Exception) {
        }


        //dataset
        try {
            holder.itemView.ask.setText(forexList.get(position).ask)
            holder.itemView.ask.setText(forexList.get(position).bid)

            Log.d("dad_fisrt_image", "---" + oldData.get(position).firstflag);
            Log.d("dad_fisrt_image", "---" + oldData.get(position).secondflag);

            if (!TextUtils.isEmpty(forexList.get(position).changeper)) {
                if (forexList.get(position).changeper!!.contains("-")) {
                    holder.itemView.tv_change_percentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.redcolor
                        )
                    )
                    holder.itemView.tv_change.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                    Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
                    holder.itemView.tv_change_percentage.setText(
                        forexList!!.get(position).changeper + "%" + "  (" + forexList!!.get(
                            position
                        ).daychange + ")"
                    )
                } else {
                    Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
                    holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                    holder.itemView.tv_change.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                    holder.itemView.tv_change_percentage.setText(
                        forexList!!.get(position).changeper + "%" + "  (" + forexList!!.get(
                            position
                        ).daychange + ")"
                    )
                }
            }


            if (oldData.get(position).cryptoType.equals("1")) {
                holder.itemView.llAdd.visibility = View.GONE;
                holder.itemView.img_check.visibility = View.VISIBLE;
                holder.itemView.llwatch.isEnabled = false;
            } else {
                holder.itemView.llAdd.visibility = View.VISIBLE;
                holder.itemView.img_check.visibility = View.GONE;
            }
            holder.itemView.name.setText(forexList.get(position).symbol)
            holder.itemView.tv_company.setText("(" + forexList.get(position).name + ")");
        } catch (e: Exception) {

        }





        holder.itemView.llwatch.setOnClickListener {
            if (forexList.get(position).cryptoType.equals("0")) {
                frgament.saveToWatchList(forexList.get(position).currencyid);
                forexList.get(position).cryptoType = "1";
                holder.itemView.llAdd.visibility = View.GONE;
                holder.itemView.img_check.visibility = View.VISIBLE;
            } else {
                AppDelegate.showAlert(mContext, "Already Added")
            }
        }


        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, ActivityCurrencyDetail::class.java);
            intent.putExtra(StockConstant.CURRENCYID, forexList.get(position).currencyid)
            intent.putExtra(StockConstant.MARKETLIST, forexList)
            intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
            intent.putExtra("flag", 2)
            ActivityCompat.startActivityForResult(mContext as Activity, intent, 410, null);
        }

    }

    override fun getItemCount(): Int {
        return forexList.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}