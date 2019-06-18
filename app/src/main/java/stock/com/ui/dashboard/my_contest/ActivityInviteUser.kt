package stock.com.ui.dashboard.my_contest

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_invite_user.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class ActivityInviteUser : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_Alluser -> {
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.white));

            }
            R.id.tv_friends -> {
                changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.white));
                changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.textColorLightBlack));
                changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.colorbutton));
                changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
            }
            R.id.img_btn_back -> {
                finish()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_user)
        tv_friends.setOnClickListener(this)
        tv_Alluser.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        changeTextColor(tv_Alluser, ContextCompat.getColor(this, R.color.white));
        changeTextColor(tv_friends, ContextCompat.getColor(this, R.color.textColorLightBlack));
        changeBackGroundColor(tv_Alluser, ContextCompat.getColor(this, R.color.colorbutton));
        changeBackGroundColor(tv_friends, ContextCompat.getColor(this, R.color.white));
        setInviteAdapter()
    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }

    @SuppressLint("WrongConstant")
    fun setInviteAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyle_invite_list!!.layoutManager = llm
        recyle_invite_list!!.adapter = InviteUserAdapter(this@ActivityInviteUser)
    }


}