package stock.com.utils.networkUtils

import android.Manifest
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import stock.com.utils.StockConstant
import android.widget.AdapterView

import android.app.Dialog
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.TextView
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ListView
import stock.com.R
import stock.com.interfaces.SelectDialogInterface





class UtilityMethods {


    companion object {
        fun hasStoragePermission(context: Activity): Boolean {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) === PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    StockConstant.REQUEST_WRITE_STORAGE
                )
                return false
            } else
                return true
        }

        fun hasCameraPermission(context: Activity): Boolean {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {

                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.CAMERA),
                    StockConstant.REQUEST_CAMERA
                )
                return false
            } else
                return true
        }

        fun showSelectDialog(
            mActivity: Context,
            list_data: ArrayList<String>,
            mSelectInterface: SelectDialogInterface,
            title: String,
            view_clicked_postion: Int
        ) {
            val d = Dialog(mActivity)
            d.run {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_select)
                getWindow().setGravity(Gravity.CENTER)
                setCancelable(true)
                setCanceledOnTouchOutside(true)
            }
            val city_list = d.findViewById(R.id.list_view) as ListView
            val tv_title = d.findViewById(R.id.ciiqer_text) as TextView
            if (title != "") {
                tv_title.text = title
            }
            val adapter = ArrayAdapter(mActivity, R.layout.row_listselect, R.id.text_item, list_data)
            city_list.setAdapter(adapter)
            d.show();

            city_list.setOnItemClickListener(object : AdapterView.OnItemClickListener {
                override
                fun onItemClick(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                    mSelectInterface.selectedPosition(view_clicked_postion, i)
                    d.dismiss()
                }
            })
        }
    }
}