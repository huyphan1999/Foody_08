package hcmute.edu.vn.foody_08;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Menu_Restaurant extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String,List<Dish>> menu=new HashMap<>();
    TextView txtNameRes;
    Restaurant restaurant;
    ImageView imgBtnBack;

    FoodyDatabase db=new FoodyDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__restaurant);

        addControl();

        Intent intent=getIntent();
        restaurant = (Restaurant) intent.getSerializableExtra("Restaurant");

        setTextData(restaurant);

        displayMenu(restaurant);
        imgBtnBack=findViewById(R.id.imgbutton_back2);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
                Toast.makeText(getApplicationContext(), "Go to Menu Restaurant", Toast.LENGTH_SHORT).show();


            }
        });



    }

    protected void addControl() {
        txtNameRes = findViewById(R.id.txtNameRes);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
    }

    protected void setTextData(Restaurant res) {
        txtNameRes.setText(res.getName());
    }

    protected void displayMenu(Restaurant restaurant) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        menu=db.getMenu(restaurant.getId());
        expandableListTitle = new ArrayList<String>(menu.keySet());
        expandableListAdapter = new MenuAdapter(this, expandableListTitle, menu);
        expandableListView.setAdapter(expandableListAdapter);

        for(int i=0; i < expandableListAdapter.getGroupCount(); i++)
            expandableListView.expandGroup(i);

        //Ném dấu mũi tên qua phải
        expandableListView.setIndicatorBounds(width - GetDipsFromPixel(30),width - GetDipsFromPixel(10));
    }

    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

}