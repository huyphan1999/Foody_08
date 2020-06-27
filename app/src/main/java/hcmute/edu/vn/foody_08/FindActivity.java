package hcmute.edu.vn.foody_08;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FindActivity extends Activity implements SearchView.OnQueryTextListener {
    private static final int SEARCH_VIEW_REQUEST_CODE = 100;

    FoodyDatabase db = new FoodyDatabase(this);

    //Tỉnh nhận vào
    Province province = new Province();

    ArrayList<Restaurant> lstRestaurant;
    List<Restaurant> tempLstRestaurant = new ArrayList<>();

    RecycleViewAdapter myAdapter;
    RecyclerView myrv;

    //Widgets
    String query;
    SearchView searchView;
    Button provinceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);


        Intent intent = getIntent();
        province = (Province) intent.getSerializableExtra("Province");
        query = intent.getStringExtra("query");

        addControl();

        setTextData();

        setListRestaurant();

        setEvent();
    }

    public void setTextData() {
        provinceButton.setText(province.getName());

    }


    public void addControl() {
        myrv = findViewById(R.id.reclycleView_id);
        provinceButton = findViewById(R.id.btn_city);
        searchView = findViewById(R.id.txtTimKiem);
        searchView.setOnQueryTextListener(this);
    }

    public void setEvent() {
        searchView.setQuery(query, true);

        provinceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindActivity.this, CityActivity.class);
                intent.putExtra("Province", province);
                intent.putExtra("requestCode", SEARCH_VIEW_REQUEST_CODE);
                startActivityForResult(intent, SEARCH_VIEW_REQUEST_CODE);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_VIEW_REQUEST_CODE && resultCode == RESULT_OK) {
            province = (Province) data.getSerializableExtra("Province");

            lstRestaurant.clear();
            tempLstRestaurant.clear();
            setListRestaurant();
            setTextData();
            searchView.setQuery("", true);
        }
    }

    public void cloneLstRestaurent() {
        tempLstRestaurant.addAll(lstRestaurant);
    }

    public void setListRestaurant() {
        lstRestaurant = (ArrayList<Restaurant>) db.getAllRestaurants(province.getId());
        cloneLstRestaurent();
        myAdapter = new RecycleViewAdapter(this, lstRestaurant);
        myrv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        myrv.addItemDecoration(itemDecorator);
        myrv.setAdapter(myAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }

    public void filter(String newText) {
        newText = newText.toLowerCase();
        lstRestaurant.clear();
        if (newText.length() == 0) {
            lstRestaurant.addAll(tempLstRestaurant);
        } else {
            for (Restaurant restaurant : tempLstRestaurant) {
                if (restaurant.getName().toLowerCase().contains(newText)) {
                    lstRestaurant.add(restaurant);
                }
            }
        }

        myAdapter.notifyDataSetChanged();
    }
}
