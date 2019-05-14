package stock.com.AppBase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import stock.com.ui.splash.activity.WelcomeActivity
import stock.com.utils.StockConstant

open class BaseFragment : Fragment() {

//    var pref: Prefs?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        pref=Prefs.getInstance(context)
//        updateMenuTitles()
    }

    fun displayToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_LONG).show()
    }

//    fun isNetworkAvailable() : Boolean{
//        if (NetworkUtils.isConnected()) {
//            return true
//        }
//        com.os.drewel.utill.Utils.getInstance().showToast(context,getString(R.string.error_network_connection))
//            return false
//
//    }

    fun saveIntoPrefsString(key: String, value: String) {
        val prefs = activity!!.getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(key, value)
        edit.apply()
    }


    /*
        This method for use to get string in preference class
     */

    fun getFromPrefsString(key: String): String? {
        val prefs = activity!!.getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(key, StockConstant.DEFAULT_VALUE_STRING)
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

        saveIntoPrefsString(StockConstant.USERID,"")
        saveIntoPrefsString(StockConstant.USERNAME, "")
        saveIntoPrefsString(StockConstant.USERPHONE, "")
        saveIntoPrefsString(StockConstant.USEREMAIL, "")
        saveIntoPrefsString(StockConstant.USERIMG, "")
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent);
        activity!!.finish();


       /* startActivity(Intent(context, WelcomeActivity::class.java))
        (0 until StockConstant.ACTIVITIES.size)
            .filter { StockConstant.ACTIVITIES[it] != null }
            .forEach { StockConstant.ACTIVITIES[it]?.finish() }
        activity!!.finish()*/
    }
    fun setContestType(s: String) {
        val prefs = activity!!.getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(StockConstant.CONTEST_TYPE, s)
        edit.apply()
    }
    fun setFilter(s: String, key:String) {
        val prefs = activity!!.getSharedPreferences(StockConstant.PREF_NAME, Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putString(key, s)
        edit.apply()
    }

}