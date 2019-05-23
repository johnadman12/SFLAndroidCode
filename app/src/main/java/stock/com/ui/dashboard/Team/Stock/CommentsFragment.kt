package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_comments.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.constant.Tags.response
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.Comments
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList

class CommentsFragment : BaseFragment() {
    var stockId: String = ""
    var list: ArrayList<Comments.Commentlist>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        if (arguments != null)
            stockId = arguments!!.getString("Stockname")

        getCommentsList()
        btnSend.setOnClickListener {
            if (TextUtils.isEmpty(et_comment.text.toString()))
                displayToast("please enter comment first", "error")
            else
                postComment(et_comment.text.toString())
        }
    }


    @SuppressLint("WrongConstant")
    private fun setCommentsAdapter(commentlist: ArrayList<Comments.Commentlist>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvComments!!.layoutManager = llm
        rvComments.visibility = View.VISIBLE
        rvComments!!.adapter = StockCommentsAdapter(context!!, this, commentlist);
    }


    fun getCommentsList() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Comments> =
            apiService.getComments(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId.toInt(), getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    list = response.body()!!.commentlist
                    setCommentsAdapter(list!!)
                }
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun postComment(textComment: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Comments> =
            apiService.postComments(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId.toInt(),
                getFromPrefsString(StockConstant.USERID).toString(),
                textComment
            )
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    AppDelegate.showAlert(activity!!, response.body()!!.message)
                    if (list != null)
                        list!!.clear()
                    list!!.addAll(response.body()!!.commentlist)
//                    list!!.sortByDescending { it.createdAt };
                    setCommentsAdapter(list!!)
                    et_comment.setText("")
                    AppDelegate.hideKeyBoard(activity!!)

                }
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun likeComment(commentId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.likeComment(
                commentId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    displayToast("You liked this post", "sucess")
//                    rvComments.adapter!!.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }

    fun parseDateToddMMyyyy(time: String): Long? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        var dateInMillis: Long = 0
        try {
            val date = outputPattern.parse(time)
            dateInMillis = date.getTime()
            return dateInMillis
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }
}