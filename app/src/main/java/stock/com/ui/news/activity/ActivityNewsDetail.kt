package stock.com.ui.news.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ActivityNewsDetail : BaseActivity(), View.OnClickListener {
    var uuid: String = ""
    var identifires: String = ""
    var newsdetail: CityfalconNewsPojo.Story? = null
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.ivShare -> {
                shareIntent()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        if (intent != null) {
            newsdetail = intent.getSerializableExtra(StockConstant.NEWS) as CityfalconNewsPojo.Story?
        }
        img_btn_back.setOnClickListener(this)
        ivShare.setOnClickListener(this)
        setdata(newsdetail!!)
//        getNewslist()

    }

    private fun setdata(news: CityfalconNewsPojo.Story) {
            tvNewsText.setText(news.title)
            tvDescription.setText(news.description)
            tvTime.setText(parseDateToddMMyyyy(news.publishTime))
            tvNewsEditor.setText(news.source.brandName)

            if (TextUtils.isEmpty(news.source.imageUrls.large))
                ivNews.visibility = View.GONE
            else
                Glide.with(this).load(news.source.imageUrls.large).into(ivNews)
            Glide.with(this).load(news.source.imageUrl).into(ivEditor)

    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, tvDescription.text.toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    override fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    val categories: String = "mp,op"

   /* fun getNewslist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientNews()!!.create(ApiInterface::class.java)
        val call: Call<CityfalconNewsPojo> =
            apiService.getNewsHome(
                "tickers",
                identifires, categories, "20",
                "top", "d1", false,
                StockConstant.NEWS_ACCESS_TOKEN
            )
        call.enqueue(object : Callback<CityfalconNewsPojo> {
            override fun onResponse(call: Call<CityfalconNewsPojo>, response: Response<CityfalconNewsPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    for (i in 0 until response.body()!!.stories.size) {
                        if (uuid.equals(response.body()!!.stories.get(i).uuid))
                            setdata(response.body()!!.stories.get(i))
                    }
                }
            }

            override fun onFailure(call: Call<CityfalconNewsPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }

        })
    }*/

}