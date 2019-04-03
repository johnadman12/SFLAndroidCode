package stock.com.ui.dashboard

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.bottom_navigation.*
import com.specyci.residemenu.ResideMenu
import kotlinx.android.synthetic.main.dashboard_activity.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.left_menu.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.home.adapter.MatchLiveAdapter
import stock.com.ui.dashboard.home.fragment.HomeFragment
import stock.com.ui.dashboard.more.fragment.MoreFragment
import stock.com.ui.dashboard.myContest.fragment.MyContestFragment
import stock.com.ui.dashboard.profile.fragment.ProfileFragment
import stock.com.ui.pojo.SignupDataPojo
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.StockConstant

class DashBoardActivity : BaseActivity(), View.OnClickListener, ResideMenu.OnMenuListener,
    BottomNavigationView.OnNavigationItemSelectedListener/*, NavigationView.OnNavigationItemSelectedListener */ {


    private var doubleBackToExitPressedOnce = false

    private var resideMenu: ResideMenu? = null

    override fun onClick(p0: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        setUpMenu();
        initView()
        ll_contest.setOnClickListener {
            toolbar.visibility = View.VISIBLE
            //setMenu(true, false, false, false, false, false, false)
            setTitleVisibility(false, true)
            setTitleText(getString(R.string.my_contest))
            setFragment(MyContestFragment());

            changetTextViewBackground(tv_contest, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);

        }
        ll_home.setOnClickListener {
            toolbar.visibility = View.VISIBLE
            //setMenu(true, false, false, false, false, false, false)
            setTitleVisibility(false, true)
            setFragment(HomeFragment());
            changetTextViewBackground(tv_home, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);

        }
        ll_profile.setOnClickListener {
            toolbar.visibility = View.GONE
            setTitleVisibility(true, false)
            // setMenu(false, false, false, true, false, false, false)
            setTitleText(getString(R.string.profile))
            setFragment(ProfileFragment())

            changetTextViewBackground(tv_profile, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);


        }
        ll_market.setOnClickListener {
            toolbar.visibility = View.GONE
            setTitleVisibility(false, true)
            // setMenu(false, false, false, true, false, false, false)
            setFragment(ProfileFragment())

            changetTextViewBackground(tv_market, R.color.colorPrimary);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);


        }
        ll_lobby.setOnClickListener {
            toolbar.visibility = VISIBLE
            setTitleVisibility(true, false)
            // setMenu(false, false, false, true, false, false, false)
            setFragment(LobbyFragment())
        }

    }

    private fun changetTextViewBackground(tv: TextView, color: Int) {
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

        changetTextViewBackground(tv_home, R.color.colorPrimary);
        changetTextViewBackground(tv_market, R.color.textColorLightBlack);
        changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
        changetTextViewBackground(tv_contest, R.color.textColorLightBlack);

        setFragment(HomeFragment())

        /*setTitleVisibility(false, true)
    setMenu(true, false, false, false, false, false, false)
    bottomNavigationView!!.setOnNavigationItemSelectedListener(this)
    bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    removeShiftMode(bottomNavigationView)
    setFragment(HomeFragment());
    */

        if (getFromPrefsString(StockConstant.USERID).toString() == "") {
            ll_bottom.visibility = View.GONE
        } else {
            ll_bottom.visibility = View.VISIBLE
        }


        img_btn_menu.setOnClickListener {
            resideMenu!!.openMenu(ResideMenu.DIRECTION_LEFT);

        }


    }

    @SuppressLint("WrongConstant")
    private fun setUpMenu() {

        resideMenu = ResideMenu(this, R.layout.left_menu, R.layout.left_menu);
        resideMenu?.setBackground(R.color.menu_bg);
        resideMenu!!.attachToActivity(this);
        resideMenu!!.setMenuListener(this);
        //resideMenu.setScaleValue(0.5f);
        resideMenu!!.setScaleValue(0.45f);

        resideMenu!!.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
        resideMenu!!.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);


        var parentLayout: LinearLayout;
        parentLayout = resideMenu!!.getLeftMenuView().findViewById(R.id.ll_left_menu_parent) as LinearLayout




            val arrayList = ArrayList<String>()//Creating an empty arraylist
            arrayList.add(resources.getString(R.string.support))//Adding object in arraylist
            arrayList.add(resources.getString(R.string.how_to_play_))
            arrayList.add(resources.getString(R.string.legality))
            arrayList.add(resources.getString(R.string.fair_play_commitment))
            arrayList.add(resources.getString(R.string.trust))
            arrayList.add(resources.getString(R.string.saifty))
            arrayList.add(resources.getString(R.string.licenses))
            arrayList.add(resources.getString(R.string.rules_winning))
            arrayList.add(resources.getString(R.string.new_and_press))
            arrayList.add(resources.getString(R.string.careers))
            arrayList.add(resources.getString(R.string.membership))
            arrayList.add(resources.getString(R.string.feedback))
            arrayList.add(resources.getString(R.string.company))
            arrayList.add(resources.getString(R.string.logout))

            var recyclerView_slide = parentLayout.findViewById<RecyclerView>(R.id.recyclerView_slide_menu);
            var img_btn_close = parentLayout.findViewById<AppCompatImageButton>(R.id.img_btn_close);
            var img_btn_eye = parentLayout.findViewById<AppCompatImageButton>(R.id.img_btn_eye);
            var tv_username = parentLayout.findViewById<AppCompatTextView>(R.id.tv_username);
            var tv_level = parentLayout.findViewById<AppCompatTextView>(R.id.tv_level);
            var tv_pro = parentLayout.findViewById<AppCompatTextView>(R.id.tv_pro);


            tv_username.setText(getUserData().username);

            val llm = LinearLayoutManager(applicationContext);
            llm.orientation = LinearLayoutManager.VERTICAL;
            recyclerView_slide!!.layoutManager = llm;
            recyclerView_slide!!.adapter = SlideMenuAdapter(applicationContext!!, arrayList,this);


            img_btn_close.setOnClickListener {
                resideMenu!!.closeMenu();
            }
            img_btn_eye.setOnClickListener {
                 startActivity(Intent(this@DashBoardActivity, WatchListActivity::class.java));
                 resideMenu!!.closeMenu();
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
    override fun openMenu() {
    }
    override fun closeMenu() {
    }
        public fun showDialog1() {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_logout)
            dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.CENTER)
            dialog.setCancelable(true)
            val lp = WindowManager.LayoutParams()
            val window = dialog.window
            lp.copyFrom(window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
            dialog.setCanceledOnTouchOutside(true);

            val tv_yes = dialog.findViewById(R.id.tv_yes) as TextView
            val tv_cancel = dialog.findViewById(R.id.tv_cancel) as TextView

            tv_yes.setOnClickListener {
                appLogout();
                dialog.dismiss();
            }
            tv_cancel.setOnClickListener {
                dialog.dismiss();
            }
            dialog.show();
        }
    }