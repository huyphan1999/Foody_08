package hcmute.edu.vn.foody_08;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    public Context mContext;
    private List<Restaurant> mData;

    public RecycleViewAdapter(Context mContext, List<Restaurant> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.card_view_item_food_detail,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_tenquan.setText(mData.get(position).getName());
        holder.tv_diachi.setText(mData.get(position).getAddress());

        double latitude = mData.get(position).getLatitude();
        double longitude = mData.get(position).getLongitude();
        String distance = Distance.getDistance(latitude, longitude);
        if (distance == null) {
            distance = "0km";
            Toast.makeText(mContext, "Không thể lấy được vị trí của bạn vui lòng thử lại", Toast.LENGTH_LONG).show();
        }
        holder.tv_khoangcach.setText(distance);
        holder.tv_theloai.setText(mData.get(position).getCategory());
        Picasso.get()
                .load(mData.get(position).getThumbnail())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.img_thumbnail);

        holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RestaurantActivity.class);
                intent.putExtra("Restaurant", mData.get(position));
                //start the activity
                mContext.startActivity(intent);
            }
        });
    }
    /*@Override
    public void onBindViewHolder(@NonNull RecycleViewAdapter.MyViewHolder holder, final int position) {
        holder.tv_book_title.setText(mData.get(position).getName());
        holder.img_book_thumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RestaurantActivity.class);
                //passing data to the book activity
                intent.putExtra("RestaurantName",mData.get(position).getName());
                intent.putExtra("Description",mData.get(position).getDescription());
                intent.putExtra("Thumbnail",mData.get(position).getThumbnail());
                //start the activity
                mContext.startActivity(intent);
            }
        });

    }*/

    @Override
    public int getItemCount() {
        return mData.size();
    }
   /* public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView ;
        TextView tv_address;


        public MyViewHolder(View itemView){
            super(itemView);
            tv_book_title=(TextView)itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail=(ImageView)itemView.findViewById(R.id.book_img_id);
            cardView=(CardView)itemView.findViewById(R.id.cardview_id);

        }

    }*/
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tenquan;
        TextView tv_diachi;
        TextView tv_khoangcach;
        TextView tv_theloai;
       ImageView img_thumbnail;
       CardView cardViewItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tenquan = itemView.findViewById(R.id.txtTenQuan);
            tv_diachi = itemView.findViewById(R.id.txtDiaChi);
            tv_khoangcach = itemView.findViewById(R.id.txtKhoangCach);
            tv_theloai = itemView.findViewById(R.id.txtTheLoai);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);
            cardViewItem = itemView.findViewById(R.id.cardViewItem);
        }
    }
}
