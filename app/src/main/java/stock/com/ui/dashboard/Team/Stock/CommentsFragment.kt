package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import stock.com.ui.pojo.CommentHashTagPojo
import stock.com.ui.pojo.CommentUserPojo
import stock.com.ui.pojo.Comments
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class CommentsFragment : BaseFragment() {
    var stockId: String = ""
    var type: String = ""
    var list: ArrayList<Comments.Commentlist>? = null
    var mentionAdapter: MentionArrayAdapter<CommentUserPojo.User>? = null
    var hashTagAdapter: HashtagArrayAdapter<CommentHashTagPojo.Market>? = null
    var users: ArrayList<CommentUserPojo.User>? = null
    var hasTag: ArrayList<CommentHashTagPojo.Market>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        users = ArrayList()
        hasTag = ArrayList()
        mentionAdapter = MentionArrayAdapter(activity!!)
        hashTagAdapter = HashtagArrayAdapter(activity!!)

        if (arguments != null) {
            stockId = arguments!!.getString(StockConstant.STOCKID)
            type = arguments!!.getString(StockConstant.CONTEST_TYPE)
        }


        if (type.equals("crypto", true)) {
            getMarketCommentsList()
        } else if (type.equals("stock", true)) {
            getCommentsList()
        } else if (type.equals("currency", true)) {
            getMarketCommentsList()
        }

        btnSend.setOnClickListener {
            if (TextUtils.isEmpty(et_comment.text.toString()))
                displayToast("please enter comment first", "error")
            else {
                if (type.equals("stock", true))
                    postComment(et_comment.text.toString())
                else {
                    postCommentMarket(et_comment.text.toString())
                }
            }
        }

      /*  et_comment.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s != null)
                    if (s.contains("@")) {
                        showMentionUser(s.toString())
                    } else if (s.contains("#")) {
                        showHasTag(s.toString())
                    }
            }
        })*/








        //et_comment.setHashtagEnabled(false)
        et_comment.setMentionTextChangedListener { view, s ->
            Log.d("hjhkjhkhkhj00000","---"+s)
            if (s != null) {
                showMentionUser(s)
            }
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
                    if (response.body()!!.status == "1") {
                        d.dismiss()
                        list = response.body()!!.commentlist
                        setCommentsAdapter(list!!)
                    } else if (response.body()!!.status == "0") {
                        displayToast(response.body()!!.message, "warning")
                    }
                }
            }

            override fun onFailure(call: Call<Comments>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun getMarketCommentsList() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Comments> =
            apiService.getCommentsMArket(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId.toInt(),
                type, getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<Comments> {
            override fun onResponse(call: Call<Comments>, response: Response<Comments>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        d.dismiss()
                        list = response.body()!!.commentlist
                        setCommentsAdapter(list!!)
                    } else if (response.body()!!.status == "0") {
                        displayToast(response.body()!!.message, "warning")
                    }
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


    fun postCommentMarket(textComment: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Comments> =
            apiService.postCommentsMarket(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId.toInt(),
                type,
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
                    et_comment.setText("")
                    setCommentsAdapter(list!!)
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

    fun showMentionUser(search: String) {
        Log.d("MentionUser","---"+search);
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CommentUserPojo> =
            apiService.getAllUsers(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                search
            )
        call.enqueue(object : Callback<CommentUserPojo> {
            override fun onResponse(call: Call<CommentUserPojo>, response: Response<CommentUserPojo>) {
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        users!!.clear()
                        users!!.addAll(response.body()!!.users!!)
                        mentionAdapter!!.clear();
                        mentionAdapter!!.addAll(users!!)
//                        et_comment!!.hashtagAdapter = mentionAdapter;
                        et_comment!!.mentionAdapter = mentionAdapter;
                    }
                }
            }

            override fun onFailure(call: Call<CommentUserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    fun showHasTag(search: String) {

        Log.d("HashTag--","--"+search)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CommentHashTagPojo> =
            apiService.getAllHashTag(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                search
            )
        call.enqueue(object : Callback<CommentHashTagPojo> {
            override fun onResponse(call: Call<CommentHashTagPojo>, response: Response<CommentHashTagPojo>) {
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        hasTag!!.clear()
                        hasTag!!.addAll(response.body()!!.market!!)
                        hashTagAdapter!!.clear();
                        hashTagAdapter!!.addAll(hasTag!!)
                        et_comment.hashtagAdapter= hashTagAdapter






                    }
                }
            }

            override fun onFailure(call: Call<CommentHashTagPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    fun shareIntent(text: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dfx Sharing comments")
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
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