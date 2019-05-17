package stock.com.ui.social_network

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_social_network.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.SocialLinkPojo
import stock.com.ui.pojo.TrainingPojo
import stock.com.utils.StockDialog

class SocialNetworkActivity : BaseActivity(), View.OnClickListener {


    private var fbUrl:String="";
    private var intsUrl:String="";
    private var ytUrl:String="";
    private var twUrl:String="";
    private var snapchatUrl:String="";
    private var webUrl:String="";


    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.img_fb -> {
              if(fbUrl.equals(""))
                    return;
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(fbUrl)
                startActivity(openURL)
            }

            R.id.img_twitter -> {
                if(twUrl.equals(""))
                    return;
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(twUrl)
                startActivity(openURL)
            }

            R.id.img_yt -> {
                if(ytUrl.equals(""))
                    return;
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(ytUrl)
                startActivity(openURL)
            }

            R.id.img_inst -> {
                if(intsUrl.equals(""))
                    return;
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(intsUrl)
                startActivity(openURL)
            }

            R.id.img_snapcht -> {
                if(snapchatUrl.equals(""))
                    return;
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(snapchatUrl)
                startActivity(openURL)
            }

            R.id.img_website -> {

            }


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_network)

        getSocialLink()

        img_fb.setOnClickListener(this);
        img_twitter.setOnClickListener(this);
        img_yt.setOnClickListener(this);
        img_inst.setOnClickListener(this);
        img_snapcht.setOnClickListener(this);
        img_website.setOnClickListener(this);



        img_btn_back.setOnClickListener {
            onBackPressed()
        }


    }

    fun getSocialLink() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SocialLinkPojo> =
            apiService.getSocialLink()
        call.enqueue(object : Callback<SocialLinkPojo> {
            override fun onResponse(call: Call<SocialLinkPojo>, response: Response<SocialLinkPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        ll_main.visibility = View.VISIBLE;
                        //displayToast(response!!.body()!!.socialLinksList!!.size.toString())
                       for (item in response!!.body()!!.socialLinksList!!) {
                           Log.d("sdaadadad --","--"+item.value)
                            if(item.name.equals("facebook_url")){
                                fbUrl=item.value;
                            }
                            if(item.name.equals("twitter_url")){
                                twUrl=item.value;
                            }
                            if(item.name.equals("youtube_url")){
                                ytUrl=item.value;
                            }
                            if(item.name.equals("instagram_url")){
                                intsUrl=item.value;
                            }
                            if(item.name.equals("snapchat_url")){
                                snapchatUrl=item.value;
                            }
                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SocialLinkPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

}
