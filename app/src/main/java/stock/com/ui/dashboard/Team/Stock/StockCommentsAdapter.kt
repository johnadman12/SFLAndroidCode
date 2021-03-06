package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.like.LikeButton
import kotlinx.android.synthetic.main.row_comments.view.*
import stock.com.R
import java.text.ParseException
import java.text.SimpleDateFormat
import com.like.OnLikeListener
import stock.com.ui.dashboard.profile.ActivityOtherUserProfile
import stock.com.ui.pojo.Comments
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.util.*
import kotlin.Unit


class StockCommentsAdapter
    (
    val mContext: Context,
    val commentsFragment: CommentsFragment,
    val commentlist: ArrayList<Comments.Commentlist>
) :
    RecyclerView.Adapter<StockCommentsAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_comments, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.username.setText(commentlist.get(position).username)
        holder.itemView.tv_comments.setText(commentlist.get(position).comments)
        holder.itemView.text_country.setText(commentlist.get(position).country_name)
        holder.itemView.comment_time.setText(
            "- " +
                    DateUtils.getRelativeTimeSpanString(
                        parseDateToddMMyyyy(
                            commentlist.get(
                                position
                            ).createdAt
                        )
                    )
        )
        holder.itemView.tv_like.setText(commentlist.get(position).likescount.toString() + " Likes")
        Glide.with(mContext).load(commentlist.get(position).profileImage).into(holder.itemView.iv_user)

        /* holder.itemView.ll_like.setOnClickListener {
             holder.itemView.star_button.performClick()
         }*/
        holder.itemView.ll_share.setOnClickListener {
            commentsFragment.shareIntent(commentlist.get(position).comments)
        }

        if (commentlist.get(position).likesstatus.equals("0"))
            holder.itemView.star_button.isLiked = false
        else
            holder.itemView.star_button.isLiked = true


        holder.itemView.star_button.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                commentsFragment.likeComment(commentlist.get(position).stockCommentId)
                commentlist.get(position).likescount = commentlist.get(position).likescount + 1
                commentlist.get(position).likesstatus = "1"
                notifyDataSetChanged()
            }

            override fun unLiked(likeButton: LikeButton) {
                commentsFragment.likeComment(commentlist.get(position).stockCommentId)
                commentlist.get(position).likescount = commentlist.get(position).likescount - 1
                commentlist.get(position).likesstatus = "0"
                notifyDataSetChanged()

            }
        })

        holder.itemView.tv_comments.setOnMentionClickListener({ view, s ->
          /*  mContext.startActivity(
                Intent(mContext, ActivityOtherUserProfile::class.java).putExtra(
                    StockConstant.FRIENDID, "0"
                ).putExtra(
                    StockConstant.USERNAME, view.text
                )
            )*/

        })

    }


    override fun getItemCount(): Int {
        return commentlist.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun parseDateToddMMyyyy(time: String): Long {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
//        val outputPattern = "dd MMM HH:mm:ss "
        val outputPattern: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
//        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
//        val outputFormat = SimpleDateFormat(outputPattern)
        var timeZone: String = Calendar.getInstance().getTimeZone().getID();
        var date: Date? = null
        var str: String? = null
        var dateInMillis: Long = 0
        try {
            date = inputFormat.parse(time)
            str = outputPattern.format(date.time + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()))
            val date1 = outputPattern.parse(str)
            dateInMillis = date1.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dateInMillis
    }

}