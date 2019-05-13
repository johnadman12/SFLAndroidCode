package stock.com.ui.dashboard.Team.Stock;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R


class StockRatingAdapter  (val mContext: Context) :
            RecyclerView.Adapter<StockRatingAdapter.RatingListHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingListHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_analyst_rating, parent, false)
            return RatingListHolder(view)
        }

        override fun onBindViewHolder(holder: RatingListHolder, position: Int) {

        }


        override fun getItemCount(): Int {
            return 10
        }

        inner class RatingListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }
    }
