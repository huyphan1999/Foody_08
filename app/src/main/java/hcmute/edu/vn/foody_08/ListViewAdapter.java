package hcmute.edu.vn.foody_08;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Province> result;
    Context mcontext;
    int imageId;
    int cityId;


    public ListViewAdapter(Context context, ArrayList<Province> result, int imageId, int cityId) {
        this.mcontext = context;
        this.result = result;
        this.imageId = imageId;
        this.cityId = cityId;
    }


    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view_row = inflater.inflate(R.layout.city_item, parent, false);

        final TextView tv_Content = view_row.findViewById(R.id.tvNoiDung);
        final ImageView ic_tick = view_row.findViewById(R.id.imgCheck);


        if (result.get(position).getId() == cityId) {
            tv_Content.setText(result.get(position).getName());
            tv_Content.setTextColor(0xFF03A9F4);
            ic_tick.setImageResource(imageId);
            //Picasso.with(context).load(imageId).into(imgAvatar);
        } else {
            tv_Content.setText(result.get(position).getName());
            tv_Content.setTextColor(Color.BLACK);
        }
        return view_row;
    }


    //filter
    //public void filter(String charText){
    // charText = charText.toLowerCase(Locale.getDefault());
    // provinceNamesList.clear();
    //  if (charText.length()==0){
    //  provinceNamesList.addAll(arraylist);
    // /
    // else {
    //     for (Province provinceName : arraylist) {
    //        if (provinceName.getName().toLowerCase(Locale.getDefault())
    //                .contains(charText)) {
    //            provinceNamesList.add(provinceName);
    //        }
    //     }
    //  }
    //   notifyDataSetChanged();
    //  }
}
