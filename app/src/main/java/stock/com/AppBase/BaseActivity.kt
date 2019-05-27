package stock.com.AppBase

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.irozon.sneaker.Sneaker
import kotlinx.android.synthetic.main.action_bar_notification_icon.view.*
import kotlinx.android.synthetic.main.dialogue_join_contest.*
import kotlinx.android.synthetic.main.dialogue_wallet.view.*
import stock.com.R
import stock.com.data.Prefs
import stock.com.ui.comment.activity.CommentActivity
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.dashboard.home.ActivityNewsSearch
import stock.com.ui.notification.activity.NotificationActivity
import stock.com.ui.pojo.SignupDataPojo
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {
    lateinit var notificationView: View
    private var progressDialog: ProgressDialog? = null
    var pref: Prefs? = null
    var notif = false
    var wallet = false
    var filter = false
    var edit = false
    var comment = false
    var info_icon = false
    var sor_icon = false
    var search = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0);
        pref = Prefs(this)

    }


    /*fun setFragment(fragment: Fragment, bundle: Bundle) {
//        this.fragment = fragment;
       fragment.arguments = bundle;
       val fragmentManager = supportFragmentManager
       fragmentManager
           .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
           .replace(R.id.container, fragment)
           .commitAllowingStateLoss()
   }*/


    /* */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> {
            }
