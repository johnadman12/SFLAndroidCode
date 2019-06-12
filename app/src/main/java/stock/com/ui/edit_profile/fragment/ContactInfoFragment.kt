package stock.com.ui.edit_profile.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.content_login.*
import kotlinx.android.synthetic.main.content_otp.*
import kotlinx.android.synthetic.main.fragment_contact_info.*
import kotlinx.android.synthetic.main.fragment_contact_info.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.FilterPojo
import stock.com.ui.pojo.UserPojo
import stock.com.ui.signup.activity.OTPActivity
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.networkUtils.NetworkUtils

class ContactInfoFragment : BaseFragment(), View.OnClickListener {

    private var country: Country? = null;

    private var dialog: Dialog? = null;

    private var countryId: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_email_address.isEnabled = false;

        country = Gson().fromJson(
            SessionManager.getInstance(context).getString(StockConstant.COUNTRYLIST),
            Country::class.java
        );

        view.tv_apply.setOnClickListener(this);
        view.tv_cancel.setOnClickListener(this);
        view.et_country.setOnClickListener(this);

        getProfile()


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_cancel -> {
                activity!!.finish();
            }
            R.id.et_country -> {
                showCountryList();
            }
            R.id.tv_apply -> {
                if (et_number.text.toString().equals(""))
                    displayToast(resources.getString(R.string.mob_error), "warning");
                else if (et_address.text.toString().equals(""))
                    displayToast(resources.getString(R.string.address_error), "warning");
                else if (et_country.text.toString().equals("")) {
                    displayToast(resources.getString(R.string.country_error),"warning");
                } else if (et_zip_code.text.toString().equals("")) {
                    displayToast(resources.getString(R.string.zip_coder_error), "warning");
                } else {
                    if (NetworkUtils.isConnected()) {
                        callApiUpdateProfile();
                    } else {
                        displayToast(resources.getString(R.string.error_network_connection),"error")
                    }
                }
            }
        }
    }

    fun callApiUpdateProfile() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.updateProfileDetails(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString(),
            et_address.text.toString(),
            et_zip_code.text.toString(),
            et_number.text.toString(),
            countryId
        )
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        //setData(response.body()!!.user!!)
                        //ll_main_contact.visibility = View.VISIBLE;
                        if (!response.body()!!.otp.equals("")) {
                            var intent = Intent(context, OTPActivity::class.java);
                           // intent.putExtra("otp", response.body()!!.otp);
                            intent.putExtra("isReset", "profile");
                            intent.putExtra("phoneNumber", et_number.text.toString().trim());
                            context!!.startActivity(intent)
                            activity!!.finish();
                        } else {
                            displayToast(response.body()!!.message, "warning")
                        }
                    }else if(response.body()!!.status.equals("2")){
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })

    }

    fun getProfile() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserPojo> = apiService.getProfile(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString()
        )
        call.enqueue(object : Callback<UserPojo> {
            override fun onResponse(call: Call<UserPojo>, response: Response<UserPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        setData(response.body()!!.user!!)
                        ll_main_contact.visibility = View.VISIBLE;
                    }else if (response.body()!!.status.equals("2")){
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

    private fun setData(user: UserPojo.User) {
        et_email_address.setText(user.email);
        et_number.setText(user.phone_number);
        et_address.setText(user.address);
        // et_country.setText(user.country_id);
        et_zip_code.setText(user.zipcode);

        countryId = user.country_id;

        for (item in country!!.country!!) {
            Log.d("sadadadada+---", "sddad" + item.id)
            Log.d("sadadadada+---", "sddad" + countryId)
            if (item.id.toString() == countryId) {
                et_country.setText(item.name);
                break;
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun showCountryList() {
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.dialog_select)
        // dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.setGravity(Gravity.CENTER)
        dialog!!.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        val window = dialog!!.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        dialog!!.setCanceledOnTouchOutside(true);

        var recylerView = dialog!!.findViewById(R.id.recylerView) as RecyclerView
        var ciiqer_text = dialog!!.findViewById(R.id.ciiqer_text) as TextView
        recylerView.visibility = View.VISIBLE;

        // ciiqer_text.setText(resources.getString(R.string.select_country))

        // displayToast(country!!.country!!.size.toString())

        var adapter1 = CountryAdapter(activity!!, country!!.country!!, this)
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recylerView!!.layoutManager = llm
        recylerView.adapter = adapter1;


        dialog!!.show();
    }

    public fun selectCountry(id: String, name: String) {
        dialog!!.dismiss()
        // displayToast("id+-"+id+"--name-"+name)
        et_country.setText(name);
        countryId = id;

    }


}