package hcmute.edu.vn.foody_08;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    TextView btnCity;
    SearchView editSearch;
    RecyclerView myrv;
    FoodyDatabase db = new FoodyDatabase(this);
    Province defaultProvince = new Province("Hà Nội", 218);
    private FusedLocationProviderClient fusedLocationClient;

    ArrayList<Restaurant> lstRestaurant;
    int province_id;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControl();

        setTextData();

        setListRestaurent();


        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                intent.putExtra("province_id", 218);
                startActivityForResult(intent, RESULT_OK);
            }
        });


        getLocation();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

    }

    public void setTextData() {
        btnCity.setText(defaultProvince.getName());
    }

    public void setListRestaurent() {
        lstRestaurant = (ArrayList<Restaurant>) db.getAllRestaurants(defaultProvince.getId());
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this, lstRestaurant);
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }

    public void addControl() {
        editSearch = findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
        btnCity = findViewById(R.id.btn_City);
        myrv = findViewById(R.id.recyclerview_id);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            province_id = data.getIntExtra("province_id", 0);
            btnCity.setText(GetProvinceName(province_id));
        }
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this, db.getAllRestaurants(province_id));
        myrv.setLayoutManager(new GridLayoutManager(this, 2));
        myrv.setAdapter(myAdapter);
    }


    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Distance.setMyLocation(location);
                        }
                    }
                });
    }

    private String GetProvinceName(int province_id) {
        Cursor dataFlag = db.GetData("SELECT * FROM Province WHERE province_id = " + province_id);
        dataFlag.moveToFirst();
        return dataFlag.getString(1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            finish();
        }
        getLocation();
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
