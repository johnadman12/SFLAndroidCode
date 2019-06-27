package stock.com.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_searchbar.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import okhttp3.ResponseBody
import stock.com.ui.pojo.Demo
import org.json.JSONObject
import stock.com.ui.pojo.HomeSearchPojo
import java.lang.Exception


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
    private fun setHomeAdapter(data: ArrayList<HomeSearchPojo>) {
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
        val call: Call<ResponseBody> =
            apiService.getHomeSearch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), search

            )
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                d.dismiss()
                if (response.body() != null) {
                    // Log.d("dsdad", "---" + response.body()!!.string())
                    try {
                        var objects: JSONObject = JSONObject(response.body()!!.string());
                        val main = ArrayList<HomeSearchPojo>()
                        val iterator = objects.getJSONObject("data").keys();
                        var homeSearch: HomeSearchPojo? = null
                        while (iterator.hasNext()) {

                            var key = iterator.next();
                            var list = ArrayList<Demo>();
                            var array = objects.getJSONObject("data").getJSONArray(key);
                            Log.d("465464646---", "" + array);
                            if (array.length() != 0) {
                                for (i in 0 until array.length()) {
                                    var jsonObject = array.getJSONObject(i);
                                    var demo = Demo();
                                    demo.name = jsonObject.getString("name")
                                    demo.id = jsonObject.getString("id")
                                    demo.type = jsonObject.getString("type")
                                    list.add(demo);
                                }
                            }
                            homeSearch = HomeSearchPojo()
                            homeSearch!!.title = key
                            homeSearch.users = list!!

                            main.add(homeSearch)
                        }
                        setHomeAdapter(main)
                    } catch (e: Exception) {
                        Log.d("dsdasd", "---" + e.localizedMessage)
                    }
                    /* if (response.body()!!.get("status").asInt == 1) {
                         displayToast("parsed", "error")
                         var obje: JsonObject = response.body()!!.get("data").asJsonObject
                         val categoryJSONObj = JSONObject(obje.toString())
                         val iterator = categoryJSONObj.keys();
                         while (iterator.hasNext()) {
                             var key = iterator.next();
                             var list=ArrayList<Demo>();
                             Log.i("TAG","key:"+key +"--Value::"+categoryJSONObj.optString(key));
                             var array:JsonArray=categoryJSONObj.optString(key);
                             for (i in 0 until  array.length){


                             }
                             //list.add()
                             //map.put(key,categoryJSONObj.optString(key).toString())
                         }


                         setHomeAdapter(map)
                     }
 */

//                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }
}

/*
val obj: JSONObject = response.body()!!.getJSONObject("data")
Log.e("obj", obj.toString())
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
        */
/* demo!!.name = dynamicValue.getString("name")
         demo.id = dynamicValue.getString("id")
         demo.type = dynamicValue.getString("type")*//*

        demo!!.name = dynamicValue.getString(0)
        demo.id = dynamicValue.getString(1)
        demo.type = dynamicValue.getString(2)
        mCategoryList!!.add(demo);
    }

    //Add Into Hashmap
    mMap.put(dynamicKey, mCategoryList!!);
    Log.e("obj", mMap.toString())*/
