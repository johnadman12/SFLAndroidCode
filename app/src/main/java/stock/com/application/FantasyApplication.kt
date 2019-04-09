package stock.com.application

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.core.content.ContextCompat
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import net.danlew.android.joda.JodaTimeAndroid
import stock.com.R
import stock.com.constant.PrefConstant
import stock.com.constant.Tags
import stock.com.data.Prefs
import stock.com.utils.AppDelegate
import stock.com.utils.networkUtils.Utils
import retrofit2.Retrofit

class FantasyApplication : MultiDexApplication() {

    var options: DisplayImageOptions? = null
    private var retrofit: Retrofit? = null

    companion object {
        lateinit var fantasyApplication: FantasyApplication
        fun getInstance(): FantasyApplication {
            return fantasyApplication
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        fantasyApplication = this

        /* initialize joda Time*/
        JodaTimeAndroid.init(this)
        /* initialize universal image loader*/
        initImageLoader(this)
        val defaultBitmap1 = AppDelegate.drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.ic_launcher_round)!!)
        options = DisplayImageOptions.Builder()
            .showImageOnLoading(BitmapDrawable(resources, defaultBitmap1))
            .showImageForEmptyUri(BitmapDrawable(resources, defaultBitmap1))
            .showImageOnFail(BitmapDrawable(resources, defaultBitmap1))
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .build()
        Utils.init(this)
    }

    private fun initImageLoader(context: Context) {
        val config = ImageLoaderConfiguration.Builder(context)
        config.threadPriority(Thread.NORM_PRIORITY - 2)
        config.denyCacheImageMultipleSizesInMemory()
        config.diskCacheFileNameGenerator(Md5FileNameGenerator())
        config.diskCacheSize(100 * 1024 * 1024) // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO)
        config.writeDebugLogs() // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build())
    }


    fun getLanguage(): String {
        val language = Prefs(this).getStringValue(
            PrefConstant.KEY_LANGUAGE, ""
                ?: ""
        )
        return if (language.isEmpty())
            Tags.LANGUAGE_ENGLISH
        else
            language
    }

//    fun setLocale(lang: String, mContext: Context) {
//        val locale = Locale(lang, "US")
//        Locale.setDefault(locale)
//        val config = Configuration()
//        config.locale = locale
//        baseContext.resources.updateConfiguration(config,
//                baseContext.resources.displayMetrics)
//    }

//    public fun getStringByLocal(context: Activity, value: String): String {
//        var configuration = Configuration(context.getResources().getConfiguration());
//        configuration.setLocale(Locale("en"))
//        return context.createConfigurationContext(configuration).getResources().getString(R.string.call);
//    }

}