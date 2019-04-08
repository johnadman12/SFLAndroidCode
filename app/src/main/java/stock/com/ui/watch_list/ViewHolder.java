package stock.com.ui.watch_list;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import stock.com.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public SwipeRevealLayout swipeRevealLayout;
    AppCompatImageButton img_btn_remove;
    AppCompatImageView imageView;
    AppCompatTextView tv_company_name;
    AppCompatTextView tv_sector;
    AppCompatTextView tv_market_open;
    AppCompatTextView tv_change_percentage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        swipeRevealLayout=(SwipeRevealLayout)itemView.findViewById(R.id.swipeRevealLayout);
        img_btn_remove=(AppCompatImageButton) itemView.findViewById(R.id.img_btn_remove);
        imageView=(AppCompatImageView) itemView.findViewById(R.id.imageView);
        tv_company_name=(AppCompatTextView) itemView.findViewById(R.id.tv_company_name);
        tv_sector=(AppCompatTextView) itemView.findViewById(R.id.tv_sector);
        tv_market_open=(AppCompatTextView) itemView.findViewById(R.id.tv_market_open);
        tv_change_percentage=(AppCompatTextView) itemView.findViewById(R.id.tv_change_percentage);

    }
}
