package stock.com.ui.dashboard.home.adapter

import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import com.nostra13.universalimageloader.core.ImageLoader
import stock.com.R
import stock.com.application.FantasyApplication
import stock.com.ui.pojo.HomePojo
import stock.com.utils.AppDelegate

class SlidingImageAdapterHomeBanner(private val context: FragmentActivity, private val IMAGES: List<HomePojo.Banner>) :
    PagerAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return IMAGES.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout = inflater.inflate(R.layout.child_banner_layout, view, false)!!

        val imageView = imageLayout.findViewById<View>(R.id.imv_product) as ImageView

        ImageLoader.getInstance().displayImage(
            AppDelegate.BANNER_URL + IMAGES[position].image,
            imageView,
            FantasyApplication.getInstance().options
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        view.addView(imageLayout, position-1)
        /*imageView.setOnClickListener {
            context.startActivity(
                Intent(context, ZoomActivity::class.java).putExtra(
                    "URL",
                    IMAGES[position].image
                )
            )
        }*/
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}

    override fun saveState(): Parcelable? {
        return null
    }

}

