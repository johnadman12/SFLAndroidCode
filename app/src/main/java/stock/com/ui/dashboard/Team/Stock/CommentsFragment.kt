package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.linkedin.android.spyglass.suggestions.SuggestionsResult
import com.linkedin.android.spyglass.suggestions.impl.BasicSuggestionsListBuilder
import com.linkedin.android.spyglass.suggestions.interfaces.Suggestible
import com.linkedin.android.spyglass.tokenization.QueryToken
import com.linkedin.android.spyglass.tokenization.interfaces.QueryTokenReceiver
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.next_button_dailog.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
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
import kotlin.collections.ArrayList


class CommentsFragment : BaseFragment(), QueryTokenReceiver {

    var stockId: String = ""
    var type: String = ""
    private var lastPersonSuggestions: SuggestionsResult? = null
    private var lastHashTagSuggestions: SuggestionsResult? = null

    var list: ArrayList<Comments.Commentlist>? = null
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

        et_comment.setQueryTokenReceiver(this);
        et_comment.setSuggestionsListBuilder(CustomSuggestionsListBuilder())

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

    private inner class CustomSuggestionsListBuilder : BasicSuggestionsListBuilder() {
        @NonNull
        override fun getView(
            @NonNull suggestion: Suggestible,
            @Nullable convertView: View?,
            parent: ViewGroup,
            @NonNull context: Context,
            @NonNull inflater: LayoutInflater,
            @NonNull resources: Resources
        ): View {

            val v = super.getView(suggestion, convertView, parent, context, inflater, resources)
            if (v !is TextView) {
                return v
            }

            // Color text depending on the type of mention
            if (suggestion is CommentUserPojo.User) {
                v.setTextColor(getResources().getColor(R.color.blue))
            } else if (suggestion is CommentHashTagPojo.Market) {
                v.setTextColor(getResources().getColor(R.color.blue))
            }
            return v
        }
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

    fun showMentionUser(search: String): ArrayList<String> {
        var tempList = ArrayList<String>()
        Log.d("MentionUser", "---" + search);
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
                       /* for (i in 0 until users!!.size) {
                            tempList!!.add("sdadad")
                        }*/

                    }
                }
            }

            override fun onFailure(call: Call<CommentUserPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
        return tempList
    }

    fun showHasTag(search: String): ArrayList<String> {

        var tempList = ArrayList<String>()

        Log.d("HashTag--", "--" + search)
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

                    }
                }
            }

            override fun onFailure(call: Call<CommentHashTagPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })

        return tempList;
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

    override fun onQueryReceived(queryToken: QueryToken): MutableList<String> {

        Log.d("sadasdadada", "---" + queryToken.toString());
        Log.d("sadasdadada", "---" + queryToken.tokenString);
        var buckets = ArrayList<String>()

        val listener = et_comment

        if (queryToken.tokenString.contains("@")) {
            buckets = showMentionUser(queryToken.tokenString)
            lastPersonSuggestions = SuggestionsResult(queryToken, users!!)
            listener.onReceiveSuggestionsResult(lastPersonSuggestions!!, "")
        }
        else if (queryToken.tokenString.contains("#")) {
            buckets = showHasTag(queryToken.tokenString)
            lastHashTagSuggestions = SuggestionsResult(queryToken, hasTag!!)
            listener.onReceiveSuggestionsResult(lastHashTagSuggestions!!, "")
        }
        return buckets
    }

}