package stock.com.ui.my_contest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_contest.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.home.fragment.HomeFragment
import stock.com.ui.edit_profile.fragment.ContactInfoFragment
import stock.com.ui.my_contest.fragment.CreatedFragment
import stock.com.ui.my_contest.fragment.FinishedFragment
import stock.com.ui.my_contest.fragment.LiveFragment

class MyContestActivity : BaseActivity(), View.OnClickListener {


    private var fragment: Fragment? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_contest)


        tv_created.setOnClickListener(this);
        tv_invited.setOnClickListener(this);
        tv_upcoming.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        tv_finished.setOnClickListener(this);


        changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
        changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
        changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
        changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
        changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));

        changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.colorbutton));
        changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
        changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
        changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
        changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));


        setFragment(CreatedFragment(), Bundle())

        img_btn_back.setOnClickListener {
            onBackPressed();
        }


    }

    private fun setFragment(fragment: Fragment, bundle: Bundle) {
        this.fragment = fragment;
        fragment.arguments = bundle;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_created -> {
                changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
                changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.colorbutton));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));

                setFragment(CreatedFragment(), Bundle())

            }
            R.id.tv_invited -> {


                changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
                changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.colorbutton));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));


            }
            R.id.tv_upcoming -> {


                changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
                changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.colorbutton));
                changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));


            }
            R.id.tv_live -> {


                changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
                changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.colorbutton));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));

                setFragment(LiveFragment(), Bundle());


            }
            R.id.tv_finished -> {

                changeTextColor(tv_created, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(applicationContext, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.white));

                changeBackGroundColor(tv_created, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(applicationContext, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(applicationContext, R.color.colorbutton));

                setFragment(FinishedFragment(), Bundle());


            }


        }
    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }


}