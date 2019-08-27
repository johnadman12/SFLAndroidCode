package stock.com.ui.dashboard.Team.Stock

import android.os.Bundle
import android.text.TextUtils
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.AssestData
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.DecimalFormat
import java.util.*

class DataFragment : BaseFragment() {
    var itemId: Int = 0
    var type: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            itemId = arguments!!.getInt(StockConstant.MARKETID)
            type = arguments!!.getString(StockConstant.MARKET_TYPE)

        }
        getData(itemId.toString())
    }

    fun getData(assestId: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<AssestData> =
            apiService.getAssestData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                assestId, type
            )
        call.enqueue(object : Callback<AssestData> {
            override fun onResponse(call: Call<AssestData>, response: Response<AssestData>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setData(response.body()!!.stock!!.get(0))
                        d.dismiss()

                    } else if (response.body()!!.status == "0") {
                        displayToast(response.body()!!.message, "error")
                    }
                }
            }

            override fun onFailure(call: Call<AssestData>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    private fun setData(list: AssestData.Stock) {
        try {
            tvOpen.setText("$" + list.open)
            tvClose.setText("$" + list.close)
            tvHigh.setText("$" + list.high)
            tvLow.setText("$" + list.low)
            tvAverage.setText("$" + list.average)
            if (list.changePercent.contains("-"))
                tvChange.setTextColor(ContextCompat.getColor(activity!!, R.color.redcolor))
            else
                tvChange.setTextColor(ContextCompat.getColor(activity!!, R.color.green))
            tvChange.setText("$" + list.change)
        } catch (e: Exception) {
        }

        try {
            tvMarketCap.setText("$" + list.marketCap)
            tvVolume.setText("$" + list.latestVolume)
            tvCirculating.setText("$" + list.circulating)
            if (TextUtils.isEmpty(list.maxSupply)) {
                tvmaxSupply.setText("N/A")
            } else {
                tvmaxSupply.setText("$" + list.maxSupply)
            }

        } catch (e: Exception) {
            Log.d("excep", e.message)

        }

        /*try {
            tvMarketCap.setText("$" + formatValue(list.marketCap.toDouble()))
            tvVolume.setText("$" + formatValue(list.latestVolume.toDouble()))
            tvCirculating.setText("$" + formatValue(list.circulating.toDouble()))
            if (TextUtils.isEmpty(list.maxSupply)) {
                tvmaxSupply.setText("N/A")
            } else {
                tvmaxSupply.setText("$" + formatValue(list.maxSupply.toDouble()))
            }

        } catch (e: Exception) {
            Log.d("excep", e.message)
        }*/
        tvRank.setText(list.rank)
        tvCrypto.setText("About " + list.symbol)
        tvAbout.setText(list.cryptodescription)

        if (list.urls != null) {

            if (list.urls!!.websiteUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.websiteUrl!!.get(0))) {
                ll_website.visibility = View.VISIBLE
                tvWebsite.setText(list.urls!!.websiteUrl!!.get(0))
                tvWebsite.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvWebsite, Linkify.WEB_URLS);
            }

            if (list.urls!!.redditUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.redditUrl!!.get(0))) {
                ll_Reddit.visibility = View.VISIBLE
                tvReddit.setText(list.urls!!.redditUrl!!.get(0))
                tvReddit.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvReddit, Linkify.WEB_URLS);
            }

            if (list.urls!!.messageBoardUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.messageBoardUrl!!.get(0))) {
                ll_Message.visibility = View.VISIBLE
                tvMessageBoard.setText(list.urls!!.messageBoardUrl!!.get(0))
                tvMessageBoard.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvMessageBoard, Linkify.WEB_URLS);
            }

            if (list.urls!!.sourceCodeUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.sourceCodeUrl!!.get(0))) {
                ll_Source.visibility = View.VISIBLE
                tvSourceCode.setText(list.urls!!.sourceCodeUrl!!.get(0))
                tvSourceCode.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvSourceCode, Linkify.WEB_URLS);
            }
            if (list.urls!!.explorerUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.explorerUrl!!.get(0))) {
                ll_Explorer.visibility = View.VISIBLE
                tvExplorer.setText(list.urls!!.explorerUrl!!.get(0))
                tvExplorer.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvExplorer, Linkify.WEB_URLS);
            }
            if (list.urls!!.twitterUrl!!.size > 0 && !TextUtils.isEmpty(list.urls!!.twitterUrl!!.get(0))) {
                ll_Twitter.visibility = View.VISIBLE
                tvTwitter.setText(list.urls!!.twitterUrl!!.get(0))
                tvTwitter.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
                Linkify.addLinks(tvTwitter, Linkify.WEB_URLS);
            }
        }
        if (list.cryptologo != null)
            Glide.with(activity!!).load(list.cryptologo).into(logo)
    }

    /*  fun withSuffix(count: Double): String {
          if (count < 1000.0) return "" + count
          val exp: Double = (Math.log(count) / Math.log(1000.0))
          return String.format(
              "%.1f %c",
              count / Math.pow(1000.0, exp),
              "kMBnTPE"[exp.toInt() - 1]
          )
      }*/

    private val SUFFIXES = arrayOf("", "k", "M", "Bn", "T", "P", "E")//kMBTPE

    /* fun coolNumberFormat(count: Double): String {
         if (count < 1000.0)
             return "" + count
         val exp = (Math.log(count) / Math.log(1000.0))
         Log.d("dklddklldldld", exp.toString())
         return String.format(
             "%.3f %s", count / Math.pow(1000.0, exp), SUFFIXES[exp.toInt() - 1]
         )
     }*/

    fun formatValue(value: Double): String {
        var value = value
        val power: Int
        var formattedNumber = "0"
        if (value > 0) {
            val formatter = DecimalFormat("#,###.###")
            power = StrictMath.log10(value).toInt()
            value = value / Math.pow(10.0, (power / 3 * 3).toDouble())
            formattedNumber = formatter.format(value)
            formattedNumber = formattedNumber + " "+SUFFIXES[power / 3]
        }
        return formattedNumber;
        // return if (formattedNumber.length > 4) formattedNumber.replace("\\.[0-9]+".toRegex(), "") else formattedNumber
    }

}