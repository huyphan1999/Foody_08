package hcmute.edu.vn.foody_08;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<String> result;
    Context mcontext;
    int imageId;
    String tinh;
    EditText editText1;


    public ListViewAdapter(Context context, ArrayList<String> result, int imageId, String tinh, EditText editText1) {
        this.mcontext = context;
        this.result = result;
        this.imageId = imageId;
        this.tinh = tinh;
        editText1 = editText1;
    }

    public class ViewHolder {
        TextView name;
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
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        View view_row = inflater.inflate(R.layout.city_item, parent, false);

        final TextView tv_Content = (TextView) view_row.findViewById(R.id.tvNoiDung);
        final ImageView ic_tick = (ImageView) view_row.findViewById(R.id.imgCheck);


        if (result.get(position).equals(tinh)) {
            tv_Content.setText(result.get(position));
            tv_Content.setTextColor(0xFF03A9F4);
            ic_tick.setImageResource(imageId);
            //Picasso.with(context).load(imageId).into(imgAvatar);
        } else {
            tv_Content.setText(result.get(position));
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
