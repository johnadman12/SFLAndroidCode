package stock.com.ui.edit_profile.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.Country
import stock.com.utils.StockConstant

class ContactInfoFragment : BaseFragment(), View.OnClickListener {

    private var countrySelectedItems: ArrayList<String>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViews()
        countrySelectedItems = ArrayList();
        val mPrefs: SharedPreferences = activity!!.getPreferences(AppCompatActivity.MODE_PRIVATE);
        val gson = Gson()
        val json = mPrefs.getString(StockConstant.COUNTRYLIST, "false")
        val type = object : TypeToken<ArrayList<Country.CountryPojo>>() {
        }.type
        val topic: ArrayList<Country.CountryPojo> = gson.fromJson(json, type)
//        showCountryListDialog(topic)

        //getCountryList();
    }

    override fun onClick(v: View?) {
    }




    /*@SuppressLint("WrongConstant")
    public fun showCountryListDialog(country:ArrayList<Country.CountryPojo>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_country_list)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        dialog.setCanceledOnTouchOutside(true);

        val recyclerView_country_list = dialog.findViewById(R.id.recyclerView_country_list) as RecyclerView

        val llm = LinearLayoutManager(context);
        llm.orientation = LinearLayoutManager.VERTICAL;
        recyclerView_country_list.layoutManager = llm;
        recyclerView_country_list.adapter =
            CountryListAdapter(context!!, country, object : CountryListAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    countrySelectedItems!!.remove(item);
                }

                override fun onItemCheck(item: String) {
                    countrySelectedItems!!.add(item);
                    Log.e("value", item)
                }
            })

        dialog.show();
    }
*/
}