package stock.com.ui.edit_profile.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_filter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.FilterPojo
import stock.com.utils.StockDialog

class ContactInfoFragment : BaseFragment(), View.OnClickListener {
    private var countrySelectedItems: ArrayList<String>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initViews()
        countrySelectedItems = ArrayList();
        getCountryList();
    }

    override fun onClick(v: View?) {
    }


    fun getCountryList() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<FilterPojo> =
            apiService.getFilterList()
        call.enqueue(object : Callback<FilterPojo> {

            override fun onResponse(call: Call<FilterPojo>, response: Response<FilterPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        showCountryListDialog(response.body()!!.country)
                        // setMarketAdapter(response.body()!!.market)
                        //setContestAdapter(response.body()!!.category!!)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<FilterPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    public fun showCountryListDialog(country: List<FilterPojo.Country>?) {
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
        recyclerView_country_list!!.layoutManager = llm;
        recyclerView_country_list!!.adapter =
            CountryListAdapter(context!!, country!!, object : CountryListAdapter.OnItemCheckListener {
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

}