package stock.com.ui.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.bottom_navigation.*
import com.specyci.residemenu.ResideMenu
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.action_bar_notification_icon.view.*
import kotlinx.android.synthetic.main.dashboard_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.comment.activity.CommentActivity
import stock.com.ui.company.CompanyActivity
import stock.com.ui.dashboard.ContestNewBottom.ActivityMyTeam
import stock.com.ui.dashboard.ContestNewBottom.MyContestFragment
import stock.com.ui.dashboard.Lobby.LobbyFragment
import stock.com.ui.dashboard.Market.MarketFragment
import stock.com.ui.dashboard.home.ActivityHomeSearch
import stock.com.ui.dashboard.home.InterfaceHome
import stock.com.ui.dashboard.home.fragment.HomeFragment
import stock.com.ui.dashboard.profile.MessageActivity
import stock.com.ui.dashboard.profile.fragment.ProfileFragment
import stock.com.ui.edit_profile.EditProfileActivity
import stock.com.ui.feedback.FeedBackActivity
import stock.com.ui.friends.FriendsActivity
import stock.com.ui.notification.activity.NotificationActivity
import stock.com.ui.offer_list.OfferListActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.rules_and_scoring.RulesScoringActivity
import stock.com.ui.setting.SettingActivity
import stock.com.ui.share.ShareActivity
import stock.com.ui.social_network.SocialNetworkActivity
import stock.com.ui.support.SupportActivity
import stock.com.ui.wallet.WalletActivity
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class DashBoardActivity : BaseActivity(), View.OnClickListener, ResideMenu.OnMenuListener
    /*, NavigationView.OnNavigationItemSelectedListener */,
    InterfaceHome {

    override fun changeFragment(fragment: Fragment?) {
        val bundle = Bundle()
        bundle.putString("flag", "")
        setFragment(LobbyFragment(), bundle)
        changetTextViewBackground(tv_market, R.color.textColorLightBlack);
        changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
        changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
        changetTextViewBackground(tv_home, R.color.textColorLightBlack);
    }

    var flagcomingScreen: String = "0"
    private var doubleBackToExitPressedOnce = false

    private var resideMenu: ResideMenu? = null

    private var fragment: Fragment? = null;
    override fun onClick(p0: View?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        StockConstant.ACTIVITIES.add(this)
//        if (intent != null)
        setUpMenu();
        initView()

        ll_contest.setOnClickListener {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                toolbar.visibility = View.VISIBLE
                setMenu(false, false, false, false, false, false, false)
                setTitleVisibility(false, true)
                setTitleText(getString(R.string.my_contest))
                //setFragment(MyContestFragment(), Bundle());
                var b = Bundle();
                b.putString("flag", "false")
                setFragment(MyContestFragment(), b);
//                startActivity(Intent(this@DashBoardActivity, MyContestActivity::class.java))
                changetTextViewBackground(tv_contest, R.color.colorPrimary);
                changetTextViewBackground(tv_market, R.color.textColorLightBlack);
                changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
                changetTextViewBackground(tv_home, R.color.textColorLightBlack);
            }
        }
        ll_home.setOnClickListener {
            toolbar.visibility = View.VISIBLE
            setMenu(true, true, false, false, false, false, false)
            setTitleVisibility(false, true)
            setFragment(HomeFragment(), Bundle());
            changetTextViewBackground(tv_home, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
        }
        ll_profile.setOnClickListener {

            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                toolbar.visibility = View.VISIBLE
                setTitleVisibility(false, true)
                setMenu(false, false, false, true, false, false, false)
                setTitleText(getString(R.string.profile))
                setFragment(ProfileFragment(), Bundle())
                changetTextViewBackground(tv_profile, R.color.colorPrimary);
                changetTextViewBackground(tv_market, R.color.textColorLightBlack);
                changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
                changetTextViewBackground(tv_home, R.color.textColorLightBlack);

            }
        }
        ll_market.setOnClickListener {

            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                toolbar.visibility = View.VISIBLE
                setTitleVisibility(false, true)
                setMenu(false, false, false, true, false, false, false)
                setFragment(MarketFragment(), Bundle())

                changetTextViewBackground(tv_market, R.color.colorPrimary);
                changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
                changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
                changetTextViewBackground(tv_home, R.color.textColorLightBlack);
            }
        }
        ll_lobby.setOnClickListener {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                toolbar.visibility = VISIBLE
                setTitleVisibility(false, true)
                setMenu(false, false, false, true, false, false, false)
                val bundle = Bundle()
                bundle.putString("flag", "")
                setFragment(LobbyFragment(), bundle)
                changetTextViewBackground(tv_market, R.color.textColorLightBlack);
                changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
                changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
                changetTextViewBackground(tv_home, R.color.textColorLightBlack);
            }
        }

        if (flagcomingScreen.equals("1")) {
            val bundle = Bundle()
            bundle.putString("flag", "")
            setFragment(LobbyFragment(), bundle)
            setTitleVisibility(false, true)
            setMenu(false, false, false, true, false, false, false)
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);
        }
    }

    private fun changetTextViewBackground(tv: TextView, color: Int) {
        tv.setTextColor(ContextCompat.getColor(applicationContext, color));
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
        // setMenu(true, false, false, false, false, false, false)
        setTitleText(getString(R.string.home))

        changetTextViewBackground(tv_home, R.color.colorPrimary);
        changetTextViewBackground(tv_market, R.color.textColorLightBlack);
        changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
        changetTextViewBackground(tv_contest, R.color.textColorLightBlack);

        setFragment(HomeFragment(), Bundle())

        /*setTitleVisibility(false, true)
        setMenu(true, false, false, false, false, false, false)
        bottomNavigationView!!.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        removeShiftMode(bottomNavigationView)
        setFragment(HomeFragment());
    */



        setMenu(true, true, false, false, false, false, false)
        if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
            //ll_bottom.visibility = View.GONE;
            //img_btn_menu.visibility = GONE;
            //img_btn_menu.visibility = GONE;
            //setMenu(false, false, false, false, false, false, false)
        } else {
            //ll_bottom.visibility = View.VISIBLE;
            //img_btn_menu.visibility = VISIBLE;
            //img_btn_menu.visibility = VISIBLE;
            // setMenu(true, false, false, false, false, false, false)
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
        arrayList.add(resources.getString(R.string.offers))
        // arrayList.add(resources.getString(R.string.invite))
        arrayList.add(resources.getString(R.string.social_network))
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
        arrayList.add(resources.getString(R.string.action_settings))


        var recyclerView_slide = parentLayout.findViewById<RecyclerView>(R.id.recyclerView_slide_menu);
        var img_btn_close = parentLayout.findViewById<AppCompatImageButton>(R.id.img_btn_close);
        var img_btn_eye = parentLayout.findViewById<AppCompatImageButton>(R.id.img_btn_eye);
        var img_my_contest = parentLayout.findViewById<AppCompatImageButton>(R.id.img_my_contest);
        var tv_username = parentLayout.findViewById<AppCompatTextView>(R.id.tv_username);
        var tv_level = parentLayout.findViewById<AppCompatTextView>(R.id.tv_level);
        var tv_pro = parentLayout.findViewById<AppCompatTextView>(R.id.tv_pro);
        var profile_image = parentLayout.findViewById<CircleImageView>(R.id.profile_image);
        var llMessage = parentLayout.findViewById<LinearLayout>(R.id.llMessage);

        var ll_Friends = parentLayout.findViewById<LinearLayout>(R.id.ll_Friends);
        var ll_wallet = parentLayout.findViewById<LinearLayout>(R.id.ll_wallet);
        var llContest = parentLayout.findViewById<LinearLayout>(R.id.llContest);


        ll_wallet.setOnClickListener {

            var intent = Intent(this, WalletActivity::class.java);
            startActivity(intent);
        }

        llContest.setOnClickListener {
            startActivity(Intent(this, ActivityMyTeam::class.java))
        }


        ll_Friends.setOnClickListener {
            // var intent = Intent(this, FriendsActivity::class.java);
            //startActivity(intent);
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                var intent = Intent(this, FriendsActivity::class.java);
                startActivity(intent);
            }

        }

        llMessage.setOnClickListener {
            startActivity(Intent(this, MessageActivity::class.java))
        }

        img_my_contest.setOnClickListener {
            // var intent = Intent(this, StatisticsActivity::class.java);
            // startActivity(intent);
        }


        Glide.with(applicationContext).load(/*StockConstant.IMAG_BASE_PATH + "/" +*/ getUserData().profile_image)
            .error(R.mipmap.ic_launcher).into(profile_image)
        tv_username.setText("Guest user");

        if (!getFromPrefsString(StockConstant.USERID).toString().equals("")) {
            arrayList.add(resources.getString(R.string.logout))
            tv_username.setText(getUserData().username);
            tv_pro.setText(getUserData().level_type);


        }

        //Log.d("profile_image","*----"+getUserData().profile_image);
        val llm = LinearLayoutManager(applicationContext);
        llm.orientation = LinearLayoutManager.VERTICAL;
        recyclerView_slide!!.layoutManager = llm;
        recyclerView_slide!!.adapter = SlideMenuAdapter(applicationContext!!, arrayList, this);


        img_btn_close.setOnClickListener {
            resideMenu!!.closeMenu();
        }

        img_btn_eye.setOnClickListener {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
                resideMenu!!.closeMenu();
            } else {
                startActivity(Intent(this@DashBoardActivity, WatchListActivity::class.java));
                resideMenu!!.closeMenu();
            }
        }

        profile_image.setOnClickListener {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
                resideMenu!!.closeMenu();
            } else {
                startActivity(Intent(this@DashBoardActivity, EditProfileActivity::class.java));
                resideMenu!!.closeMenu();
            }
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

    fun setFragment(fragment: Fragment, bundle: Bundle) {
        this.fragment = fragment;
        fragment.arguments = bundle;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }


    fun setFragmentForActivity() {
        (0 until StockConstant.ACTIVITIES.size)
            .filter { StockConstant.ACTIVITIES[it] != null }
            .forEach { StockConstant.ACTIVITIES[it]?.finish() }
        ll_contest.performClick()
//        setFragment(MyContestFragment(), Bundle())
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
            if (TextUtils.isEmpty(getFromPrefsString(StockConstant.PASSWORD)))
                appLogout()
            finish()
            return
        }
        this.doubleBackToExitPressedOnce = true
        displayToast(resources.getString(R.string.back_exit), "error")
        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun openMenu() {
    }

    override fun closeMenu() {
    }

    fun showDialog1() {
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
            //appLogout();
            logOut();
            dialog.dismiss();
        }
        tv_cancel.setOnClickListener {
            dialog.dismiss();
        }
        dialog.show();
    }

    private fun logOut() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.logOut(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString()
        )
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        appLogout();
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    } else if (response.body()!!.status.equals("0")) {
                        appLogout();
                    } else {
                        displayToast(response.body()!!.message, "warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun setIntent(pos: String) {
        if (pos.equals(resources.getString(R.string.logout))) {
            showDialog1();
        }
        if (pos.equals(resources.getString(R.string.support))) {
            var intent = Intent(this, SupportActivity::class.java);
            startActivity(intent);
        }
        if (pos.equals(resources.getString(R.string.social_network))) {
            var intent = Intent(this, SocialNetworkActivity::class.java);
            startActivity(intent);
        }
        if (pos.equals(resources.getString(R.string.offers))) {
            var intent = Intent(this, OfferListActivity::class.java);
            startActivity(intent);
        }
        if (pos.equals(resources.getString(R.string.rules_winning))) {
            var intent = Intent(this, RulesScoringActivity::class.java);
            startActivity(intent);
        }
        if (pos.equals(resources.getString(R.string.feedback))) {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                var intent = Intent(this, FeedBackActivity::class.java);
                startActivity(intent);
            }
        }
        if (pos.equals(resources.getString(R.string.company))) {
            var intent = Intent(this, CompanyActivity::class.java);
            startActivity(intent);
        }
        if (pos.equals(resources.getString(R.string.action_settings))) {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name),
                    getString(R.string.login_default)
                )
            } else {
                var intent = Intent(this, SettingActivity::class.java);
                startActivity(intent);
            }
        }


        /* if (pos.equals(resources.getString(R.string.how_to_play_))) {
             var intent = Intent(this, StatisticsActivity::class.java);
             startActivity(intent);
         }*/

        /*   if (pos.equals(resources.getString(R.string.how_to_play_))) {
                 var intent = Intent(this, LocationActivity::class.java);
                 startActivity(intent);
           }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((requestCode == 404) && (resultCode == Activity.RESULT_OK)) {
            var b = Bundle();
            b.putString("flag", "true")
            setFragment(MyContestFragment(), b);
            setMenu(false, false, false, false, false, false, false)
            changetTextViewBackground(tv_contest, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);

        } else if ((requestCode == StockConstant.REDIRECT_UPCOMING_MARKET) && (resultCode == Activity.RESULT_OK)) {
            var b = Bundle();
            b.putString("flag", "true")
            setFragment(MyContestFragment(), b);
            setMenu(false, false, false, false, false, false, false)
            changetTextViewBackground(tv_contest, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);

        } else if ((requestCode == StockConstant.REDIRECT_CREATED) && (resultCode == Activity.RESULT_OK)) {
            var b = Bundle();
            b.putString("flag", "")
            setFragment(MyContestFragment(), b);
            setMenu(false, false, false, false, false, false, false)
            changetTextViewBackground(tv_contest, R.color.colorPrimary);
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);

        } else if ((requestCode == 406) && (resultCode == Activity.RESULT_OK)) {
            val bundle = Bundle()
            bundle.putString("flag", "")
            setFragment(LobbyFragment(), bundle)
            setMenu(false, false, false, false, false, false, false)
            changetTextViewBackground(tv_market, R.color.textColorLightBlack);
            changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
            changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
            changetTextViewBackground(tv_home, R.color.textColorLightBlack);
        }
        if (requestCode == StockConstant.RESULT_CODE_HOME_SEARCH) {
            if (resultCode == Activity.RESULT_OK) {
                var id = data!!.getStringExtra("id");
                val bundle = Bundle()
                bundle.putString("flag", "")
                bundle.putString("id", id)
                setFragment(LobbyFragment(), bundle)
                setMenu(false, false, false, false, false, false, false)
                changetTextViewBackground(tv_market, R.color.textColorLightBlack);
                changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
                changetTextViewBackground(tv_contest, R.color.textColorLightBlack);
                changetTextViewBackground(tv_home, R.color.textColorLightBlack);

            }
        }
    }

    public fun test() {
        var b = Bundle();
        b.putString("flag", "true")
        setFragment(MyContestFragment(), b);
        setMenu(false, false, false, false, false, false, false)
        changetTextViewBackground(tv_contest, R.color.colorPrimary);
        changetTextViewBackground(tv_market, R.color.textColorLightBlack);
        changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
        changetTextViewBackground(tv_home, R.color.textColorLightBlack);
    }

    public fun callToCreated() {
         var b = Bundle();
         b.putString("flag", "")
         setFragment(MyContestFragment(), b);
         setMenu(false, false, false, false, false, false, false)
         changetTextViewBackground(tv_contest, R.color.colorPrimary);
         changetTextViewBackground(tv_market, R.color.textColorLightBlack);
         changetTextViewBackground(tv_profile, R.color.textColorLightBlack);
         changetTextViewBackground(tv_home, R.color.textColorLightBlack);
//        displayToast("problem", "sucess")
    }


    var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* set layout of menu*/
        menuInflater.inflate(R.menu.action_menu, menu)
        this.menu = menu
        /* if child activity is product activity then visible filer menu icon*/
        menu.findItem(R.id.menu_filter).isVisible = filter
        menu.findItem(R.id.menu_sort).isVisible = sor_icon
        menu.findItem(R.id.menu_notification).isVisible = notif
