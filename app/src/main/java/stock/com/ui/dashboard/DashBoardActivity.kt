package stock.com.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.bottom_navigation.*
import kotlinx.android.synthetic.main.dashboard_activity.*
import kotlinx.android.synthetic.main.dashboard_fragment.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.home.fragment.HomeFragment
import stock.com.ui.dashboard.more.fragment.MoreFragment
import stock.com.ui.dashboard.myContest.fragment.MyContestFragment
import stock.com.ui.dashboard.profile.fragment.ProfileFragment
import stock.com.utils.StockConstant


class DashBoardActivity : BaseActivity(), View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener/*, NavigationView.OnNavigationItemSelectedListener */ {

    private var doubleBackToExitPressedOnce = false
    override fun onClick(p0: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity);

        initView();

        ll_home.setOnClickListener {
            toolbar.visibility = View.VISIBLE
            setTitleVisibility(false, true)
            setMenu(true, false, false, false, false, false, false)
            setTitleText(getString(R.string.home))

            changetTextViewBackground(tv_home,R.color.colorPrimary);
            changetTextViewBackground(tv_market,R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile,R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest,R.color.textColorLightBlack);

            setFragment(HomeFragment())

        }

        ll_contest.setOnClickListener {
            toolbar.visibility = View.VISIBLE
            //setMenu(true, false, false, false, false, false, false)
            setTitleVisibility(true, false)
            setTitleText(getString(R.string.my_contest))
            setFragment(MyContestFragment());

            changetTextViewBackground(tv_contest,R.color.colorPrimary);
            changetTextViewBackground(tv_market,R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile,R.color.textColorLightBlack);
            changetTextViewBackground(tv_home,R.color.textColorLightBlack);

        }
        ll_profile.setOnClickListener {
            toolbar.visibility = View.GONE
            setTitleVisibility(true, false)
           // setMenu(false, false, false, true, false, false, false)
            setTitleText(getString(R.string.profile))
            setFragment(ProfileFragment())

            changetTextViewBackground(tv_profile,R.color.colorPrimary);
            changetTextViewBackground(tv_market,R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest,R.color.textColorLightBlack);
            changetTextViewBackground(tv_home,R.color.textColorLightBlack);


        }

    }
    private fun changetTextViewBackground(tv:TextView,color:Int){
        tv.setTextColor(ContextCompat.getColor(applicationContext, color));
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                toolbar.visibility = View.VISIBLE
                setTitleVisibility(false, true)
                setMenu(true, false, false, false, false, false, false)
                setTitleText(getString(R.string.home))
                setFragment(HomeFragment())
                return true
            }
            R.id.navigation_mycontest -> {
                toolbar.visibility = View.VISIBLE
                setMenu(true, false, false, false, false, false, false)
                setTitleVisibility(true, false)
                setTitleText(getString(R.string.my_contest))
                setFragment(MyContestFragment())
                return true
            }
            R.id.navigation_profile -> {
                toolbar.visibility = View.GONE
                setTitleVisibility(true, false)
                setMenu(false, false, false, true, false, false, false)
                setTitleText(getString(R.string.profile))
                setFragment(ProfileFragment())
                return true
            }
            R.id.navigation_more -> {
                toolbar.visibility = View.VISIBLE
                setMenu(true, false, false, false, false, false, false)
                setTitleText(getString(R.string.more))
                setFragment(MoreFragment())
                return true
            }
        }
        return false
    }

    @SuppressLint("RestrictedApi")
    private fun removeShiftMode(view: BottomNavigationView) {
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShifting(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field")
        } catch (e: IllegalAccessException) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode")
        }

    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setDisplayShowHomeEnabled(false)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setHomeAsUpIndicator(R.mipmap.menu);


        toolbar.visibility = View.VISIBLE
        setTitleVisibility(false, true)
        setMenu(true, false, false, false, false, false, false)
        setTitleText(getString(R.string.home))

        changetTextViewBackground(tv_home,R.color.colorPrimary);
        changetTextViewBackground(tv_market,R.color.textColorLightBlack);
        changetTextViewBackground(tv_profile,R.color.textColorLightBlack);
        changetTextViewBackground(tv_contest,R.color.textColorLightBlack);

        setFragment(HomeFragment())

        /*setTitleVisibility(false, true)
        setMenu(true, false, false, false, false, false, false)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        removeShiftMode(bottomNavigationView)
        setFragment(HomeFragment());
        */

        if(getFromPrefsString(StockConstant.USERID).toString() == ""){
            ll_bottom.visibility = View.GONE
        }else{
            ll_bottom.visibility = View.VISIBLE
        }

    }

//    fun updateNavigationView() {
//        nav_view.getHeaderView(0).txt_username.setText(Prefs.getInstance(this).userdata!!.first_name + " " + Prefs.getInstance(this).userdata!!.last_name)
//        ImageLoader.getInstance().displayImage(Prefs.getInstance(this).userdata!!.img, nav_view.getHeaderView(0).cimg_user, FantasyApplication.getInstance().options)
//    }

    fun setTitleVisibility(title: Boolean, icon: Boolean) {
        if (icon)
            img_AppIcon.visibility = VISIBLE
        else
            img_AppIcon.visibility = GONE

        if (title)
            toolbarTitle.visibility = VISIBLE
        else
            toolbarTitle.visibility = GONE
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
//        addressTv.text = pref!!.getStringValue(PrefConstant.KEY_SELECTED_STORE_NAME, getString(R.string.home))
//        updateNavigationView()
    }

    /* set title of action bar*/
    fun setTitleText(title: String) {
        toolbarTitle.text = title
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            (0 until StockConstant.ACTIVITIES.size)
                .filter { StockConstant.ACTIVITIES[it] != null }
                .forEach { StockConstant.ACTIVITIES[it]?.finish() }
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        displayToast(resources.getString(R.string.back_exit))
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}
