package stock.com.ui.dashboard.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import stock.com.R;
import stock.com.ui.dashboard.home.fragment.HomeFragment;
import stock.com.ui.offer_list.OfferListActivity;
import stock.com.ui.pojo.HomePojo;
import stock.com.ui.share.ShareActivity;
import stock.com.utils.AppDelegate;

import java.util.List;

public class StockNameAdapter extends PagerAdapter {


    private Context mContext;
    private String userId;
    private LayoutInflater mLayoutInflater;
    private HomeFragment homeFragment;
    private List<HomePojo.Exchange> Exchange;


    public StockNameAdapter(final Context context, List<HomePojo.Exchange> LIBRARIES, HomeFragment fragment) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.Exchange = LIBRARIES;
        this.homeFragment = fragment;
    }

    @Override
    public int getCount() {
        return Exchange.size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.row_view_stock_name, container, false);
        TextView tvExchangeName = view.findViewById(R.id.tvExchangeName);
        TextView tvExchangePercentage = view.findViewById(R.id.tvExchangePercentage);
        TextView tvValue = view.findViewById(R.id.tvValue);
        tvExchangeName.setText(Exchange.get(position).getName());
        if (Exchange.get(position).getChangePercent().substring(0, 1).equals("-")) {
            tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            tvExchangePercentage.setText(Exchange.get(position).getChangePercent());
        } else {
            tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            tvExchangePercentage.setText("+" + Exchange.get(position).getChangePercent());

        }
        tvValue.setText(Exchange.get(position).getLatestPrice());
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
