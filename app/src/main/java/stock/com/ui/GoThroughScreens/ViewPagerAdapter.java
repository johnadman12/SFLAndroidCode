package stock.com.ui.GoThroughScreens;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import stock.com.R;


import java.util.ArrayList;

/**
 * Created by oot on 1/5/2017.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Drawable> list;
    private static final String TAG = "ViewPagerAdapter";

    public ViewPagerAdapter(Context context, ArrayList<Drawable> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ConstraintLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager_layout, container, false);
        imageView =  itemView.findViewById(R.id.imageViewPager);

        imageView.setImageDrawable(list.get(position));

       /* try{
            Glide.with(context).load(list.get(position)).error(R.mipmap.ic_launcher).placeholder(0).thumbnail(0.1f).into(imageView);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        ((ViewPager) container).addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((androidx.constraintlayout.widget.ConstraintLayout) object);
    }
}
