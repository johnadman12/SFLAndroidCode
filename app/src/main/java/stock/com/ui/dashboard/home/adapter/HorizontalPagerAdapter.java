package stock.com.ui.dashboard.home.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;

import stock.com.R;
import stock.com.ui.dashboard.DashBoardActivity;
import stock.com.ui.dashboard.home.fragment.HomeFragment;
import stock.com.ui.invite.activity.InviteCodeActivity;
import stock.com.ui.invite.activity.InviteFriendsActivity;
import stock.com.ui.offer_list.OfferListActivity;
import stock.com.ui.pojo.HomePojo;
import stock.com.ui.share.ShareActivity;
import stock.com.ui.signup.activity.SignUpActivity;
import stock.com.utils.AppDelegate;
import stock.com.utils.StockConstant;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class HorizontalPagerAdapter extends PagerAdapter {


    private Context mContext;
    private String userId;
    private LayoutInflater mLayoutInflater;
    private HomeFragment homeFragment;
    private List<HomePojo.Banner> LIBRARIES;


    public HorizontalPagerAdapter(final Context context, List<HomePojo.Banner> LIBRARIES, HomeFragment fragment, String userid) {
        mContext = context;
        userId = userid;
        mLayoutInflater = LayoutInflater.from(context);

        this.LIBRARIES = LIBRARIES;
        this.homeFragment = fragment;
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

        Glide.with(mContext).load("http://18.188.34.216/webadmin/uploads/banner/" + LIBRARIES.get(position).getImage()).error(R.mipmap.ic_launcher).into(imageView);

        container.addView(view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userId.toString().equals("")) {
                    AppDelegate.INSTANCE.showAlertRegister(mContext, mContext.getResources().getString(R.string.app_name),mContext
                            .getString(R.string.login_default));
                } else {

                    if (LIBRARIES.get(position).getType() == 1) {
                   /* Intent intent = new Intent(mContext, DashBoardActivity.class);
                    intent.putExtra("flagcomingFrom", "0");
                    mContext.startActivity(intent);*/
                        homeFragment.setintent();

                    } else if (LIBRARIES.get(position).getType() == 2) {
                        mContext.startActivity(new Intent(mContext, ShareActivity.class));
                    } else if (LIBRARIES.get(position).getType() == 3) {
                        mContext.startActivity(new Intent(mContext, OfferListActivity.class));
                    }
                }
            }
        });
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
