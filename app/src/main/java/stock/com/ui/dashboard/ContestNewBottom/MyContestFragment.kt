package stock.com.ui.dashboard.ContestNewBottom

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_contest.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.home.fragment.HomeFragment
import stock.com.ui.edit_profile.fragment.ContactInfoFragment
import stock.com.ui.my_contest.fragment.CreatedFragment
import stock.com.ui.my_contest.fragment.FinishedFragment
import stock.com.ui.my_contest.fragment.LiveFragment
import stock.com.ui.my_contest.fragment.UpcomingFragment

class MyContestFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.ll_my_team -> {
                startActivity(Intent(activity, ActivityMyTeam::class.java))
            }
            R.id.tv_created -> {
//                ll_my_team.visibility = View.VISIBLE
                changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CreatedFragment(), Bundle())

            }
            R.id.tv_invited -> {

//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));


            }
            R.id.tv_upcoming -> {
//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(UpcomingFragment(), Bundle())



            }
            R.id.tv_live -> {

//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(LiveFragment(), Bundle());


            }
            R.id.tv_finished -> {
//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

                changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.colorbutton));

                setFragment(FinishedFragment(), Bundle());

            }

        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_my_contest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_created.setOnClickListener(this);
        tv_invited.setOnClickListener(this);
        tv_upcoming.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        tv_finished.setOnClickListener(this);
        ll_my_team.setOnClickListener(this);

        val args = arguments;

        if(args!!.getString("flag").equals("true")){

            changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
            changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

            changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.colorbutton));
            changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

            setFragment(UpcomingFragment(), Bundle());

        }else{
            changeTextColor(tv_created, ContextCompat.getColor(activity!!, R.color.white));
            changeTextColor(tv_invited, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_live, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
            changeTextColor(tv_finished, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

            changeBackGroundColor(tv_created, ContextCompat.getColor(activity!!, R.color.colorbutton));
            changeBackGroundColor(tv_invited, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_upcoming, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_live, ContextCompat.getColor(activity!!, R.color.white));
            changeBackGroundColor(tv_finished, ContextCompat.getColor(activity!!, R.color.white));

            setFragment(CreatedFragment(), Bundle());


        }



    }


    private fun setFragment(fragment: Fragment, bundle: Bundle) {
        val fragment: Fragment? = fragment;
        fragment!!.arguments = bundle;
        val fragmentManager: FragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentManager
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }


    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }


}


