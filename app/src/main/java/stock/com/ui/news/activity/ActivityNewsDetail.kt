package stock.com.ui.news.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ActivityNewsDetail : BaseActivity(), View.OnClickListener {
    var position: Int = 0
    var newsdetail: ArrayList<CityfalconNewsPojo.Story>? = null
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
        newsdetail = ArrayList();
        initViews()
    }

    private fun initViews() {
        if (intent != null) {
            newsdetail = intent.getParcelableArrayListExtra("newspojo")
            position = intent.getIntExtra("clickpos", 0)

        }

        img_btn_back.setOnClickListener(this)
        ivShare.setOnClickListener(this)
        setdata(newsdetail!!.get(position))
    }

    private fun setdata(news: CityfalconNewsPojo.Story) {
        if (news != null) {
            tvNewsText.setText(news.title)
            tvDescription.setText(news.description)
            tvTime.setText(parseDateToddMMyyyy(news.publishTime))
            /*tvNewsEditor.setText(news.source.brandName)

            if (TextUtils.isEmpty(news.source.imageUrls.medium))
                ivNews.visibility = View.GONE
            else
                Glide.with(this).load(news.source.imageUrls.medium).into(ivNews)
            Glide.with(this).load(news.source.imageUrl).into(ivEditor)*/
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

}