//        menu.findItem(R.id.menu_Search).isVisible = search
        menu.findItem(R.id.menu_wallet).isVisible = wallet
        menu.findItem(R.id.menu_edit).isVisible = edit
        menu.findItem(R.id.menu_comment).isVisible = comment
        menu.findItem(R.id.menu_info).isVisible = info_icon
//        menu.findItem(R.id.menu_sort).isVisible = driveActivityName == ProductActivity().javaClass.name
//        if (driveActivityName == ProductActivity().javaClass.name){
//        }
        getViewOfCartMenuItem(menu)
        /* se click listener of toolbar cart icon*/
        notificationView.setOnClickListener {
            if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                AppDelegate.showAlertRegister(
                    this, getResources().getString(R.string.app_name), getString(R.string.login_default)
                )

            } else {
                startActivity(Intent(this, NotificationActivity::class.java))
            }
        }
        return true
    }

    fun setMenu(
        notif: Boolean,
        search: Boolean, wallet: Boolean, filter: Boolean, edit: Boolean, comment: Boolean,
        info_icon: Boolean
    ) {
//        this.search = serach
        this.notif = notif
        this.wallet = wallet
        this.filter = filter
        this.edit = edit
        this.comment = comment
        this.info_icon = info_icon
        this.sor_icon = search
        if (menu != null) {
//            menu!!.findItem(R.id.menu_Search).isVisible = search
            menu!!.findItem(R.id.menu_filter).isVisible = filter
            menu!!.findItem(R.id.menu_sort).isVisible = search
            menu!!.findItem(R.id.menu_notification).isVisible = notif
            menu!!.findItem(R.id.menu_wallet).isVisible = wallet
            menu!!.findItem(R.id.menu_edit).isVisible = edit
            menu!!.findItem(R.id.menu_comment).isVisible = comment
            menu!!.findItem(R.id.menu_info).isVisible = info_icon
        }
    }

    private fun getViewOfCartMenuItem(menu: Menu): View {
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item.itemId == R.id.menu_notification) {
                notificationView = item.actionView
                /* get cart item quantity and set it*/
//                val cartQuantity = pref!!.getStringValue(PrefConstant.KEY_CART_ITEM_COUNT, "")
                val cartQuantity = "1"
                notificationView.notifItemCountTv.text = cartQuantity
                setDynamicallyParam(cartQuantity)
//                if (pref!!.storeIdMatch() || !AppDelegate.isValidString(pref!!.getStringValue(PrefConstant.KEY_STORE_ID, "")))
//                    cartItemView.cartItemCountTv.text = cartQuantity
//                else
//                    cartItemView.cartItemCountTv.text = "X"
                if (cartQuantity == "0" || cartQuantity.isEmpty())
                    notificationView.notifItemCountTv.visibility = View.GONE
                else
                    notificationView.notifItemCountTv.visibility = View.VISIBLE

                return notificationView
            } /*else if (item.itemId == R.id.menu_sort) {
                searchView = item.ac
                return searchView

            }*/
        }
        return notificationView
    }

    /* change height width of cart item count*/
    private fun setDynamicallyParam(cartItemQuantity: String) {
        if (cartItemQuantity.length > 2) {
            notificationView.notifItemCountTv.measure(0, 0)
            val width = notificationView.notifItemCountTv.measuredWidth

            val linearPram = RelativeLayout.LayoutParams(width, width)
            val marginInDp = -5
            val marginInPx = marginInDp * resources.displayMetrics.density
            linearPram.setMargins(marginInPx.toInt(), marginInPx.toInt(), 0, 0)
            linearPram.addRule(RelativeLayout.END_OF, R.id.notif_icon)
            notificationView.notifItemCountTv.layoutParams = linearPram
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> {
            }
//            R.id.menu_notification -> {
////                startActivity(Intent(this,ContestDetailActivity::class.java))
//                return true
//            }
            /*R.id.menu_wallet -> {
                val view = findViewById<View>(R.id.menu_wallet)
                if (walletPopupWindow == null)
                    initWalletPopUp(view)
                else
                    showWalletPopUp(view)
                return true
            }*/
            R.id.menu_sort -> {
                if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                    AppDelegate.showAlertRegister(
                        this, getResources().getString(R.string.app_name), getString(R.string.login_default)
                    )
                } else {
                    startActivityForResult(
                        Intent(this, ActivityHomeSearch::class.java),
                        StockConstant.RESULT_CODE_HOME_SEARCH
                    )
                }
                return true
            }
            R.id.menu_comment -> {
                startActivity(Intent(this, CommentActivity::class.java))
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}