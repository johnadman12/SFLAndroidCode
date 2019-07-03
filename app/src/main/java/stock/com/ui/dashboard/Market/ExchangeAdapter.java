package stock.com.ui.dashboard.Market;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import stock.com.R;
import stock.com.ui.pojo.ExchangeList;

import java.util.List;

public class ExchangeAdapter  extends PagerAdapter {


    private Context mContext;
    private String userId;
    private LayoutInflater mLayoutInflater;
    private List<ExchangeList.Exchange> Exchange;


    public ExchangeAdapter(final Context context, List<ExchangeList.Exchange> LIBRARIES) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.Exchange = LIBRARIES;
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
        tvExchangeName.setText(Exchange.get(position).name);
        if (Exchange.get(position).changePercent.substring(0, 1).equals("-")) {
            tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            tvExchangePercentage.setText(Exchange.get(position).changePercent);
        } else {
            tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            tvExchangePercentage.setText("+" + Exchange.get(position).changePercent);

        }
        tvValue.setText(Exchange.get(position).latestPrice);
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
