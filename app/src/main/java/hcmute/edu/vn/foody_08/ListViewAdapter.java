package hcmute.edu.vn.foody_08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<Province> provinceNamesList = null;
    private ArrayList<Province> arraylist;

    public ListViewAdapter(Context context, List<Province> provinceNamesList) {
        mContext = context;
        this.provinceNamesList = provinceNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Province>();
        this.arraylist.addAll(provinceNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return provinceNamesList.size();
    }

    @Override
    public Province getItem(int position) {
        return provinceNamesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.city_item, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name_city);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(provinceNamesList.get(position).getName());
        return view;
    }


    //filter
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        provinceNamesList.clear();
        if (charText.length()==0){
            provinceNamesList.addAll(arraylist);
        }
        else {
            for (Province provinceName : arraylist) {
                if (provinceName.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    provinceNamesList.add(provinceName);
                }
            }
        }
        notifyDataSetChanged();
    }
}
