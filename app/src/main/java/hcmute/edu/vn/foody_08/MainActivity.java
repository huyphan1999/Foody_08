package hcmute.edu.vn.foody_08;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView btnCity;
    SearchView editsearch;

    ListViewAdapter adapter;
    FoodyDatabase db = new FoodyDatabase(this);
    int province_id;
    ArrayList<Restaurant> lstRestaurant;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            province_id = data.getIntExtra("province_id", 0);
            btnCity.setText(GetProvinceName(province_id));
        }
        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this, db.getAllRestaurants(province_id));
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editsearch = findViewById(R.id.search);
        btnCity = findViewById(R.id.btn_City);

        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                intent.putExtra("province_id", 218);
                startActivityForResult(intent, RESULT_OK);
            }
        });


        editsearch.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        });
        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this, db.getAllRestaurants(province_id));
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }

    private String GetProvinceName(int province_id) {
        Cursor dataFlag = db.GetData("SELECT * FROM Province WHERE province_id = " + province_id);
        dataFlag.moveToFirst();
        return dataFlag.getString(1);
    }
}
