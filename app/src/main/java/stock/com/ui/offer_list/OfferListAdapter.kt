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


class OfferListAdapter(val mContext: Context) :
    RecyclerView.Adapter<OfferListAdapter.FeatureListHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_offers, parent, false)



        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {


    }


    override fun getItemCount(): Int {
        return 10
    }



    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }



}

