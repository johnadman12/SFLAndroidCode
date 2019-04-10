package stock.com.ui.edit_profile.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.fragment_contact_info.*
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.FilterPojo
import stock.com.ui.pojo.UserPojo
import stock.com.utils.StockConstant
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
        val mPrefs: SharedPreferences = activity!!.getPreferences(AppCompatActivity.MODE_PRIVATE);
        val gson = Gson()
        val json = mPrefs.getString(StockConstant.COUNTRYLIST, "false")
        val type = object : TypeToken<ArrayList<Country.CountryPojo>>() {
        }.type
        val topic: ArrayList<Country.CountryPojo> = gson.fromJson(json, type)
//        showCountryListDialog(topic)

        //getCountryList();
        getProfile()
    }

    override fun onClick(v: View?) {
    }
    fun getProfile() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserPojo> = apiService.getProfile(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<UserPojo> {
            override fun onResponse(call: Call<UserPojo>, response: Response<UserPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        setData(response.body()!!.user!!)
                        ll_main_contact.visibility=View.VISIBLE;
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
    private fun setData(user: UserPojo.User) {
        et_email.setText(user.email);
        et_number.setText(user.phone_number);
        et_address.setText(user.address);
        et_country.setText(user.country_id);
        et_zip_code.setText(user.zipcode);
    }


}