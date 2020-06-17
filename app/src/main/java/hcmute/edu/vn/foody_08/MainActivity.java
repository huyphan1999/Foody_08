package hcmute.edu.vn.foody_08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener {

    Button btnCity;
    SearchView editsearch;
    ListViewAdapter adapter;


    ArrayList<Restaurant> lstRestaurant;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoodyDatabase db=new FoodyDatabase(this);

        // Locate the EditText in listview_main.xml
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





        lstRestaurant=new ArrayList<>();
        lstRestaurant.add(new Restaurant(1,"Bánh Rán Thiên Quang","Category","Description","Address",R.drawable.banh_ran_thien_quang));
        lstRestaurant.add(new Restaurant(2,"Bánh Tráng Sài Gòn","Categorie Book","Description book","Address",R.drawable.banh_trang_sai_gon));
        lstRestaurant.add(new Restaurant(3,"Bún Bò Huế","Categorie Book","Description book","Address",R.drawable.bun_bo_hue));
        lstRestaurant.add(new Restaurant(4,"Chân Gà Bay Online","Categorie Book","Description book","Address",R.drawable.chan_ga_bay_online));
        lstRestaurant.add(new Restaurant(5,"Chè Ngon Hà Nội","Categorie Book","Description book","Address",R.drawable.che_ngon_ha_noi_mon_duong_pho));
        lstRestaurant.add(new Restaurant(6,"Cơm Văn Phòng","Categorie Book","Description book","Address",R.drawable.com_van_phong));

        db.initRestaurants(lstRestaurant);

        RecyclerView myrv = findViewById(R.id.recyclerview_id);
        RecycleViewAdapterMain myAdapter = new RecycleViewAdapterMain(this,db.getAllRestaurants());
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myAdapter);

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
