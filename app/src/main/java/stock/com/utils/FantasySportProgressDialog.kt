package stock.com.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import stock.com.R


/**
 * custom progress dialog
 */

class FantasySportProgressDialog : ProgressDialog {

    internal var titleTv: TextView? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    override fun onCreate(savedInstanceState: Bundle) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_custom_progressbar)
        titleTv = findViewById(R.id.title_tv) as TextView
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window!!.setDimAmount(0.20f)
    }

    /**
     * show dialog if not showing
     */
    fun showDialog() {

        try {
            if (msGWProgressDialog!!.isShowing) {
                msGWProgressDialog!!.dismiss()
            }
            msGWProgressDialog!!.show()
            msGWProgressDialog!!.setCancelable(false)
            //  titleTv.setText(title);
        } catch (ex: Exception) {
            msGWProgressDialog = null
            ex.printStackTrace()
        }

    }

    /*
     * set title of progress dialog*/

    fun setTitle(title: String) {
        try {
            if (msGWProgressDialog != null && msGWProgressDialog!!.isShowing) {
                titleTv!!.text = title
            }
        } catch (ex: Exception) {
            msGWProgressDialog = null
            ex.printStackTrace()
        }

    }

    /**
     * hide dialog if not showing
     */
    fun dismissDialog() {
        try {
            Handler().postDelayed({
                try {
                    if (msGWProgressDialog != null && msGWProgressDialog!!.isShowing) {
                        msGWProgressDialog!!.dismiss()
                        msGWProgressDialog = null
                    } else if (msGWProgressDialog != null)
                        msGWProgressDialog = null
                } catch (ex: Exception) {
                    msGWProgressDialog = null
                    ex.printStackTrace()
                }
            }, 500)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    companion object {
        var msGWProgressDialog: FantasySportProgressDialog? = null
        fun getProgressDialog(mContext: Context): FantasySportProgressDialog? {
            try {
                if (msGWProgressDialog == null) {
                    msGWProgressDialog = FantasySportProgressDialog(mContext, R.style.AppCompatAlertDialogStyle)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return msGWProgressDialog
        }
    }


}
