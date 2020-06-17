package hcmute.edu.vn.foody_08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class CityActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener {
    String[] provinceNameList;
    private ListView lv;
    ListViewAdapter adapter;
    private ArrayList<ProvinceName> arrayList= new ArrayList<ProvinceName>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        provinceNameList= new String[]{"Đồng Nai","Hồ Chí Minh","Vũng Tàu","Bình Dương","Hà Nội","Đà Nẵng"};
        // Locate the ListView in listview_main.xml
        lv =  findViewById(R.id.listView_city);
        for (int i = 0; i < provinceNameList.length; i++) {
            ProvinceName provinceNames = new ProvinceName(provinceNameList[i]);
            // Binds all strings into an array
            arrayList.add(provinceNames);
        }
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arrayList);

        // Binds the Adapter to the ListView
        lv.setAdapter(adapter);

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
}
