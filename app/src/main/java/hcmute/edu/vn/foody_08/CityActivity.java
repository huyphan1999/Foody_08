package hcmute.edu.vn.foody_08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends Activity implements SearchView.OnQueryTextListener {
    public static int imgTICK = R.drawable.bluee_tick;

    private ArrayList<Province> arrayListProvince = new ArrayList<Province>();

    List<Province> tempLstProvince = new ArrayList<>();
    ListViewAdapter listViewAdapter;
    int requestCode;
    FoodyDatabase db = new FoodyDatabase(this);
    private TextView txtCancel, txtAgree;
    Province province;
    private ListView listViewProvince;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        Intent intent = getIntent();
        province = (Province) intent.getSerializableExtra("Province");
        requestCode = intent.getIntExtra("requestCode", MainActivity.MAIN_VIEW_REQUEST_CODE);

        arrayListProvince = db.getAllPronvinces();
        tempLstProvince.addAll(arrayListProvince);

        listViewProvince = findViewById(R.id.listView_city);
        listViewAdapter = new ListViewAdapter(CityActivity.this, arrayListProvince, imgTICK, province.getId());
        listViewProvince.setAdapter(listViewAdapter);
        txtCancel = findViewById(R.id.txtHuy);
        txtAgree = findViewById(R.id.txtXong);
        searchView = findViewById(R.id.search_city_id);
        searchView.setOnQueryTextListener(this);

        listViewProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                province = arrayListProvince.get(position);
                listViewProvince.setAdapter(new ListViewAdapter(CityActivity.this, arrayListProvince, imgTICK, province.getId()));
                listViewProvince.smoothScrollToPosition(position);
            }
        });


        txtAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult(RESULT_OK);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult(RESULT_CANCELED);
            }
        });
    }

    public void returnResult(int resultCode) {

        Intent intent = new Intent();
        intent.putExtra("Province", province);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onBackPressed() {

        // đặt resultCode là Activity.RESULT_CANCELED thể hiện
        // đã thất bại khi người dùng click vào nút Back.
        // Khi này sẽ không trả về data.
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }


    public void filter(String newText) {
        newText = newText.toLowerCase();
        arrayListProvince.clear();
        if (newText.length() == 0) {
            arrayListProvince.addAll(tempLstProvince);
        } else {
            for (Province province : tempLstProvince) {
                if (province.getName().toLowerCase().contains(newText)) {
                    arrayListProvince.add(province);
                }
            }
        }
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }
}
