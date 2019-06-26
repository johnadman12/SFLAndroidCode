package stock.com.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_searchbar.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomeSearchPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.JsonElement
import stock.com.ui.pojo.Demo
import org.json.JSONObject
import org.json.JSONArray


class ActivityHomeSearch : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchbar)
        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        imgcross.setOnClickListener {
            et_search.setText("")
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 3)
                    getHomeSearch(s.toString())
            }
        })

    }

    @SuppressLint("WrongConstant")
    private fun setHomeAdapter(data: HomeSearchPojo.Data?) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_home!!.layoutManager = llm
        recycle_home.visibility = View.VISIBLE
        recycle_home!!.adapter = HomeNewsAdapter(this@ActivityHomeSearch, data);
    }


    fun getHomeSearch(search: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<JSONObject> =
            apiService.getHomeSearch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), search

            )
        call.enqueue(object : Callback<JSONObject> {

            override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.getInt("status") == 1) {
                        displayToast("parsed", "error")
                        val obj: JSONObject = response.body()!!.getJSONObject("data")
                        val keys = obj.keys()
                        val mMap = HashMap<String, ArrayList<Demo>>()
                        while (keys.hasNext()) {
                            // here you will get dynamic keys
                            val dynamicKey: String = keys.next();

                            // get the value of the dynamic key
                            val dynamicValue: JSONArray = obj.getJSONArray(dynamicKey);

                            //Let's store into POJO Class and Prepare HashMap.
                            val mCategoryList: ArrayList<Demo>? = null
                            for (i in 0 until dynamicValue.length()) {
                                val demo: Demo? = null
                               /* demo!!.name = dynamicValue.getString("name")
                                demo.id = dynamicValue.getString("id")
                                demo.type = dynamicValue.getString("type")*/
                                demo!!.name = dynamicValue.getString(0)
                                demo.id = dynamicValue.getString(1)
                                demo.type = dynamicValue.getString(2)
                                mCategoryList!!.add(demo);
                            }

                            //Add Into Hashmap
                            mMap.put(dynamicKey, mCategoryList!!);
                            Log.e("obj", mMap.toString())

                        }


                        /* val retMap = Gson().fromJson(obj, object : TypeToken<HashMap<String, Demo>>() {

                         }.type)*/

                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<JSONObject>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }
}