//            R.id.menu_notification -> {
////                startActivity(Intent(this,ContestDetailActivity::class.java))
//                return true
//            }
            R.id.menu_wallet -> {
                val view = findViewById<View>(R.id.menu_wallet)
                if (walletPopupWindow == null)
                    initWalletPopUp(view)
                else
                    showWalletPopUp(view)
                return true
            }
            R.id.menu_sort -> {
                if (getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                    AppDelegate.showAlertRegister(
                        this, getResources().getString(R.string.app_name), getString(R.string.login_default),
                        getResources().getString(R.string.ok)
                    )
                } else {
                    startActivity(Intent(this, ActivityNewsSearch::class.java))
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


    fun displayToast(message: String, type: String) {
        if (type.equals("error")) {
            showSneakBarRed(message, type)
        } else if (type.equals("sucess")) {
            showSneakBar(message, type)
        } else if (type.equals("warning")) {
            showSneakBarOrange(message, type)
        }
    }

    fun showSneakBar(message: String, type: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
            .setTitle(getString(R.string.app_name), R.color.colorPrimary) // Title and title color
            .setMessage(message, R.color.white) // Message and message color
            .setDuration(4000) // Time duration to show
            .autoHide(true) // Auto hide Sneaker view
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
            .setIcon(R.mipmap.ic_launcher, R.color.transparent, false) // Icon, icon tint color and circular icon view
            .setTypeface(
                Typeface.createFromAsset(
                    this.getAssets(),
                    "fonts/faktpro_medium.ttf"
                )
            )
            .setCornerRadius(10, 2)
            .sneak(R.color.green_trans) // Sneak with background color
    }

    fun showSneakBarOrange(message: String, type: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
            .setTitle(getString(R.string.app_name), R.color.colorPrimary) // Title and title color
            .setMessage(message, R.color.white) // Message and message color
            .setDuration(4000) // Time duration to show
            .autoHide(true) // Auto hide Sneaker view
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
            .setIcon(R.mipmap.ic_launcher, R.color.transparent, false) // Icon, icon tint color and circular icon view
            .setTypeface(
                Typeface.createFromAsset(
                    this.getAssets(),
                    "fonts/faktpro_medium.ttf"
                )
            )
            .setCornerRadius(10, 2)
            .sneak(R.color.orange) // Sneak with background color
    }

    fun showSneakBarRed(message: String, type: String) {
        Sneaker.with(this) // Activity, Fragment or ViewGroup
            .setTitle(getString(R.string.app_name), R.color.colorPrimary) // Title and title color
            .setMessage(message, R.color.white) // Message and message color
            .setDuration(4000) // Time duration to show
            .autoHide(true) // Auto hide Sneaker view
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
            .setIcon(R.mipmap.ic_launcher, R.color.transparent, false) // Icon, icon tint color and circular icon view
            .setTypeface(
                Typeface.createFromAsset(
                    this.getAssets(),
                    "fonts/faktpro_medium.ttf"
                )
            )
            .setCornerRadius(10, 2)
            .sneak(R.color.redcolorTrans) // Sneak with background color
    }

    /**/  open fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
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
                    this, getResources().getString(R.string.app_name), getString(R.string.login_default),
                    getResources().getString(R.string.ok)
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

    private var walletPopupWindow: PopupWindow? = null

    private lateinit var popupWindowView: View
    private fun initWalletPopUp(anchorView: View) {
        try {
            /* set view for filter popup window*/
            walletPopupWindow = PopupWindow(this)
            popupWindowView = layoutInflater.inflate(R.layout.dialogue_wallet, null) as View
            walletPopupWindow!!.contentView = popupWindowView
            /* set visibility of brands list*/
            popupWindowView.img_close.setOnClickListener {
                walletPopupWindow!!.dismiss()
            }
            popupWindowView.ll_bottom.setOnClickListener {
                walletPopupWindow!!.dismiss()
            }
            /* show popup window*/
            showWalletPopUp(anchorView)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showWalletPopUp(anchorView: View) {
        walletPopupWindow!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        walletPopupWindow!!.width = WindowManager.LayoutParams.MATCH_PARENT
        walletPopupWindow!!.isOutsideTouchable = true
        walletPopupWindow!!.isFocusable = true
//        walletPopupWindow!!.setBackgroundDrawable(
//            BitmapDrawable(
//                resources,
//                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
//            )
//        )
        var background = ColorDrawable(android.graphics.Color.BLACK)
        background.alpha = 10
        walletPopupWindow!!.setBackgroundDrawable(background);
        val rectangle = Rect()
        val window = window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        walletPopupWindow!!.showAsDropDown(anchorView)
    }

    /* get view of cart menu item*/
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

    public fun showJoinContestDialogue(activity: AppCompatActivity) {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialogue_join_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(false)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.img_Close.setOnClickListener {
            dialogue.dismiss()
        }
        dialogue.btn_Join.setOnClickListener {
            startActivity(Intent(this, ActivityCreateTeam::class.java))
            dialogue.dismiss()
        }


        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    fun appLogout() {
        /*val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_yes_no)
        val dialogTitle: TextView = dialog.findViewById(R.id.dialogTitle)
        val no: TextView = dialog.findViewById(R.id.no)
        val yes: TextView = dialog.findViewById(R.id.yes)
        dialogTitle.text = resources.getString(R.string.logout_message)
        no.setOnClickListener {
            dialog.dismiss()
        }

        yes.setOnClickListener {
            dialog.dismiss()
            saveIntoPrefsString(StockConstant.USERID, " ")
            startActivity(Intent(this, WelcomeActivity::class.java))
            (0 until StockConstant.ACTIVITIES.size)
                .filter { StockConstant.ACTIVITIES[it] != null }
                .forEach { StockConstant.ACTIVITIES[it]?.finish() }
            finish()
        }
        dialog.show()*/

        saveIntoPrefsString(StockConstant.USERID, "")
        saveIntoPrefsString(StockConstant.USERNAME, "")
//        saveIntoPrefsString(StockConstant.USERPHONE, "")
//        saveIntoPrefsString(StockConstant.USEREMAIL, "")
        saveIntoPrefsString(StockConstant.USERIMG, "")
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent);
        finish();

        /* startActivity(Intent(this, WelcomeActivity::class.java))
         (0 until StockConstant.ACTIVITIES.size)
             .filter { StockConstant.ACTIVITIES[it] != null }
             .forEach { StockConstant.ACTIVITIES[it]?.finish() }
         finish()*/


    }

    /*
            this method for use to save string in Preference class
         */
    fun saveIntoPrefsString(key: String, value: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun saveUserData(key: String, userdata: SignupDataPojo?) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit();
        //edit.putString(key, userdata.toString())
        edit.putString(StockConstant.USERNAME, userdata!!.username)
        edit.putString(StockConstant.USEREMAIL, userdata!!.email)
        edit.putString(StockConstant.USERPHONE, userdata!!.phone_number);
        edit.putString(StockConstant.USERIMG, userdata!!.profile_image);
        edit.apply()
    }

    fun getUserData(): SignupDataPojo {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        var signupDataPojo = SignupDataPojo()
        signupDataPojo.username = prefs.getString(StockConstant.USERNAME, "");
        signupDataPojo.email = prefs.getString(StockConstant.USEREMAIL, "");
        signupDataPojo.phone_number = prefs.getString(StockConstant.USERPHONE, "");
        signupDataPojo.profile_image = prefs.getString(StockConstant.USERIMG, "");
        return signupDataPojo;
    }

    fun getUserData(key: String): SharedPreferences? {
        val prefs = getSharedPreferences(StockConstant.USERDATA, Context.MODE_PRIVATE)
        return prefs/*.getString(*//*key, StockConstant.DEFAULT_VALUE_STRING*//*)*/
    }

    /*
        This method for use to get string in preference class
     */

    fun getFromPrefsString(key: String): String? {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, StockConstant.DEFAULT_VALUE_STRING)
    }

    fun setContestType(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.CONTEST_TYPE, s)
        edit.apply()
    }


    fun setSectorFilter(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.SECTOR_TYPE, s)
        edit.apply()
    }

    fun setSectorWatchlistFilter(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.SECTOR_WATCHLIST_TYPE, s)
        edit.apply()
    }

    fun setMarketWatchlistFilter(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.MARKET_WATCHLIST_TYPE, s)
        edit.apply()
    }

    fun setCountryWatchlistFilter(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.COUNTRY_WATCHLIST_TYPE, s)
        edit.apply()
    }

    fun setAssetWatchlistFilter(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.ASSETS_WATCHLIST_TYPE, s)
        edit.apply()
    }

    fun setMarketContest(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.MARKET_TYPE, s)
        edit.apply()
    }

    fun setCountryContest(s: String) {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.COUNTRY_TYPE, s)
        edit.apply()
    }

    fun nextdialog() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.next_button_dailog)
        val no: Button = dialog.findViewById(R.id.cancle_btn)
        no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}





