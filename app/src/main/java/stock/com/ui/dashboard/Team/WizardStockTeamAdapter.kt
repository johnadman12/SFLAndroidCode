package stock.com.ui.dashboard.Team

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.dialog_join_wizard.*
import kotlinx.android.synthetic.main.dialog_view_contest.*
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.StockPojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class WizardStockTeamAdapter(
    val mContext: Context, val mContest: MutableList<StockTeamPojo.Stock>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<WizardStockTeamAdapter.FeatureListHolder>(), Filterable {
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
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        list = ArrayList()
        list = mContest as ArrayList
        holder.itemView.tvSymbol.setText(searchList!!.get(position).symbol)
        holder.itemView.tvCompanyName.setText(searchList!!.get(position).companyName)
        holder.itemView.tvPrevClose.setText(searchList!!.get(position).previousClose)
        holder.itemView.tvlatestVolume.setText(searchList!!.get(position).latestVolume)
        holder.itemView.tvPercentage.setText(searchList!!.get(position).latestPrice)
        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.ivsTOCK)

        holder.itemView.llremoveStock.visibility = VISIBLE
        holder.itemView.img_add.visibility = GONE
        searchList!!.get(position).addedToList=1

        /* if (searchList!!.get(position).addedStock.equals("yes")) {
             holder.itemView.toggleButton1.isChecked = true
         } else {
             holder.itemView.toggleButton1.isChecked = false
         }
 */
        holder.itemView.img_add.setOnClickListener {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
            searchList!!.get(position).addedToList = 1
            onItemCheckListener.onItemCheck(searchList!!.get(position));
        }
        holder.itemView.llremoveStock.setOnClickListener {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
            searchList!!.get(position).addedToList = 0
            onItemCheckListener.onItemUncheck(searchList!!.get(position));
        }

        //default value of addedlisttostock -> 0
       /* if (searchList!!.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
        } else if (searchList!!.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
        }
*/
        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
//                holder.itemView.toggleButton1.isChecked = false
                searchList!!.get(position).setAddedStock("0")
            } else
//                holder.itemView.toggleButton1.isChecked = true
            searchList!!.get(position).setAddedStock("1");

        }
        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(searchList!!.get(position))
        }

       /* holder.itemView.setOnClickListener {
            showViewContestDialogue()
        }*/
    }


    override fun getItemCount(): Int {
        return searchList!!.size
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
                        }
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

    fun showViewContestDialogue() {
        var dialogue = Dialog(mContext)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_view_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.tvViewContest.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ActivityStockDetail::class.java).putExtra("Stockid", searchList!!.get(position).stockid))
            dialogue.dismiss()
        }
        dialogue.tv_hide.setOnClickListener {
            dialogue.dismiss()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

}