package hcmute.edu.vn.foody_08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class FindActivity extends AppCompatActivity {
    List<Restaurant> lstFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        FoodyDatabase db=new FoodyDatabase(this);

        RecyclerView myrv=(RecyclerView) findViewById(R.id.reclycleView_id);
        RecycleViewAdapter myAdapter= new RecycleViewAdapter(this,db.getAllRestaurants());
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);
    }

}
