package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.row_view_featured_contest.view.*
import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.ui.pojo.PriceBreak
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LobbyContestAdapter(
    val mContext: Context,
    val mContest: List<LobbyContestPojo.Contest>
) :
    RecyclerView.Adapter<LobbyContestAdapter.FeatureListHolder>() {
    var SECONDS_IN_A_DAY = 24 * 60 * 60

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_featured_contest, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.entry_fee.setText(mContest.get(position).entryFees)
        holder.itemView.tvStockName.setText(mContest.get(position).exchangename)
        holder.itemView.tvTime.setText(mContest.get(position).exchangename)
//        holder.itemView.tvWinnersTotal.setText(mContest.get(position).totalWinners)
        holder.itemView.tvTotalWinnings.setText(mContest.get(position).winningAmount)
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + mContest.get(position).exchangeimage.trim())
            .into(holder.itemView.ivStock)
        var sports: Double =
            (mContest.get(position).contestSize.toInt() - mContest.get(position).teamsJoined.toInt()).toDouble()
        holder.itemView.tvSprortsLeft.setText(
            sports.toString() + "/" +
                    mContest.get(position).contestSize
        )
        mContest.get(position).setCalculatePosition(sports.toInt())
        holder.itemView.tvTime.setText(parseDateToddMMyyyy(mContest.get(position).scheduleStart))
        if (!mContest.get(position).scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(mContest.get(position).scheduleStart)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            mContest.get(position).setDate(diff.toInt())
            if (diff.toString().contains("-")) {
                holder.itemView.tvTimeLeft.setText("0:0:0: Left")
            } else {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis

                        val diffSec = diff / 1000
                        val seconds = diffSec % 60
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600
                        holder.itemView.tvTimeLeft.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }

        holder.itemView.iv_info.setOnClickListener {
            showInfoDialogue(mContest.get(position).description);
        }
        holder.itemView.circular_progress.isAnimationEnabled
        holder.itemView.circular_progress.setProgress(
            mContest.get(position).teamsJoined.toDouble(),
            mContest.get(position).contestSize.toDouble()
        )
        holder.itemView.circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)

        holder.itemView.llWinners.setOnClickListener {
            val manager = (mContext as AppCompatActivity).supportFragmentManager
            val bottomSheetDialogFragment = BottomSheetWinningListFragment(
                mContest.get(position).priceBreak as ArrayList<PriceBreak>,
                mContest.get(position).winningAmount
            )
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }
        holder.itemView.ll_Circular.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ContestDetailActivity::class.java).putExtra(
                    "contestid",
                    mContest.get(position).contestid
                ).putExtra(
                    StockConstant.EXCHANGEID,
                    mContest.get(position).exchangeid
                )
            )
        }
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = ""
            sb
        }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    fun showInfoDialogue(textView: String) {
        var dialogue = Dialog(mContext)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_information)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.tvInfo.setText(textView)
        dialogue.btnOK.setOnClickListener {
            if (dialogue.isShowing)
                dialogue.dismiss()
        }
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }
}

