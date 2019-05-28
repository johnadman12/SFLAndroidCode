package stock.com.utils


import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Dialog
import android.app.ProgressDialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.telephony.TelephonyManager
import android.text.format.DateUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.irozon.sneaker.Sneaker
import stock.com.BuildConfig
import stock.com.R
import stock.com.constant.Tags
import stock.com.ui.signup.activity.SignUpActivity
import stock.com.utils.networkUtils.NetworkUtils
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object AppDelegate {

    val MAP_ZOOM = 17f
    val TILT = 25f
    val IMAGE_MAX_SIZE = 2048
    val SELECT_PICTURE = 222
    val PIC_CROP = 333
    var CHAT_PAGE_RESUME = false
    var frame_layout_height = 0
    var mProgressDialog: ProgressDialog? = null
    var zeroResult = false
    var metrics: DisplayMetrics? = null
    var API_KEY_4 = "AIzaSyBrBqsVSkmZ_OIuI-I-Vd8mJCZ297j8tXo"
    var API_KEY_1 = "AIzaSyBue6xSS-BTr8upwyyi6ae3NWcE22OeFSw"
    var API_KEY_2 = "AIzaSyC7hYR1hv6sEn5sq05jBeSn4mE7jrjqczk"
    var API_KEY_3 = "AIzaSyCbLy-PxiSQjd5aAGgxBkULK1B9Dk3dGK8"
    var API_ACCESS_KEY = "AIzaSyAkpr9y0YAkyQorrjCJ2f-WLEL8E5O7DUs"
    var fbPermissions = arrayOf("public_profile", "email", "user_birthday")
    var bitmapMask: Bitmap? = null
    var bitmapCache: Bitmap? = null
    lateinit var mAlert: AlertDialog.Builder
    var displayMetrics: DisplayMetrics? = null
    val LIKE = 1
    val DISLIKE = 2
    var BANNER_URL = "http://18.188.34.216/webadmin/uploads/banner/"
    var EXCHANGE_URL = "http://18.188.34.216/webadmin/uploads/exchange/"
    private var builder: Dialog? = null
    private var mInstance: AppDelegate? = null


//fun getHourDifferenceBetweenTwoDate(createdTime: String): Int {
//
//    val dtCurrentTime = DateTime(Tags.timeZone)
//
//    val outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(Tags.timeZone)
//
//    val dtCreatedTime = outputFormatter.parseDateTime(createdTime)
//    val hoursDifference = Hours.hoursBetween(dtCreatedTime, dtCurrentTime)
//    return hoursDifference.hours
//}

    fun isAppIsInBackground(context: Context): Boolean {

        var isInBackground = false
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {

            val runningProcesses = am.runningAppProcesses
            for (processInfo in runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (activeProcess in processInfo.pkgList) {
                        if (activeProcess == context.packageName) {
                            isInBackground = true
                            break
                        }
                    }
                }
            }
        } else {
            val taskInfo = am.getRunningTasks(1)
            val componentInfo = taskInfo[0].topActivity
            if (componentInfo.packageName == context.packageName) {
                isInBackground = true
                return isInBackground
            }
        }
        return isInBackground
    }

    fun setLocale(lang: String?, mContext: Context) {

        val locale = Locale(lang, "US")
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        mContext.resources.updateConfiguration(
            config,
            mContext.resources.displayMetrics
        )
    }

    fun getTimeStampFromDate(str_date: String): Long {
        var timestamp: Long = 0
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val date = formatter.parse(str_date) as Date
            timestamp = date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timestamp
    }


    fun saveBitmapToExternalStorage(bitmap: Bitmap?, name: String): String {
        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/Android/data/com.zoom.activity/cache/image")
        myDir.mkdirs()
        val file = File(myDir, name)
        Log.i("file ", "" + file)
        if (file.exists())
            file.delete()
        try {
            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return myDir.toString() + "/" + name
    }

    fun printHashKey(pContext: Context) {
        try {
            val info = pContext.packageManager.getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.i("hash", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("hash", "printHashKey()", e)
        } catch (e: Exception) {
            Log.e("hash", "printHashKey()", e)
        }

    }

    fun changeTimeToRelativeTime(timeStamp: Long): String {

        // it comes out like this 2013-08-31 15:55:22 so adjust the date format
        return try {

            DateUtils.getRelativeTimeSpanString(timeStamp, System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS)
                .toString()
        } catch (e: Exception) {
            ""
        }

    }


    fun convertTimeFormat(time: String, fromFormat: String, toFormat: String): String {
        var timea = ""
        try {
            val sdf = SimpleDateFormat(fromFormat, Locale.getDefault())
            val dateObj = sdf.parse(time)
            println(dateObj)
            timea = SimpleDateFormat(toFormat, Locale.getDefault()).format(dateObj)
            return timea
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timea
    }

    //
//    fun convertTimeFormatAndTimeZone(time: String, fromFormat: String, toFormat: String): String {
//        var timea = ""
//        try {
//            val fromDateFormat = DateTimeFormat.forPattern(fromFormat).withZone(DateTimeZone.UTC!!)
//
//            val toDateFormat = DateTimeFormat.forPattern(toFormat).withZone(DateTimeZone.getDefault())
//            val jodaTime = fromDateFormat.parseDateTime(time)
//
//            timea = jodaTime.toString(toDateFormat)
//            return timea
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//
//        return timea
//    }
//

    private fun updateResourcesLocaleLegacy(context: Context, locale: Locale): Context {
        val resources = context.resources
        val configuration = resources.configuration
        configuration.locale = locale
        configuration.setLayoutDirection(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }


    fun setTimeHMA(time: String, fromFormat: String, toFormat: String): String {
        var timea = ""
        try {
            val sdf = SimpleDateFormat(fromFormat)
            val dateObj = sdf.parse(time)
            println(dateObj)
            timea = SimpleDateFormat(toFormat).format(dateObj)
            return timea
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timea
    }

    fun isValidEmail(target: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        //String[] ema_arr = new String[5];
        // ema_arr[0] =
    }

    //checking network availability
    fun isNetworkAvailable(context: Context): Boolean {
        if (NetworkUtils.isConnected()) {
            return true
        }
        showToast(context, context.getString(R.string.error_network_connection))
//        Toast.makeText(this, getString(R.string.error_network_connection), Toast.LENGTH_LONG).show()
        return false
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = cm.activeNetworkInfo
//        return if (networkInfo != null && networkInfo.isConnected) {
//            true
//        } else false
    }

    private val IP_ADDRESS = Pattern.compile(
        "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                + "|[1-9][0-9]|[0-9]))"
    )

    fun validateIPAddress(ipAddress: String): Boolean {
        val matcher = IP_ADDRESS.matcher(ipAddress)
        return matcher.matches()
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            ) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap!!)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


    fun getCompressImagePath(uri: Uri, mContext: Context): String? {
        var `in`: InputStream? = null
        var returnedBitmap: Bitmap? = null
        val mContentResolver: ContentResolver
        val getImagePath = ""
        try {
            mContentResolver = mContext.contentResolver
            `in` = mContentResolver.openInputStream(uri)
            //Decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`in`, null, o)
            `in`!!.close()
            var scale = 1
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = Math.pow(
                    2.0,
                    Math.round(
                        Math.log(
                            IMAGE_MAX_SIZE / Math.max(
                                o.outHeight,
                                o.outWidth
                            ).toDouble()
                        ) / Math.log(0.5)
                    ).toInt().toDouble()
                ).toInt()
            }

            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            `in` = mContentResolver.openInputStream(uri)
            var bitmap = BitmapFactory.decodeStream(`in`, null, o2)
            `in`!!.close()

            //First check
            val ei = ExifInterface(uri.path)
            val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    returnedBitmap = rotateImage(bitmap, 90f)
                    //Free up the memory
                    bitmap!!.recycle()
                    bitmap = null
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    returnedBitmap = rotateImage(bitmap, 180f)
                    //Free up the memory
                    bitmap!!.recycle()
                    bitmap = null
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    returnedBitmap = rotateImage(bitmap, 270f)
                    //Free up the memory
                    bitmap!!.recycle()
                    bitmap = null
                }
                else -> returnedBitmap = bitmap
            }
            val root = Environment.getExternalStorageDirectory().toString()
            val myDir = File("$root/Android/data/com.os.pikpak.activity/cache/image")
            myDir.mkdirs()

            val tsLong = System.currentTimeMillis()
            val timeStamp = tsLong.toString()
            val fname = timeStamp + "_" + ".jpg"
            val file = File(myDir, fname)
            Log.i("file ", "" + file)
            if (file.exists())
                file.delete()
            try {
                val out = FileOutputStream(file)
                returnedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

//compressImagePath = image_path;

            return myDir.toString() + "/" + fname
        } catch (e: FileNotFoundException) {
            //  L.e(e);
        } catch (e: IOException) {
            //L.e(e);
        }

        return null
    }

    private fun rotateImage(source: Bitmap?, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source!!, 0, 0, source.width, source.height, matrix, true)
    }

    fun getDate(time: Long, pattern: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = time
        val d = c.time
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(d)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * check if app is in background or foreground
     * @param mContext
     * @return
     */
    fun isAppForground(mContext: Context): Boolean {

        val am = mContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = am.getRunningTasks(1)
        if (!tasks.isEmpty()) {
            val topActivity = tasks[0].topActivity
            if (topActivity.packageName != mContext.packageName) {
                return false
            }
        }

        return true
    }

    fun isValidPassword(pass2: String): Boolean {
        val PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[!@#$%&*]).{6,20})"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(pass2)
        return matcher.matches()
    }

    fun CheckEmail(email: String): Boolean {
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun getYoutubeVideoId(youtubeUrl: String?): String {
        var video_id = ""
        if (AppDelegate.isValidString(youtubeUrl) && youtubeUrl!!.trim { it <= ' ' }.length > 0 && youtubeUrl.startsWith(
                "http"
            )
        ) {
            var expression =
                "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*" // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
            var pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(youtubeUrl)
            if (matcher.matches()) {
                val groupIndex1 = matcher.group(7)
                if (groupIndex1 != null && groupIndex1.length == 11)
                    video_id = groupIndex1
            }
        } else {
            if (AppDelegate.isValidString(youtubeUrl) && youtubeUrl!!.trim { it <= ' ' }.length > 0) {
                var string = youtubeUrl.split("=")
                if (string != null && string.size > 0)
                    video_id = (string[string.lastIndex])
            }
        }
        AppDelegate.LogT("VideoId==>" + video_id)
        return video_id
    }

    fun calculateInSampleSize(
        options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(
        resId: String,
        reqWidth: Int, reqHeight: Int
    ): Bitmap {

        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(resId, options)
    }

    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): String {
        val theta = lon1 - lon2
        var dist =
            Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(
                deg2rad(theta)
            )
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60.0 * 1.1515
        val df = DecimalFormat("0.##")
        return df.format(dist)
    }

    fun rotate(bitmap: Bitmap?, degree: Int): Bitmap? {
        try {
            val w = bitmap!!.width
            val h = bitmap.height
            val mtx = Matrix()
            mtx.setRotate(degree.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true)
        } catch (e: Exception) {
            AppDelegate.LogE(e)
            return null
        } catch (e: VerifyError) {
            Log.d("Info", "File not found: " + e.message)
            return null
        }

    }

    @Throws(FileNotFoundException::class, IOException::class)
    fun getThumbnail(uri: Uri, context: Context): Bitmap? {
        var input = context.contentResolver.openInputStream(uri)

        val onlyBoundsOptions = BitmapFactory.Options()
        onlyBoundsOptions.inJustDecodeBounds = true
        onlyBoundsOptions.inDither = true//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions)
        input!!.close()

        if (onlyBoundsOptions.outWidth == -1 || onlyBoundsOptions.outHeight == -1) {
            return null
        }

        val originalSize =
            if (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) onlyBoundsOptions.outHeight else onlyBoundsOptions.outWidth

        val ratio = if (originalSize > 500) originalSize / 500 else 1.0

        val bitmapOptions = BitmapFactory.Options()
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio as Double)
        bitmapOptions.inDither = true //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888//
        input = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions)
        input!!.close()
        return bitmap
    }

    private fun getPowerOfTwoForSampleRatio(ratio: Double): Int {
        val k = Integer.highestOneBit(Math.floor(ratio).toInt())
        return if (k == 0)
            1
        else
            k
    }

    fun getImageUriFromBitmap(inContext: Context, inImage: Bitmap): Uri {
        var bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        var path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun call(context: Context, number: String) {
        var uri = "tel:" + number.trim()
        var intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse(uri))
        context.startActivity(intent)
    }

    fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    @JvmOverloads
    fun haveNetworkConnection(mContext: Context?, showAlert: Boolean = true): Boolean {
        var isConnected = false
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        if (mContext == null) {
            return false
        } else {
            val cm = mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.allNetworkInfo
            for (ni in netInfo) {
                if (ni.typeName.equals("WIFI", ignoreCase = true))
                    if (ni.isConnected)
                        haveConnectedWifi = true
                if (ni.typeName.equals("MOBILE", ignoreCase = true))
                    if (ni.isConnected)
                        haveConnectedMobile = true
            }
            isConnected = haveConnectedWifi || haveConnectedMobile
            if (isConnected) {
                return isConnected
            } else {
                if (showAlert) {
                    showToast(mContext, mContext.resources.getString(R.string.error_network_connection))
                }
            }
            return isConnected
        }
    }


    fun showToast(mContext: Context?, Message: String) {
        try {
            if (mContext != null)
                Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show()
            else
                Log.e("tag", "lognActivity is null at showing toast.")
        } catch (e: Exception) {
            Log.e("tag", "lognActivity is null at showing toast.", e)
        }

    }

    fun showToast(mContext: Context?, Message: String, type: Int) {
        try {
            if (mContext != null)
                if (type == 0)
                    Toast.makeText(mContext, Message, Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(mContext, Message, Toast.LENGTH_LONG).show()
            else
                Log.e("tag", "lognActivity is null at showing toast.")
        } catch (e: Exception) {
            Log.e("tag", "lognActivity is null at showing toast.", e)
        }
    }

    @JvmOverloads
    fun showProgressDialog(
        mContext: Activity, mTitle: String = "",
        mMessage: String = "Loading..."
    ) {
        hideKeyBoard(mContext)
        try {
            FantasySportProgressDialog.getProgressDialog(mContext)!!.showDialog()
        } catch (e: Exception) {

        }
    }

    fun showProgressDialog2(mContext: Activity?) {
        hideKeyBoard(mContext)
        try {
            if (mContext != null) {
                if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                    return
                }
                mProgressDialog = ProgressDialog(mContext)
                mProgressDialog!!.setCancelable(false)
                if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                    mProgressDialog!!.dismiss()
                    mProgressDialog!!.show()
                } else {
                    mProgressDialog!!.show()
                }
            } else {
                Log.d("tag", "showProgressDialog instense is null")
            }
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun hideProgressDialog(mContext: Activity?) {
        try {

            FantasySportProgressDialog.getProgressDialog(mContext!!)!!.dismissDialog()
        } catch (e: Exception) {
            AppDelegate.LogE(e)
        }

    }

    fun showFragment(
        mContext: FragmentActivity?,
        mFragment: Fragment, fragmentId: Int, mBundle: Bundle?, TAG: String
    ) {
        LogD("mBundle==" + mBundle)

        if (mBundle != null && mContext != null)
            mFragment.arguments = mBundle
        try {
            val mFragmentTransaction = mContext!!
                .supportFragmentManager
                .beginTransaction()

            mFragmentTransaction.replace(fragmentId, mFragment, TAG)
                .addToBackStack(TAG).commitAllowingStateLoss()
            hideKeyBoard(mContext)
        } catch (e: Exception) {
            LogE(e)
        }

    }


    fun HideKeyboardMain(mContext: Activity, view: View) {
        try {
            val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // R.id.search_img
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            //Utility.debug(1, TAG, "Exception in executing HideKeyboardMain()");
            LogE(e)
        }

    }

    fun openKeyboard(mActivity: Activity, view: View) {
        try {
            val imm = mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
        } catch (e: Exception) {
            LogE(e)
        }

    }

    /**
     * @param TAG
     * @param Message
     * @param LogType
     */
    fun Log(TAG: String, Message: String, LogType: Int) {
        when (LogType) {
            // Case 1- To Show Message as Debug
            1 -> Log.d(TAG, Message)
            // Case 2- To Show Message as Error
            2 -> Log.e(TAG, Message)
            // Case 3- To Show Message as Information
            3 -> Log.i(TAG, Message)
            // Case 4- To Show Message as Verbose
            4 -> Log.v(TAG, Message)
            // Case 5- To Show Message as Assert
            5 -> Log.w(TAG, Message)
            // Case Default- To Show Message as System Print
            else -> println(Message)
        }
    }

    fun Log(TAG: String, Message: String) {
        Log(TAG, Message, 1)
    }

    /* Function to show log for error message */
    fun LogD(Message: String) {
        Log(Tags.DATE, "" + Message, 1)
    }

    /* Function to show log for error message */
    fun LogDB(Message: String) {
        Log(Tags.DATABASE, "" + Message, 1)
    }

    /* Function to show log for error message */
    fun LogE(e: Exception?) {
        if (e != null) {
            LogE(e.message!!)
            e.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null.", 2)
        }
    }

    /* Function to show log for error message */
    fun LogE(e: OutOfMemoryError?) {
        if (e != null) {
            LogE(e.message!!)
            e.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null.", 2)
        }
    }

    /* Function to show log for error message */
    fun LogE(message: String, exception: Exception?) {
        if (exception != null) {
            LogE(
                "from = " + message + " => "
                        + exception.message
            )
            exception.printStackTrace()
        } else {
            Log(Tags.ERROR, "exception object is also null. at " + message, 2)
        }
    }

    /**
     * Funtion to log with tag RESULT, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogR(Message: String) {
        Log(Tags.RESULT, "" + Message, 1)
    }

    /**
     * Funtion to log with tag RESULT, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogI(Message: String) {
        Log(Tags.INTERNET, "" + Message, 1)
    }

    /**
     * Funtion to log with tag ERROR, you need to just pass the message. This
     * method is used to exeception .
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogE(Message: String) {
        Log(Tags.ERROR, "" + Message, 2)
    }

    /**
     * Funtion to log with tag your name, you need to just pass the message.
     * This method is used to log url of your api calling.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogH(Message: String) {
        Log(Tags.Heena, "" + Message, 1)
    }

    /**
     * Funtion to log with tag URL, you need to just pass the message. This
     * method is used to log url of your api calling.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogU(Message: String) {
        Log(Tags.URL, "" + Message, 1)
    }

    /**
     * Funtion to log with tag URL_API, you need to just pass the message. This
     * method is used to log url of your api calling.
     *
     * @String Message = pass your message that you want to log.
     */

    /**
     * Funtion to log with tag TEST, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogT(Message: String) {
        Log(Tags.TEST, "" + Message, 1)
    }

    /**
     * Funtion to log with tag TEST, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogCh(Message: String) {
        Log("check", "" + Message, 1)
    }


    fun LogT(Message: String, type: Int) {
        Log(Tags.TEST, "" + Message, type)
    }

    fun LogP(Message: String) {
        Log(Tags.PREF, "" + Message, 1)
    }


    /**
     * Funtion to log with tag GCM, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogGC(Message: String) {
        Log(Tags.GCM, "" + Message, 1)
    }

    /**
     * Funtion to log with tag Chat, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogC(Message: String) {
        Log(Tags.CHAT, "" + Message, 1)
    }


    /**
     * Funtion to log with tag GPS, you need to just pass the message.
     *
     * @String Message = pass your message that you want to log.
     */
    fun LogFCM(Message: String) {
        Log(Tags.FCM, "" + Message, 1)
    }

    fun timeoutalert(context: Context) {
        try {
            val alertDialog = AlertDialog.Builder(context)

            alertDialog.setTitle("Error")
            alertDialog.setMessage("Connection TimeOut")
            alertDialog.setCancelable(false)

            alertDialog.setNeutralButton(
                "OK"
            ) { dialog, which -> dialog.cancel() }

            alertDialog.show()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun checkEmptyString(xyz: String?): Boolean {
        return xyz == null || xyz.trim { it <= ' ' }.equals("", ignoreCase = true)
    }

    fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, i: Int) {
        addFragment(fragmentManager, fragment, 1)
    }

    /**
     * Function to Transaction between Fragments
     */

    fun showFragmentAnimation(
        mContext: FragmentActivity?,
        mFragment: Fragment?, fragmentId: Int, mBundle: Bundle?, TAG: String
    ) {

        if (mBundle != null && mContext != null)
            mFragment!!.arguments = mBundle
        try {
            if (mContext != null && mFragment != null)
                mContext.supportFragmentManager
                    .beginTransaction()
//                        .setCustomAnimations(R.anim.left_in, R.anim.left_out,
//                                R.anim.right_in, R.anim.right_out_1)
                    .replace(fragmentId, mFragment)
                    .addToBackStack(null).commit()
            hideKeyBoard(mContext)
        } catch (e: Exception) {
            LogE(e)
        }
    }

    fun showFragmentAnimationOppose(
        mContext: FragmentActivity?,
        mFragment: Fragment?,
        fragmentId: Int,
        mBundle: Bundle?,
        TAG: String
    ) {

        if (mBundle != null && mContext != null)
            mFragment!!.arguments = mBundle
        try {
            if (mContext != null && mFragment != null)
                mContext.supportFragmentManager
                    .beginTransaction()
//                        .setCustomAnimations(R.anim.right_in, R.anim.right_out_1, R.anim.left_in, R.anim.left_out)
                    .replace(fragmentId, mFragment)
                    .addToBackStack(null).commit()
            hideKeyBoard(mContext)
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun showFragmentAnimationBottomIn(
        mContext: FragmentActivity?,
        mFragment: Fragment?,
        fragmentId: Int,
        mBundle: Bundle?,
        TAG: String
    ) {
        if (mBundle != null && mContext != null)
            mFragment!!.arguments = mBundle
        try {
            if (mContext != null && mFragment != null)
                mContext.supportFragmentManager
                    .beginTransaction()
//                        .setCustomAnimations(R.anim.bottom_in, R.anim.left_out, R.anim.right_in, R.anim.right_out_1)
                    .replace(fragmentId, mFragment)
                    .addToBackStack(null).commit()
            hideKeyBoard(mContext)
        } catch (e: Exception) {
            LogE(e)
        }

    }


    /**
     * Function to Transaction between Fragments
     */
    fun showFragmentAnimation(
        fragmentManager: FragmentManager?,
        fragment: Fragment?, id: Int
    ) {
        try {
            if (fragmentManager != null && fragment != null)
                fragmentManager
                    .beginTransaction()
//                        .setCustomAnimations(R.anim.left_in, R.anim.left_out,
//                                R.anim.right_in, R.anim.right_out_1)
                    .replace(id, fragment)
                    .addToBackStack(null).commit()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun isContainAlpha(name: String): Boolean {
        val chars = name.toCharArray()
        for (c in chars) {
            if (Character.isLetter(c))
                return true
        }
        return false
    }

    fun hideKeyBoard(mActivity: Activity?, timeAfter: Long) {
        if (mActivity != null)
            Handler().postDelayed({ hideKeyBoard(mActivity) }, timeAfter)
    }

    fun hideKeyBoard(mActivity: Activity?) {
        if (mActivity == null)
            return
        else {
            val inputManager = mActivity
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            // check if no view has focus:
            val v = mActivity.currentFocus ?: return

            LogT("hideKeyBoard viewNot null")
            inputManager.hideSoftInputFromWindow(
                v.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }


    fun createDrawableFromView(context: Context, view: View): Bitmap? {

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        var bitmap: Bitmap? = null
        try {
            bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap!!)
            view.draw(canvas)
            LogT("Bitmap" + bitmap)
        } catch (e: Exception) {
            LogE(e)
        }

        return bitmap
    }

    val currentTime: String
        get() = SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().time)

    fun convertToBitmap(
        drawable: Drawable, widthPixels: Int,
        heightPixels: Int
    ): Bitmap {
        val mutableBitmap = Bitmap.createBitmap(
            widthPixels, heightPixels,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(mutableBitmap)
        drawable.setBounds(0, 0, widthPixels, heightPixels)
        drawable.draw(canvas)
        return mutableBitmap
    }

    fun rotateImage(angle: Int, OriginalPhoto: Bitmap): Bitmap {
        var OriginalPhoto = OriginalPhoto
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())

        OriginalPhoto = Bitmap.createBitmap(
            OriginalPhoto, 0, 0,
            OriginalPhoto.width, OriginalPhoto.height, matrix,
            true
        )
        return OriginalPhoto
    }

    fun convertdp(context: Context, x: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(x * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun createNewFile(bitmap: Bitmap, context: Context): File? {

        var file = File(Environment.getExternalStorageDirectory(), System.currentTimeMillis().toString() + ".png")
        try {
            file.createNewFile()
//Convert bitmap to byte array
            var bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
            var bitmapdata = bos.toByteArray()
//write the bytes in file
            var fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)

        val color = 0xff424242.toInt()
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    val isSDcardAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun getHashKey(mContext: Context): String? {
        var str_HashKey: String? = null
        try {
            val info = mContext.packageManager.getPackageInfo(
                mContext.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                str_HashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT)
                LogT("HashKey = " + str_HashKey!!)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            LogE(e)
        } catch (e: NoSuchAlgorithmException) {
            LogE(e)
        }

        return str_HashKey
    }

    /*
 * Masking of an image
 */
    fun masking(original: Bitmap, mask: Bitmap): Bitmap {
        var original = original
        original = Bitmap.createScaledBitmap(
            original, mask.width,
            mask.height, true
        )

        val result = Bitmap.createBitmap(
            mask.width, mask.height,
            Bitmap.Config.ARGB_8888
        )
        val mCanvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        mCanvas.drawBitmap(original, 0f, 0f, null)
        mCanvas.drawBitmap(mask, 0f, 0f, paint)
        paint.xfermode = null
        return result
    }

    /* convert drawable to bitmap */
    fun integerToBitmap(ctx: Context, integer: Int?): Bitmap {

        val o = BitmapFactory.Options()
        o.inScaled = false
        o.inJustDecodeBounds = false
        val icon = BitmapFactory.decodeResource(
            ctx.resources, integer!!,
            o
        )
        icon.density = Bitmap.DENSITY_NONE
        return icon
    }

    /*
 * Reduce image size
 */
    fun Shrinkmethod(file: String, width: Int, height: Int): Bitmap {
        val bitopt = BitmapFactory.Options()
        bitopt.inJustDecodeBounds = true
        var bit = BitmapFactory.decodeFile(file, bitopt)

        val h = Math.ceil((bitopt.outHeight / height.toFloat()).toDouble()).toInt()
        val w = Math.ceil((bitopt.outWidth / width.toFloat()).toDouble()).toInt()

        if (h > 1 || w > 1) {
            if (h > w) {
                bitopt.inSampleSize = h

            } else {
                bitopt.inSampleSize = w
            }
        }
        bitopt.inJustDecodeBounds = false
        bit = BitmapFactory.decodeFile(file, bitopt)

//            println("HEIGHT WIDTH:::::::" + bit.width + "::::"
//                    + bit.height)

        return bit

    }


    @SuppressLint("MissingPermission")
    fun getUUID(mContext: Context): String {
        val tManager = mContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        LogD("getUUID = " + tManager.deviceId)
        return tManager.deviceId
        //        return "359774050367819";
    }

    fun getBase64String(value: String): String {
        val data: ByteArray
        var str_base64 = ""
        val str = "xyzstring"
        try {
            data = str.toByteArray(charset("UTF-8"))
            str_base64 = Base64.encodeToString(data, Base64.DEFAULT)
            Log.i("Base 64 ", str_base64)
        } catch (e: UnsupportedEncodingException) {
            LogE(e)
        }

        return str_base64
    }

    fun isValidDate(inDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        dateFormat.isLenient = false
        try {
            dateFormat.parse(inDate.trim { it <= ' ' })
        } catch (pe: ParseException) {
            LogE(pe)
            return false
        }

        return true
    }


    fun getFormatedAddress(str_value_1: String, str_value_2: String): String {
        var st_value = ""
        if (isValidString(str_value_1) && isValidString(str_value_2)) {
            st_value = str_value_1 + " - " + str_value_2
        } else if (isValidString(str_value_1)) {
            st_value = str_value_1
        } else if (isValidString(str_value_2)) {
            st_value = str_value_2
        }
        return st_value
    }

    fun isValidString(string: String?): Boolean {
        return string != null && !string.equals("null", ignoreCase = true) && string.length > 0
    }

    fun isValidDouble(string: String?): Boolean {
        try {
            if (string != null && !string.equals(
                    "null",
                    ignoreCase = true
                ) && string.length > 0 && java.lang.Double.parseDouble(string) > 1
            ) {
                return true
            } else {
                LogT("isValidDouble false => " + string!!)
                return false
            }
        } catch (e: Exception) {
            LogE(e)
            return false
        }

    }

    //


    fun showAlert(mContext: Context, Message: String) {
        val dialog = Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.next_button_dailog)
        val message: TextView = dialog.findViewById(R.id.text)
        message.setText(Message)
        val no: Button = dialog.findViewById(R.id.cancle_btn)
        no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

/*fun showAlert(mContext: Context, Title: String, Message: String, str_button_name: String) {
        try {
            mAlert = AlertDialog.Builder(mContext)
            mAlert.setCancelable(false)
            mAlert.setTitle(Title)
            mAlert.setMessage(Message)
            mAlert.setPositiveButton(
                str_button_name
            ) { dialog, which -> dialog.dismiss() }
            mAlert.show()
        } catch (e: Exception) {
            LogE(e)
        }

    }*/

    fun showAlertRegister(
        mContext: Context, Title: String, Message: String
    ) {
        val dialog = Dialog(mContext/*, android.R.style.ThemeOverlay_Material_Dialog_Alert*/)
        dialog.setCancelable(false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setContentView(R.layout.dialog_alert_withoutlogin)
        val message: TextView = dialog.findViewById(R.id.message)
        message.setText(Message)
        val title: TextView = dialog.findViewById(R.id.title)
        title.setText(Title)
        val tv_yes: TextView = dialog.findViewById(R.id.tv_yes)
        val tv_cancel: TextView = dialog.findViewById(R.id.tv_cancel)
        tv_yes.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, SignUpActivity::class.java).putExtra(
                    StockConstant.FLAG,
                    "false"
                )
            );
            dialog.dismiss()
        }
        tv_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun showSneakBarRed(context: Context, message: String, type: String) {
        Sneaker.with(context as Activity) // Activity, Fragment or ViewGroup
            .setTitle(type, R.color.colorPrimary) // Title and title color
            .setMessage(message, R.color.white) // Message and message color
            .setDuration(4000) // Time duration to show
            .autoHide(true) // Auto hide Sneaker view
            .setHeight(ViewGroup.LayoutParams.WRAP_CONTENT) // Height of the Sneaker layout
            .setIcon(R.mipmap.ic_launcher, R.color.transparent, false) // Icon, icon tint color and circular icon view
            .setTypeface(
                Typeface.createFromAsset(
                    context.getAssets(),
                    "fonts/faktpro_medium.ttf"
                )
            )
            .setCornerRadius(10, 2)
            .sneak(R.color.redcolorTrans) // Sneak with background color
    }

    fun showAlert(
        mContext: Context,
        Title: String,
        Message: String,
        str_button_name: String,
        onClickListener: View.OnClickListener?,
        str_button_name_1: String,
        onClickListener1: View.OnClickListener?
    ) {
        try {
            mAlert = AlertDialog.Builder(mContext)
            mAlert.setCancelable(false)
            mAlert.setTitle(Title)
            mAlert.setMessage(Message)
            mAlert.setPositiveButton(
                str_button_name
            ) { dialog, which ->
                onClickListener?.onClick(null)
                dialog.dismiss()
            }

            mAlert.setNegativeButton(
                str_button_name_1
            ) { dialog, which ->
                onClickListener1?.onClick(null)
                dialog.dismiss()
            }
            mAlert.show()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun decodeSampledBitmapFromPath(
        path: String, reqWidth: Int,
        reqHeight: Int
    ): Bitmap {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        options.inSampleSize = calculateInSampleSize(
            options, reqWidth,
            reqHeight
        )

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    fun getBitmap(path: Uri, context: Context): Bitmap? {

        var ins: InputStream? = null
        try {
            val IMAGE_MAX_SIZE = 30000 // 1.2MP
            ins = context.contentResolver.openInputStream(path)

            // Decode image size
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(ins, null, options)
            ins.close()


            var scale = 1
            while ((options.outWidth * options.outHeight) * (1 / Math.pow(scale.toDouble(), 2.0)) >
                IMAGE_MAX_SIZE
            ) {
                scale++
            }
//                Log.d(TAG, "scale = " + scale + ", orig-width: " + options.outWidth + ",
//                        orig-height: " + options.outHeight);

            var resultBitmap: Bitmap? = null
            ins = context.contentResolver.openInputStream(path)
            if (scale > 1) {
                scale--
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                options = BitmapFactory.Options()
                options.inSampleSize = scale
                resultBitmap = BitmapFactory.decodeStream(ins, null, options)

                // resize to desired dimensions
                var height = resultBitmap.getHeight()
                var width = resultBitmap.getWidth()

                var y = Math.sqrt(IMAGE_MAX_SIZE / ((width) / height) as Double)
                var x = (y / height) * width

                var scaledBitmap = Bitmap.createScaledBitmap(
                    resultBitmap, x.toInt(),
                    y.toInt(), true
                )
                resultBitmap.recycle()
                resultBitmap = scaledBitmap

                System.gc()
            } else {
                resultBitmap = BitmapFactory.decodeStream(ins)
            }
            ins.close()

            Log.d(
                "", "bitmap size - width: " + resultBitmap.width + ", height: " +
                        resultBitmap.height
            )
            return resultBitmap
        } catch (e: IOException) {
//                Log.e("", e.getMessage(), e);
        }
        return null
    }

    fun getBitmap(path: String): Bitmap? {

        var bitmap: Bitmap? = null
        try {
            var f = File(path)
            var options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            var fis = FileInputStream(f)
            try {
//                    bitmap = BitmapFactory.decodeFile(path, options)
                if (f.exists())

                    bitmap = BitmapFactory.decodeStream(fis, null, options)
                else {
                    AppDelegate.LogT("File does not exists")
                }
            } finally {
                fis.close()
                System.gc()
            }
//                f.deleteOnExit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun decodeFile(f: File): Bitmap? {
        var b: Bitmap? = null
        try {

            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true

            var fis = FileInputStream(f)
            try {
                BitmapFactory.decodeStream(fis, null, o)
            } finally {
                fis.close()
            }

            var scale = 1
            val size = Math.max(o.outHeight, o.outWidth)
            while (size shr scale - 1 > IMAGE_MAX_SIZE) {
                ++scale
            }
            // Decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            fis = FileInputStream(f)
            try {
                b = BitmapFactory.decodeStream(fis, null, o2)
            } finally {
                fis.close()
            }
        } catch (e: IOException) {
            AppDelegate.LogE("Exception is", e)
            AppDelegate.LogT("Exception is" + f.absolutePath)

//                b = Shrinkmethod(f.absolutePath, 100, 100)
        }

        return b
    }

    fun lanchAppIfInstalled(
        mContext: Context,
        appPackageName: String
    ) {
        try {
            mContext.startActivity(
                mContext.packageManager
                    .getLaunchIntentForPackage(appPackageName)
            )
        } catch (anfe: ActivityNotFoundException) {
            LogE(anfe)
            try {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri
                            .parse("market://details?id=" + appPackageName)
                    )
                )
            } catch (e: Exception) {
                LogE(e)
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri
                            .parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                    )
                )
            }

        } catch (e: Exception) {
            LogE(e)
            try {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri
                            .parse("market://details?id=" + appPackageName)
                    )
                )
            } catch (e1: Exception) {
                LogE(e1)
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri
                            .parse("https://play.google.com/store/apps/details?id=" + appPackageName)
                    )
                )
            }

        }

    }

    fun dpToPix(mContext: Context, value: Int): Int {
        var calculatedValue = value
        try {
            calculatedValue = Math.round(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    value.toFloat(),
                    mContext.resources.displayMetrics
                )
            )
        } catch (e: Exception) {
            LogE(e)
        }

        return calculatedValue

    }

    fun pixToDP(mContext: Context, value: Int): Float {
        val r = mContext.resources
        var calculatedValue = value.toFloat()
        try {
            calculatedValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), r.displayMetrics)
        } catch (e: Exception) {
            LogE(e)
        }

        return calculatedValue

    }


    fun getFileSize(file: File): Int {
        try {
            return Integer.parseInt((file.length() / 1024).toString())
        } catch (e: Exception) {
            LogE(e)
            return 0
        }

    }


    /**
     * reduces the size of the image
     *
     * @param image
     * @param maxSize
     * @return
     */
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 0) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    fun decodeSampledBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap { // BEST QUALITY MATCH
        //First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        // Calculate inSampleSize, Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        options.inPreferredConfig = Bitmap.Config.RGB_565
        var inSampleSize = 1

        if (height > reqHeight) {
            inSampleSize = Math.round(height.toFloat() / reqHeight.toFloat())
        }
        val expectedWidth = width / inSampleSize

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round(width.toFloat() / reqWidth.toFloat())
        }

        options.inSampleSize = inSampleSize

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        return BitmapFactory.decodeFile(path, options)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setListViewHeightBasedOnChildren(listView: GridView, gridAdapter: ListAdapter?) {
        try {
            if (gridAdapter == null) {
                // pre-condition
                LogE("Adapter is null")
                val params = listView.layoutParams
                params.height = 80
                listView.layoutParams = params
                listView.requestLayout()
                return
            }
            var totalHeight = 0

            var itemCount = gridAdapter.count.toFloat()
            if (itemCount >= 3) {
                itemCount = itemCount / 3
            } else {
                itemCount = 1f
            }

            val desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.width,
                View.MeasureSpec.AT_MOST
            )
            var i = 0
            while (i < itemCount) {
                val listItem = gridAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
                i++
            }
            val params = listView.layoutParams
            params.height = totalHeight + listView.verticalSpacing * (gridAdapter.count - 1)
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
//        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setGridViewHeight(mContext: Context, listView: GridView, gridAdapter: ListAdapter?, view: View?) {
        try {
            if (gridAdapter == null) {
                // pre-condition
                LogE("Adapter is null")
                val params = listView.layoutParams
                params.height = 80
                listView.layoutParams = params
                listView.requestLayout()
                return
            }
            var totalHeight = 0

            val itemCount = gridAdapter.count.toFloat()
            val value = itemCount / 2
            LogT("height = after device = " + value)

            var string_value = value.toString() + ""
            string_value = string_value.substring(string_value.lastIndexOf(".") + 1, string_value.length)
            var intValue = value.toInt()
            if (Integer.parseInt(string_value) > 0) {
                intValue += 1
            }
            LogT("intValue = " + intValue)
            val desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.width,
                View.MeasureSpec.AT_MOST
            )
            for (i in 0..intValue - 1) {
                val listItem = gridAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += dpToPix(mContext, 150)
            }
            if (view != null && view.visibility == View.VISIBLE) {
                totalHeight += dpToPix(mContext, 60)
            }
            LogT("totalHeight = " + totalHeight)
            val params = listView.layoutParams
            params.height = totalHeight + listView.verticalSpacing * intValue
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun setListViewHeight(mContext: Context, listView: ListView, gridAdapter: ListAdapter?, view: View?) {
        try {
            if (gridAdapter == null) {
                // pre-condition
                LogE("Adapter is null")
                val params = listView.layoutParams
                params.height = 80
                listView.layoutParams = params
                listView.requestLayout()
                return
            }
            var totalHeight = 0
            val itemCount = gridAdapter.count

            val desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.width,
                View.MeasureSpec.AT_MOST
            )
            for (i in 0..itemCount - 1) {
                val listItem = gridAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
            if (view != null && view.visibility == View.VISIBLE) {
                totalHeight += dpToPix(mContext, 60)
            }
            val params = listView.layoutParams
            params.height = totalHeight
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun setListViewHeight(mContext: Context, listView: ListView, gridAdapter: ListAdapter?) {
        try {
            if (gridAdapter == null) {
                // pre-condition
                LogE("Adapter is null")
                val params = listView.layoutParams
                params.height = 80
                listView.layoutParams = params
                listView.requestLayout()
                return
            }
            var totalHeight = 0
            val itemCount = gridAdapter.count

            val desiredWidth = View.MeasureSpec.makeMeasureSpec(
                listView.width,
                View.MeasureSpec.AT_MOST
            )
            for (i in 0..itemCount - 1) {
                val listItem = gridAdapter.getView(i, null, listView)
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += listItem.measuredHeight
            }
            val params = listView.layoutParams
            params.height = totalHeight
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun setListViewHeightNull(listView: ListView) {
        try {
            val itemCount = 0
            val params = listView.layoutParams
            params.height = itemCount
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun setGridViewHeightNull(listView: GridView) {
        try {
            val itemCount = 0
            val params = listView.layoutParams
            params.height = itemCount
            listView.layoutParams = params
            listView.requestLayout()
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun getNewFile(mContext: Context): String? {
        val directoryFile: File
        val capturedFile: File
        if (isSDcardAvailable) {
            directoryFile = File(
                Environment.getExternalStorageDirectory().toString()
                        + "/" + mContext.resources.getString(R.string.app_name)
            )
        } else {
            directoryFile = mContext.getDir(mContext.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        if (directoryFile.exists() && directoryFile.isDirectory || directoryFile.mkdirs()) {
            capturedFile = File(
                directoryFile, "Image_" + System.currentTimeMillis()
                        + ".png"
            )
            try {
                if (capturedFile.createNewFile()) {
                    LogT("File created = " + capturedFile.absolutePath)
                    return capturedFile.absolutePath
                }
            } catch (e: IOException) {
                LogE(e)
            }

        }
        LogE("no file created.")
        return null
    }


    fun getNewVideoFile(mContext: Context): String {
        val directoryFile: File
        val capturedFile: File
        if (AppDelegate.isSDcardAvailable) {
            directoryFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
                        + "/" + mContext.resources.getString(R.string.app_name)
            )
        } else {
            directoryFile = mContext.getDir(mContext.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        if (directoryFile.exists() && directoryFile.isDirectory || directoryFile.mkdirs()) {
            capturedFile = File(
                directoryFile, "Video_" + System.currentTimeMillis()
                        + ".mp4"
            )
            try {
                if (capturedFile.createNewFile()) {
                    AppDelegate.LogT("File created = " + capturedFile.absolutePath)
                    return capturedFile.absolutePath
                }
            } catch (e: IOException) {
                AppDelegate.LogE(e)
            }

        }
        AppDelegate.LogE("no file created.")
        return ""
    }

    fun getNewImageFile(mContext: Context): String {
        val directoryFile: File
        val capturedFile: File
        if (AppDelegate.isSDcardAvailable) {
            directoryFile = File(
                Environment.getExternalStorageDirectory().toString()
                        + "/" + mContext.resources.getString(R.string.app_name)
            )
        } else {
            directoryFile = mContext.getDir(mContext.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        if (directoryFile.exists() && directoryFile.isDirectory || directoryFile.mkdirs()) {
            capturedFile = File(
                directoryFile, "Image_" + System.currentTimeMillis()
                        + ".png"
            )
            try {
                if (capturedFile.createNewFile()) {
                    AppDelegate.LogT("File created = " + capturedFile.absolutePath)
                    return capturedFile.absolutePath
                }
            } catch (e: IOException) {
                AppDelegate.LogE(e)
            }

        }
        AppDelegate.LogE("no file created.")
        return ""
    }

    fun getNewAudioFile(mContext: Context): String {
        val directoryFile: File
        val capturedFile: File
        if (AppDelegate.isSDcardAvailable) {
            directoryFile = File(
                Environment.getExternalStorageDirectory().toString()
                        + "/" + mContext.resources.getString(R.string.app_name)
            )
        } else {
            directoryFile = mContext.getDir(mContext.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        if (directoryFile.exists() && directoryFile.isDirectory || directoryFile.mkdirs()) {
            capturedFile = File(
                directoryFile, "Audio" + System.currentTimeMillis()
                        + ".mp3"
            )
            try {
                if (capturedFile.createNewFile()) {
                    AppDelegate.LogT("File created = " + capturedFile.absolutePath)
                    return capturedFile.absolutePath
                }
            } catch (e: IOException) {
                AppDelegate.LogE(e)
            }

        }
        AppDelegate.LogE("no file created.")
        return ""
    }

    fun getDefaultFile(mContext: Context): String? {
        val directoryFile: File
        val capturedFile: File
        if (isSDcardAvailable) {
            directoryFile = File(
                Environment.getExternalStorageDirectory().toString()
                        + "/" + mContext.resources.getString(R.string.app_name)
            )
        } else {
            directoryFile = mContext.getDir(mContext.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
        }
        LogT("getDefaultFile directory Exists.")
        if (directoryFile.exists() && directoryFile.isDirectory || directoryFile.mkdirs()) {
            LogT("getDefaultFile directory Exists and file create.")
            capturedFile = File(directoryFile, "Image_Craftdeal.png")
            try {
                if (capturedFile.createNewFile()) {
                    LogT("File created = " + capturedFile.absolutePath)
                    return capturedFile.absolutePath
                } else {
                    return capturedFile.absolutePath
                }
            } catch (e: IOException) {
                LogE(e)
            }

        } else {
            LogT("getDefaultFile directory Exists and file not create.")
        }
        LogE("no file created.")
        return null
    }

    fun openGallery(mActivity: Activity, SELECT_PICTURE: Int) {
        mActivity.startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), SELECT_PICTURE
        )
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "CropTitle", null)
        return Uri.parse(path)
    }

    fun performCrop(mActivity: Activity, picUri: Uri): Uri? {
        var cropimageUri: Uri? = null
        try {
            val values = ContentValues()

            values.put(MediaStore.Images.Media.TITLE, "cropuser")

            values.put(MediaStore.Images.Media.DESCRIPTION, "cropuserPic")

            cropimageUri = mActivity.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values
            )

            val cropIntent = Intent("com.android.camera.action.CROP")
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*")
            // set crop properties
            cropIntent.putExtra("crop", "true")

            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            // indicate output X and Y
            cropIntent.putExtra("outputX", 1000)
            cropIntent.putExtra("outputY", 1000)


            // retrieve data on return
            cropIntent.putExtra("return-data", false)
            //            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropimageUri);
            // start the activity - we handle returning in onActivityResult
            LogT("at performCrop cropimageUri = $cropimageUri, picUri = $picUri")
            mActivity.startActivityForResult(cropIntent, PIC_CROP)
        } catch (anfe: ActivityNotFoundException) {
            // display an error message
            val errorMessage = "Whoops - your device doesn't support the crop action!"
            val toast = Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }
        // respond to users whose devices do not support the crop action
        return cropimageUri
    }

    fun getFilterdUrl(str_url: String?): String? {
        var str_url = str_url
        if (str_url != null && str_url.length > 0) {
            str_url = str_url.replace("[", "%5B")
            str_url = str_url.replace("@", "%40")
            str_url = str_url.replace(" ", "%20")
        }
        return str_url
    }

    fun showKeyboard(mContext: Context?, editText: EditText?) {
        if (editText != null && mContext != null) {
            editText.postDelayed({
                editText.requestFocus()
                editText.isFocusable = true
                editText.isFocusableInTouchMode = true
                val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
            }, 100)
        }
    }

    @JvmOverloads
    fun disableButton(view: View, delayTime: Long = 200) {
        view.isEnabled = false
        view.isClickable = false
        Handler().postAtTime({
            view.isEnabled = true
            view.isClickable = true
        }, delayTime)
    }

    fun getcurrenttime(): String {
        try {
            val date1 = Calendar.getInstance().time
            val dateFormater =
                SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")  //("yyyy-MM-dd HH:mm:ss"); (format)Sat, 16 Jan 2016 11:55:23 GMT

            val gmtTime = TimeZone.getTimeZone("GMT+00")
            dateFormater.timeZone = gmtTime
            var newgmtdate = dateFormater.format(date1)

            newgmtdate = newgmtdate.substring(
                0,
                if (newgmtdate.indexOf("+") != -1) newgmtdate.indexOf("+") else newgmtdate.length
            )
            Log.d("currentLocalTime", " date " + newgmtdate)

            return newgmtdate
        } catch (e: Exception) {
            LogE(e)
        }

        return ""
    }

    fun getcurrenttime(timexoneconverteddate: String, dateFormat: String): String {
        try {
            val inputDateFormat = SimpleDateFormat(dateFormat)
            var date1 = Calendar.getInstance().time
            date1 = inputDateFormat.parse(timexoneconverteddate + " " + currentTime)

            val dateFormater =
                SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")  //("yyyy-MM-dd HH:mm:ss"); (format)Sat, 16 Jan 2016 11:55:23 GMT

            val gmtTime = TimeZone.getTimeZone("GMT")
            LogT("getcurrenttime gmtTime => " + gmtTime)
            dateFormater.timeZone = gmtTime
            var newgmtdate = dateFormater.format(date1)

            LogT("getcurrenttime newgmtdate => " + newgmtdate)
            newgmtdate = newgmtdate.substring(
                0,
                if (newgmtdate.indexOf("+") != -1) newgmtdate.indexOf("+") else newgmtdate.length
            )
            LogT("getcurrenttime currentLocalTime newgmtdate => " + newgmtdate)

            return newgmtdate
        } catch (e: Exception) {
            LogE(e)
            return timexoneconverteddate
        }

    }

    fun getDeviceWith(mContext: Context): Int {
        if (displayMetrics == null)
            displayMetrics = mContext.resources.displayMetrics
//        AppDelegate.LogT("getDeviceWidth = " + width);
        return displayMetrics!!.widthPixels
    }

    fun getDeviceHeight(mContext: Context): Int {
        if (displayMetrics == null)
            displayMetrics = mContext.resources.displayMetrics
//        AppDelegate.LogT("getDeviceHeight = " + height);
        return displayMetrics!!.heightPixels
    }

    fun loadBitmap(context: Context, picName: String): Bitmap? {
        var b: Bitmap? = null
        val fis: FileInputStream
        try {
            fis = context.openFileInput(picName)
            b = BitmapFactory.decodeStream(fis)
            fis.close()

        } catch (e: FileNotFoundException) {
            LogE(e)
        } catch (e: IOException) {
            LogE(e)
        }

        return b
    }

    fun saveFile(context: Context, b: Bitmap, picName: String) {
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(File(picName))
            b.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            //            AppDelegate.showToast(lognActivity, "Image saved BitmapImageViewTarget called " + picName);
        } catch (e: FileNotFoundException) {
            LogE(e)
        } catch (e: IOException) {
            LogE(e)
        }

    }

    fun getValidDatePostFixFormate(string: String): String {
        return if (string.endsWith("1") && !string.endsWith("11"))
            "st"
        else if (string.endsWith("2") && !string.endsWith("12"))
            "nd"
        else if (string.endsWith("3") && !string.endsWith("13"))
            "rd"
        else
            "th"
    }

    fun getDecodedStringMessage(string: String): String {
        var string = string
        if (string.contains("\\n") || string.contains("\n")) {
            string = string.replace("\\n", System.getProperty("line.separator"))
            string = string.replace("\n", System.getProperty("line.separator"))
        }
        string = string.replace("%20".toRegex(), " ")
        string = string.replace("%29".toRegex(), "'")
        return string
    }

    fun getEncodedStringMessage(string: String): String {
        var string = string
        if (string.contains(System.getProperty("line.separator"))) {
            LogT("list.seperator is present")
            string = string.replace(System.getProperty("line.separator").toRegex(), "%5Cn")
        }
        string = string.replace(" ".toRegex(), "%20")
        string = string.replace("'".toRegex(), "%29")
        return string
    }

    fun getDateForChat(long_date: String): String {
        val calendar = Calendar.getInstance()
        try {
            calendar.timeInMillis = java.lang.Long.parseLong(long_date)
        } catch (e: Exception) {
            LogE(e)
        }

        return SimpleDateFormat("dd MMMM yyyy").format(calendar.time)
    }

    fun getTimeForChat(long_date: String): String {
        val calendar = Calendar.getInstance()
        try {
            calendar.timeInMillis = java.lang.Long.parseLong(long_date)
        } catch (e: Exception) {
            LogE(e)
        }

        return SimpleDateFormat("hh:mm aa").format(calendar.time)
    }

    fun openURL(mActivity: Activity, str_url: String) {
        var str_url = str_url
        try {
            if (!isValidString(str_url)) {
                showToast(mActivity, "Website url is null.")
                return
            }
            if (!str_url.startsWith("http://") && !str_url.startsWith("https://"))
                str_url = "http://" + str_url
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(str_url))
            mActivity.startActivity(browserIntent)
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun getIntValue(string: String): Int {
        var value = 0
        try {
            value = Integer.parseInt(string)
        } catch (e: Exception) {
            LogE(e)
        }

        return value
    }


    fun getPriceFormatted(value: Int): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(value.toLong())
    }

    fun getDayOfMonthSuffix(n: Int): String {
        if (n >= 11 && n <= 13) {
            return "th"
        }
        when (n % 10) {
            1 -> return "st"
            2 -> return "nd"
            3 -> return "rd"
            else -> return "th"
        }
    }

    fun getDayOfMonthSuffix(string: String): String {
        try {
            val n = Integer.parseInt(string)
            if (n >= 11 && n <= 13) {
                return "th"
            }
            when (n % 10) {
                1 -> return "st"
                2 -> return "nd"
                3 -> return "rd"
                else -> return "th"
            }
        } catch (e: Exception) {
            LogE(e)
        }

        return "th"
    }

    @SuppressLint("NewApi")
//        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun blurRenderScript(context: Context, smallBitmap: Bitmap): Bitmap {
        var smallBitmap = smallBitmap
        try {
            smallBitmap = RGB565toARGB888(smallBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val bitmap = Bitmap.createBitmap(
            smallBitmap.width, smallBitmap.height,
            Bitmap.Config.ARGB_8888
        )

        val renderScript = RenderScript.create(context)

        val blurInput = Allocation.createFromBitmap(renderScript, smallBitmap)
        val blurOutput = Allocation.createFromBitmap(renderScript, bitmap)

        val blur = ScriptIntrinsicBlur.create(
            renderScript,
            Element.U8_4(renderScript)
        )
        blur.setInput(blurInput)
        //  blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput)
        blurOutput.copyTo(bitmap)
        renderScript.destroy()

        return bitmap

    }

    @Throws(Exception::class)
    private fun RGB565toARGB888(img: Bitmap): Bitmap {
        val numPixels = img.width * img.height
        val pixels = IntArray(numPixels)

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.width, 0, 0, img.width, img.height)

        //Create a Bitmap of the appropriate format.
        val result = Bitmap.createBitmap(img.width, img.height, Bitmap.Config.ARGB_8888)

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.width, 0, 0, result.width, result.height)
        return result
    }

    fun isLegalPassword(pass: String): Boolean {
        if (pass.length < 6 || pass.length > 20) return false
//            if (!pass.matches(".*[A-Z].*".toRegex())) return false
//
//            if (!pass.matches(".*[a-z].*".toRegex())) return false

//            return if (!pass.matches(".*\\d.*".toRegex())) false else true

        // if (!pass.matches(".*[~!.......].*")) return false;
        return true
    }

    fun hideAnimatedProgressDialog(mContext: Context?) {
        try {
            if (mContext != null) {
                if (builder != null && builder!!.isShowing) {
                    builder!!.dismiss()
                } else {
                    builder = ProgressDialog(mContext)
                    builder!!.dismiss()
                }
            } else {
            }
        } catch (e: Exception) {
            LogE(e)
        }

    }

    fun setStictModePermission() {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    }

    fun roundOff(value: Double): Double {
        var value = value
        val factor = Math.pow(10.0, 2.0).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

//    fun generatePath(lognActivity: Context, uri: Uri, type: Int): String {
//        var filePath: String? = null
//        var isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//        if (isKitKat) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                filePath = generateFromKitkat(uri, lognActivity, type)
//            }
//        }
//
//        if (filePath != null) {
//            return filePath
//        }
//
//        var cursor = lognActivity.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DATA), null, null, null)
//
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
//                filePath = cursor.getString(columnIndex)
//            }
//            cursor.close()
//        }
//        return if (filePath == null) uri.path else filePath
//    }


//    @TargetApi(19)
//    private fun generateFromKitkat(uri: Uri, lognActivity: Context, type: Int): String? {
//        var filePath: String? = null
//        if (DocumentsContract.isDocumentUri(lognActivity, uri)) {
//            var wholeID = DocumentsContract.getDocumentId(uri)
//            var column: Array<String>? = null
//            var sel: String? = null
//            var cursor: Cursor? = null
//            var id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
//            if (type == PostYourArtwork_Activity.Type_Audio) {
//                column = arrayOf(MediaStore.Audio.Media.DATA)
//                sel = MediaStore.Audio.Media._ID + "=?"
//
//                cursor = lognActivity.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        column, sel, arrayOf(id), null)
//            }
//            if (type == PostYourArtwork_Activity.Type_Video) {
//                column = arrayOf(MediaStore.Video.Media.DATA)
//                sel = MediaStore.Video.Media._ID + "=?"
//
//                cursor = lognActivity.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                        column, sel, arrayOf(id), null)
//            }
//            if (type == PostYourArtwork_Activity.Type_Image) {
//                column = arrayOf(MediaStore.Images.Media.DATA)
//                sel = MediaStore.Images.Media._ID + "=?"
//
//                cursor = lognActivity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        column, sel, arrayOf(id), null)
//            }
//            var columnIndex = cursor!!.getColumnIndex(column!![0])
//            if (cursor.moveToFirst()) {
//                filePath = cursor.getString(columnIndex)
//            }
//            cursor.close()
//        }
//        return filePath
//    }

//    val LongTypeAdapter = object : TypeAdapter<Number>() {
//
//        @Throws(IOException::class)
//        override fun write(out: JsonWriter, value: Number) {
//            out.value(value)
//        }
//
//        @Throws(IOException::class)
//        override fun read(`in`: JsonReader): Number? {
//            if (`in`.peek() === JsonToken.NULL) {
//                `in`.nextNull()
//                return null
//            }
//            try {
//                val result = `in`.nextString()
//                return if ("" == result) {
//                    null
//                } else java.lang.Long.parseLong(result)
//            } catch (e: NumberFormatException) {
//                throw JsonSyntaxException(e)
//            }
//
//        }
//    }
    //    public static void animateCamera(GoogleMap map_business, LatLng latLng) {
    //        AppDelegate.animateCamera(map_business, latLng, AppDelegate.TILT, null);
    //    }
    //
    //    public static void animateCamera(GoogleMap map_business, LatLng latLng,
    //                                     float tilt, GoogleMap.CancelableCallback callback) {
    //        if (map_business != null && latLng != null && latLng.latitude != -1.0
    //                && latLng.latitude != 0.0)
    //            map_business.animateCamera(CameraUpdateFactory
    //                    .newCameraPosition(new CameraPosition.Builder()
    //                            .target(latLng).zoom(AppDelegate.MAP_ZOOM)
    //                            .bearing(0).tilt(tilt).build()), 500, callback);
    //        else {
    //            AppDelegate
    //                    .LogE("at AppDelegate.animateCamera map_business is null.");
    //        }
    //    }
    //
    //    public static MarkerOptions getMarkerOptions(Context mContext,
    //                                                 LatLng latLng, int icon_id) {
    //        return getMarkerOptions(mContext, latLng, icon_id, 74, 64);
    //    }
    //
    //    public static MarkerOptions getMarkerOptions(Context mContext,
    //                                                 LatLng latLng, int icon_id, String title) {
    //        return getMarkerOptionsWithTitleSnippet(mContext, latLng, icon_id, 74, 64, title);
    //    }
    //
    //    public static MarkerOptions getMarkerOptions(Context mContext,
    //                                                 LatLng latLng, int icon_id, int width, int height) {
    //        markerOptions = new MarkerOptions();
    //        if (mContext != null) {
    //            try {
    //                if (latLng != null && latLng.latitude != -1.0 && icon_id != -1) {
    //                    markerOptions.icon(BitmapDescriptorFactory
    //                            .fromBitmap(AppDelegate.convertToBitmap(mContext
    //                                            .getResources().getDrawable(icon_id),
    //                                    width, height)));
    //                    markerOptions.position(latLng);
    //                }
    //            } catch (Exception e) {
    //                AppDelegate.LogE(e);
    //            }
    //        }
    //        return markerOptions;
    //    }
    //
    //    public static MarkerOptions getMarkerOptions(Context mContext, LatLng latLng) {
    //        markerOptions = new MarkerOptions();
    //        if (mContext != null) {
    //            try {
    //                if (latLng != null && latLng.latitude != -1.0) {
    //                    markerOptions.position(latLng);
    //                }
    //            } catch (Exception e) {
    //                AppDelegate.LogE(e);
    //            }
    //        }
    //        return markerOptions;
    //    }

}