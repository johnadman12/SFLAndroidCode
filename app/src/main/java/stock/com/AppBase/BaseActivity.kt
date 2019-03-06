package stock.com.AppBase

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.action_bar_notification_icon.view.*
import kotlinx.android.synthetic.main.dialogue_join_contest.*
import kotlinx.android.synthetic.main.dialogue_wallet.view.*
import stock.com.R
import stock.com.data.Prefs
import stock.com.ui.comment.activity.CommentActivity
import stock.com.ui.createTeam.activity.ChooseTeamActivity
import stock.com.ui.notification.activity.NotificationActivity
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.StockConstant

open class BaseActivity : AppCompatActivity() {
    private lateinit var notificationView: View
    private var progressDialog: ProgressDialog? = null
    var pref: Prefs? = null

    var notif = false
    var wallet = false
    var filter = false
    var edit = false
    var comment = false
    var info_icon = false
    var sor_icon = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0);
        pref = Prefs(this)
    }

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

    fun displayToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    var menu: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        /* set layout of menu*/
        menuInflater.inflate(R.menu.action_menu, menu)
        this.menu = menu
        /* if child activity is product activity then visible filer menu icon*/
        menu.findItem(R.id.menu_filter).isVisible = filter
        menu.findItem(R.id.menu_notification).isVisible = notif
        menu.findItem(R.id.menu_wallet).isVisible = wallet
        menu.findItem(R.id.menu_edit).isVisible = edit
        menu.findItem(R.id.menu_comment).isVisible = comment
        menu.findItem(R.id.menu_info).isVisible = info_icon
        menu.findItem(R.id.menu_sort).isVisible = sor_icon
//        menu.findItem(R.id.menu_sort).isVisible = driveActivityName == ProductActivity().javaClass.name
//        if (driveActivityName == ProductActivity().javaClass.name){
//        }
        getViewOfCartMenuItem(menu)
        /* se click listener of toolbar cart icon*/
        notificationView.setOnClickListener {
            //            startActivity(Intent(this,ContestDetailActivity::class.java))
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        return true
    }

    fun setMenu(notif: Boolean, wallet: Boolean, filter: Boolean, edit: Boolean, comment : Boolean,
                info_icon : Boolean, sor_icon : Boolean) {
        this.notif = notif
        this.wallet = wallet
        this.filter = filter
        this.edit = edit
        this.comment = comment
        this.info_icon = info_icon
        this.sor_icon = sor_icon
        if (menu != null) {
            menu!!.findItem(R.id.menu_filter).isVisible = filter
            menu!!.findItem(R.id.menu_notification).isVisible = notif
            menu!!.findItem(R.id.menu_wallet).isVisible = wallet
            menu!!.findItem(R.id.menu_edit).isVisible = edit
            menu!!.findItem(R.id.menu_comment).isVisible = comment
            menu!!.findItem(R.id.menu_info).isVisible = info_icon
            menu!!.findItem(R.id.menu_sort).isVisible = sor_icon
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
            }
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
            startActivity(Intent(this, ChooseTeamActivity::class.java))
            dialogue.dismiss()
        }


        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    fun appLogout() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
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
            saveIntoPrefsString(StockConstant.USERID, "")
            startActivity(Intent(this, WelcomeActivity::class.java))
            (0 until StockConstant.ACTIVITIES.size)
                .filter { StockConstant.ACTIVITIES[it] != null }
                .forEach { StockConstant.ACTIVITIES[it]?.finish() }
            finish()
        }
        dialog.show()
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



    /*
        This method for use to get string in preference class
     */

    fun getFromPrefsString(key: String): String? {
        val prefs = getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, StockConstant.DEFAULT_VALUE_STRING)
    }

}





