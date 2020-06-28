package hcmute.edu.vn.foody_08;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    String[] provinceNameList;
    private ListView listView;
    ListViewAdapter adapter;

    private ArrayList<Province> arrayListProvince = new ArrayList<Province>();
    FoodyDatabase db = new FoodyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        listView = findViewById(R.id.listView_city);
        arrayListProvince = db.getAllPronvinces();
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arrayListProvince);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);

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
