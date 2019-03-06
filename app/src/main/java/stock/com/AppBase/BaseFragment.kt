package stock.com.AppBase

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
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
}