package stock.com.ui.watch_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import stock.com.R;
import stock.com.ui.pojo.StockPojo;

import java.util.ArrayList;

public class WatchListAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

    private ArrayList<StockPojo.Stock> searchList=new ArrayList<>();
    private ArrayList<StockPojo.Stock> list;
    private Context context;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private WatchListActivity activity;

    public WatchListAdapter(ArrayList<StockPojo.Stock> list,Context context,WatchListActivity activity) {
        this.context=context;
        this.searchList=list;
        this.list=list;
        this.activity=activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_view_watch_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        viewBinderHelper.bind(holder.swipeRevealLayout, ""+position);
        holder.tv_company_name.setText(searchList.get(position).getSymbol());
        holder.tv_sector.setText(searchList.get(position).getSector());
        holder.tv_change_percentage.setText(searchList.get(position).getChangePercent());
        holder.tv_market_open.setText(searchList.get(position).getMarketopen());

        holder.img_btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity!= null){
                    activity.callApiRemoveWatch(searchList.get(position).getId());
                }
            }
        });
        if(!searchList.get(position).getImage().equals("")){
            Glide.with(context).load(searchList.get(position).getImage()).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    searchList = list;
                } else {
                    ArrayList<StockPojo.Stock> filteredList = new ArrayList<>();
                    for (StockPojo.Stock row : list) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        //if (row.getFullname().toLowerCase().contains(charString.toLowerCase())) {
                        if (row.getCompanyName().toLowerCase().contains(charString.toLowerCase())||row.getSymbol().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    searchList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = searchList;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                searchList = (ArrayList<StockPojo.Stock>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
