package hcmute.edu.vn.foody_08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    Button btnCity;
    SearchView editsearch;
    ListViewAdapter adapter;
    FoodyDatabase db = new FoodyDatabase(this);

    ArrayList<Restaurant> lstRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editsearch = findViewById(R.id.search);
        btnCity=findViewById(R.id.btn_City);
        editsearch.setOnQueryTextListener(this);
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);

                startActivity(intent);
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
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this, db.getAllRestaurants(248));
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(MainActivity.this, FindActivity.class);
        startActivity(intent);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
