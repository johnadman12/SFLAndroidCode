package stock.com.ui.news.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
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
import stock.com.ui.pojo.FilterPojo
import stock.com.ui.pojo.NewsDetailPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ActivityNewsDetail : BaseActivity(), View.OnClickListener {
    var newsId: Int = 0
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
            newsId = intent.getIntExtra("newsId",0)
        }
        img_btn_back.setOnClickListener(this)
        ivShare.setOnClickListener(this)
        getNewsdetail()
    }

    fun getNewsdetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<NewsDetailPojo> =
            apiService.getNewsDetail(newsId.toString())
        call.enqueue(object : Callback<NewsDetailPojo> {

            override fun onResponse(call: Call<NewsDetailPojo>, response: Response<NewsDetailPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setdata(response.body()!!.news)
                    }
                } else {
                    Toast.makeText(
                        this@ActivityNewsDetail,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<NewsDetailPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityNewsDetail,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                d.dismiss()
            }
        })
    }

    private fun setdata(news: NewsDetailPojo.News?) {
        if (news != null) {
            tvNewsText.setText(news.title)
            tvNewsEditor.setText(news.channel)
            tvTime.setText(parseDateToddMMyyyy(news.newstime))
            tvDescription.setText(news.description)
            Glide.with(this).load(news.image).into(ivNews)
            Glide.with(this).load(news.channel_image).into(ivEditor)
        }
    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, tvDescription.text.toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    override fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
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

}