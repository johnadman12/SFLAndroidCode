package stock.com.ui.dashboard.home.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;

import stock.com.R;
import stock.com.ui.pojo.HomePojo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {


    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private List<HomePojo.Banner> LIBRARIES;


    public HorizontalPagerAdapter(final Context context, List<HomePojo.Banner> LIBRARIES) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        this.LIBRARIES = LIBRARIES;
    }

    @Override
    public int getCount() {
        return LIBRARIES.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.child_banner_layout, container, false);
        ImageView imageView = view.findViewById(R.id.imv_product);

        Glide.with(mContext).load("http://18.188.34.216/webadmin/uploads/banner/"+LIBRARIES.get(position).getImage()).error(R.mipmap.ic_launcher).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
