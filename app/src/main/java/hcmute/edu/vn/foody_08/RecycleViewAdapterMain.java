package hcmute.edu.vn.foody_08;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecycleViewAdapterMain extends RecyclerView.Adapter<RecycleViewAdapterMain.MyViewHolder> {
    public Context mContext;
    private List<Restaurant> mData;

    public RecycleViewAdapterMain(Context mContext, List<Restaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecycleViewAdapterMain.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.card_view_item_dish, parent, false);

        return new MyViewHolder(view);
    }


    public void onBindViewHolder(@NonNull RecycleViewAdapterMain.MyViewHolder holder, final int position) {
        holder.tv_book_title.setText(mData.get(position).getName());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantActivity.class);
                //passing data to the book activity
                intent.putExtra("RestaurantName", mData.get(position).getName());
                intent.putExtra("Description", mData.get(position).getDescription());
                intent.putExtra("Thumbnail", mData.get(position).getThumbnail());
                //start the activity
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;
        TextView tv_address;


        public MyViewHolder(View itemView) {
            super(itemView);
            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);

        }

    }

}
