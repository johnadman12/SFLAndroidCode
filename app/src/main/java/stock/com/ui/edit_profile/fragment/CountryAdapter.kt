package stock.com.ui.edit_profile.fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_country_list.view.*
import stock.com.R
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.FilterPojo


class CountryAdapter(val mContext: Context, val mContest: ArrayList<Country.CountryPojo>,val fragment: ContactInfoFragment) : RecyclerView.Adapter<CountryAdapter.Countryclass>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Countryclass {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_country_list, parent, false)
        return Countryclass(view)
    }


    override fun onBindViewHolder(holder: Countryclass, position: Int) {
        val currentItem: Country.CountryPojo = mContest!!.get(position);
        holder.itemView.tvFlagName.setText(currentItem.name)
        Glide.with(mContext).load(currentItem.flagUrl6464).into(holder.itemView.ivFlag)

        holder.itemView.checkCountry.visibility=View.GONE;

        holder.itemView.llCheck.setOnClickListener {

            if(fragment!=null){
                fragment.selectCountry(mContest.get(position).id.toString(),mContest.get(position).name.toString())
            }




        }

       // Log.d("AdapterCountry","*----"+currentItem.name);


    }
    override fun getItemCount(): Int {
        return mContest!!.size
    }

    inner class Countryclass(itemView: View) : RecyclerView.ViewHolder(itemView) {
     }

}