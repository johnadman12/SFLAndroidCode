package stock.com.ui.dashboard.home


import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news_list.view.*
import stock.com.R
import stock.com.ui.news.activity.ActivityNewsDetail
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NewsListdapter(
    val mContext: Context,
    val mContest: ArrayList<CityfalconNewsPojo.Story>,
    val identifires: String
) :
    RecyclerView.Adapter<NewsListdapter.FeatureListHolder>(), Filterable {

    private var searchList: ArrayList<CityfalconNewsPojo.Story>? = null

    init {
        this.searchList = mContest
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_list, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        Glide.with(mContext).load(searchList!!.get(position).source!!.imageUrls!!.thumb)
            .into(holder.itemView.imageViewNews)
        holder.itemView.tvNewsTitle.setText(searchList!!.get(position).title)
//        holder.itemView.tvPercentage.setText(mContest.get(position).newspercentage)
        holder.itemView.tvNewsAuthor.setText(searchList!!.get(position).source!!.brandName)
        holder.itemView.tvDescription.setText(searchList!!.get(position).description)
        holder.itemView.tvNewsTime.setText(
            DateUtils.getRelativeTimeSpanString(
                parseDateToddMMyyyy(searchList!!.get(position).publishTime)!!,
                Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS
            )
        )
        holder.itemView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ActivityNewsDetail::class.java)
                    .putExtra(StockConstant.NEWS, mContest.get(position))
            )
        }

    }


    override fun getItemCount(): Int {
        return searchList!!.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun parseDateToddMMyyyy(time: String): Long? {
        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
        val outputPattern: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var dateInMillis: Long = 0
        try {
            val date = outputPattern.parse(time)
            dateInMillis = date.getTime()
            return dateInMillis
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }


    override
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = mContest
                } else {
                    val filteredList = ArrayList<CityfalconNewsPojo.Story>()
                    for (row in mContest) {
                        if (row.title!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                            Log.d("dadada", "---" + filteredList.size);
                        } else if (row.source!!.brandName!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<CityfalconNewsPojo.Story>
                notifyDataSetChanged()
            }
        }
    }

}

