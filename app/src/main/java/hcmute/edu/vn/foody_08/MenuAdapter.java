package hcmute.edu.vn.foody_08;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class MenuAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Dish>> expandableListDetail;

    public MenuAdapter(Context context, List<String> expandableListTitle,
                       HashMap<String, List<Dish>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    public String capWordFirstLetter(String fullname)
    {
        String fname = "";
        String s2;
        StringTokenizer tokenizer = new StringTokenizer(fullname);
        while (tokenizer.hasMoreTokens())
        {
            s2 = tokenizer.nextToken().toLowerCase();
            if (fname.length() == 0)
                fname += s2.substring(0, 1).toUpperCase() + s2.substring(1);
            else
                fname += " "+s2.substring(0, 1).toUpperCase() + s2.substring(1);
        }

        return fname;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Dish dish=(Dish) getChild(listPosition, expandedListPosition);
        final String expandedListText = dish.getName();
        DecimalFormat df = new DecimalFormat("#,###,###");
        final int price= dish.getPrice();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dish_menu_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expandedListItem);

        TextView expandedListTextViewPrice = (TextView) convertView
                .findViewById(R.id.expandedListItemPrice);

        expandedListTextView.setText(expandedListText);
        expandedListTextViewPrice.setText(String.valueOf(df.format(price))+" đ");
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dish_menu_type, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(capWordFirstLetter(listTitle.toLowerCase()));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
