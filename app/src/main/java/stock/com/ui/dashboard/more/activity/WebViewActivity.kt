package stock.com.ui.dashboard.more.activity


import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_webview.*
import kotlinx.android.synthetic.main.row_latest_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.WebViewPojo
import stock.com.utils.StockDialog

class WebViewActivity : BaseActivity() {

    var slug: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        slug = intent.getStringExtra("PAGE_SLUG")
        initView()
    }
    override fun onBackPressed() {
        finish()
    }
    private fun initView() {
        setSupportActionBar(toolbar)
         //toolbarTitleTv.setText(slug);
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        getData(slug);
    }
    fun getData(type:String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        var call: Call<WebViewPojo>?=null;
        if(type.equals("faq")){
            call= apiService.faq(/*getFromPrefsString(StockConstant.ACCESSTOKEN).toString()*/)
        }else if(type.equals("how-to-play")) {
            call = apiService.howToPlay(/*getFromPrefsString(StockConstant.ACCESSTOKEN).toString()*/)
        }else if(type.equals("rules-and-winnings")) {
            call = apiService.rulesAndWinning(/*getFromPrefsString(StockConstant.ACCESSTOKEN).toString()*/)
        }

        call?.enqueue(object : Callback<WebViewPojo> {
            override fun onResponse(call: Call<WebViewPojo>, response: Response<WebViewPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setData(response.body()!!.cms!!.title!!,response.body()!!.cms!!.description!!)
                        Log.d("sdadadadadad--",""+response.body()!!.cms!!.description)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<WebViewPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }
    private fun setData(title:String,description:String) {
        toolbarTitleTv.setText(title)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_description.setText(Html.fromHtml(title,Html.FROM_HTML_MODE_LEGACY))
        } else {
            tv_description.setText(Html.fromHtml(description))
        }
    }


}
